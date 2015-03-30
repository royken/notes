/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEnseignantService;
import com.douwe.notes.service.IEnseignementService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.IProgrammeService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
import com.douwe.notes.service.impl.OptionServiceImpl;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author root
 */
@Named(value = "programmeBean")
@RequestScoped
public class ProgrammeBean {

    @EJB
    private IProgrammeService programmeService;
    @EJB
    private IUniteEnseignementService uniteEnseignementService;
    @EJB
    private IParcoursService parcoursService;
    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
    @EJB
    private IOptionService optionService;
    @EJB
    private INiveauService niveauService;
    @EJB
    private IEnseignantService enseignantService;

    @EJB
    private IEnseignementService enseignementService;
    private Programme programme = new Programme();
    private List<Programme> programmes;
    private List<AnneeAcademique> anneeAcademiques;
    private List<UniteEnseignement> uniteEnseignements;
    private List<Parcours> parcourses;
    private List<Enseignant> enseignants = new LinkedList<Enseignant>();
    private List<Enseignant> enseignantChoisis = new LinkedList<Enseignant>();
    private String message;
    private List<Option> options;
    private List<Niveau> niveaus;
    Long idO = 0L, idN = 0L;
    String idAca, idUE, idP;
    Long[] ide = new Long[10];

    public ProgrammeBean() {
    }

    public void saveOrUpdateProgramme(ActionEvent actionEvent) throws ServiceException {
//        if (programme != null) {
//            if (ide != null) {
//                int i = 0;
//                while (i < ide.length) {
//                    Enseignant e = enseignantService.findEnseignantById(ide[i]);
//                    enseignantChoisis.add(e);
//                    i++;
//                }
                //enseignement.setAnneeAcademique(anneeAcademiqueService.findAnneeById(Integer.parseInt(idAca)));
                //enseignement.setEnseignants(enseignantChoisis);
                //enseignementService.saveOrUpdateEnseignement(enseignement);
          //  }
            programme.setAnneeAcademique(anneeAcademiqueService.findAnneeById(Integer.parseInt(idAca)));
            programme.setParcours(parcoursService.findParcoursById(Integer.parseInt(idP)));
            programme.setUniteEnseignement(uniteEnseignementService.findUniteEnseignementById(Integer.parseInt(idUE)));
            programmeService.saveOrUpdateProgramme(programme);
            if (programme.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", programme.getUniteEnseignement().getCode().toUpperCase() + " a été programmé en " + programme.getParcours().getNiveau().getCode().toUpperCase() + "/" + programme.getParcours().getOption().getCode().toUpperCase() + " pour l'année " + programme.getAnneeAcademique().getDebut().toString().substring(programme.getAnneeAcademique().getDebut().toString().length() - 4) + "/" + programme.getAnneeAcademique().getFin().toString().substring(programme.getAnneeAcademique().getFin().toString().length() - 4)));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", programme.getUniteEnseignement().getCode().toUpperCase() + " a été programmé en " + programme.getParcours().getNiveau().getCode().toUpperCase() + "/" + programme.getParcours().getOption().getCode().toUpperCase() + " pour l'année " + programme.getAnneeAcademique().getDebut().toString().substring(programme.getAnneeAcademique().getDebut().toString().length() - 4) + "/" + programme.getAnneeAcademique().getFin().toString().substring(programme.getAnneeAcademique().getFin().toString().length() - 4)));
            }
            idO=programme.getParcours().getOption().getId();
            idN=programme.getParcours().getNiveau().getId();
            filtrer();
            programme = new Programme();
            idAca = new String();
            idP = new String();
            idUE = new String();
        }
    

    public void filtrer() throws ServiceException {
        
        programmes = programmeService.getAllProgrammes();        
        //findProgrammeByParcours(idN, idO);
    }

    public void deleteProgramme(ActionEvent actionEvent) throws ServiceException {
        if (programme != null && programme.getId() != null) {
            programmeService.deleteProgramme(programme.getId());
            idO=programme.getParcours().getOption().getId();
            idN=programme.getParcours().getNiveau().getId();
            filtrer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", programme.getUniteEnseignement().getCode().toUpperCase() + " n'est plus programmé en " + programme.getParcours().getNiveau().getCode().toUpperCase() + "/" + programme.getParcours().getOption().getCode().toUpperCase() + " pour l'année " + programme.getAnneeAcademique().getDebut().toString().substring(programme.getAnneeAcademique().getDebut().toString().length() - 4) + "/" + programme.getAnneeAcademique().getFin().toString().substring(programme.getAnneeAcademique().getFin().toString().length() - 4)));
            programme = new Programme();
        }
    }

    public String affiche(List<Cours> c) {
        String result = "";
        if (c != null) {
            System.out.println("" + c);
            result = ": ";
            for (Cours c1 : c) {
                result += c1.getIntitule() + ";";
            }
            result=result.substring(0,result.length()-1);
        }
        return result;
    }

    public int cout(List<Cours> c) {
        int t = 0;
        if (c != null) {
            for (Cours c1 : c) {
                t += c1.getCredit();
            }
        }
        return t;
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (programme != null && programme.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un programme avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (programme != null && programme.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un programme avant de supprimer "));
        }
    }

    public IProgrammeService getProgrammeService() {
        return programmeService;
    }

    public void setProgrammeService(IProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    public IUniteEnseignementService getUniteEnseignementService() {
        return uniteEnseignementService;
    }

    public void setUniteEnseignementService(IUniteEnseignementService uniteEnseignementService) {
        this.uniteEnseignementService = uniteEnseignementService;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public List<Programme> getProgrammes() throws ServiceException {
        return programmes;
    }

    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public List<UniteEnseignement> getUniteEnseignements() throws ServiceException {
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        return uniteEnseignements;
    }

    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
    }

    public List<Parcours> getParcourses() throws ServiceException {
        parcourses = parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public String getIdAca() {
        if (programme != null && programme.getVersion() != 0) {
            idAca = programme.getAnneeAcademique().getId().toString();
        }
        return idAca;
    }

    public void setIdAca(String idAca) {
        this.idAca = idAca;
    }

    public String getIdUE() {
        if (programme != null && programme.getVersion() != 0) {
            idUE = programme.getUniteEnseignement().getId().toString();
        }
        return idUE;
    }

    public void setIdUE(String idUE) {
        this.idUE = idUE;
    }

    public String getIdP() {
        if (programme != null && programme.getVersion() != 0) {
            idP = programme.getParcours().getId().toString();
        }
        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public IEnseignantService getEnseignantService() {
        return enseignantService;
    }

    public void setEnseignantService(IEnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public List<Enseignant> getEnseignants() throws ServiceException {
        enseignants = enseignantService.getAllEnseignants();
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public List<Enseignant> getEnseignantChoisis() {
        return enseignantChoisis;
    }

    public void setEnseignantChoisis(List<Enseignant> enseignantChoisis) {
        this.enseignantChoisis = enseignantChoisis;
    }

    public Long[] getIde() {
        return ide;
    }

    public void setIde(Long[] ide) {
        this.ide = ide;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public List<Option> getOptions() throws ServiceException {
        options = optionService.getAllOptions();
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Niveau> getNiveaus() throws ServiceException {
        niveaus = niveauService.getAllNiveaux();
        return niveaus;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public Long getIdO() {
        return idO;
    }

    public void setIdO(Long idO) {
        this.idO = idO;
    }

    public Long getIdN() {
        return idN;
    }

    public void setIdN(Long idN) {
        this.idN = idN;
    }

}
