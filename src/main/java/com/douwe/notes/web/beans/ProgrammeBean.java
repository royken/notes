/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.IProgrammeService;
import com.douwe.notes.service.ISemestreService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private IDepartementService departementService;

    @EJB
    private ISemestreService semestreService;
    private List<Semestre> semestres;
    private Programme programme = new Programme();
    private List<Programme> programmes;
    private List<AnneeAcademique> anneeAcademiques;
    private List<UniteEnseignement> uniteEnseignements;
    private Map<String, Long> countries = new HashMap<String, Long>();
    private Map<String, Long> cities = null;
    private List<Parcours> parcourses;
    private List<Option> options;
    private List<Niveau> niveaus;
    Long idO = 0L, idN = 0L;
    Long idAca = 0L, idUE = 0L, idP = 0L, idS = 0L, idD = 0L;

    public ProgrammeBean() {
    }

    public void saveOrUpdateProgramme(ActionEvent actionEvent) throws ServiceException {
        programme.setAnneeAcademique(anneeAcademiqueService.findAnneeById(idAca));
        programme.setParcours(parcoursService.findParcoursById(idP));
        programme.setUniteEnseignement(uniteEnseignementService.findUniteEnseignementById(idUE));
        programme.setSemestre(semestreService.findSemestreById(idS));
        programmeService.saveOrUpdateProgramme(programme);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", programme.getUniteEnseignement().getCode().toUpperCase() + " a été programmée en " + programme.getParcours().getNiveau().getCode().toUpperCase() + "/" + programme.getParcours().getOption().getCode().toUpperCase() + " pour l'année " + programme.getAnneeAcademique().getDebut().toString().substring(programme.getAnneeAcademique().getDebut().toString().length() - 4) + "/" + programme.getAnneeAcademique().getFin().toString().substring(programme.getAnneeAcademique().getFin().toString().length() - 4)));
        idO = programme.getParcours().getOption().getId();
        idN = programme.getParcours().getNiveau().getId();
        programme = new Programme();
    }

    public void filtrer() throws ServiceException {
        programmes = new LinkedList<Programme>();
        if (idN != 0L && idS != 0L && idAca != 0L && idO != 0L) {
            programmes = programmeService.findProgrammeByParcours(idN, idO, idAca, idS);
        }
    }

    public void deleteProgramme(ActionEvent actionEvent) throws ServiceException {
        if (programme != null && programme.getId() != null) {
            programme=programmeService.findProgrammeById(programme.getId());
            programmeService.deleteProgramme(programme.getId());
            idO = programme.getParcours().getOption().getId();
            idN = programme.getParcours().getNiveau().getId();
            idAca = programme.getAnneeAcademique().getId();
            idS = programme.getSemestre().getId();
            filtrer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", programme.getUniteEnseignement().getCode().toUpperCase() + " n'est plus programmée en " + programme.getParcours().getNiveau().getCode().toUpperCase() + "/" + programme.getParcours().getOption().getCode().toUpperCase() + " pour l'année " + programme.getAnneeAcademique().getDebut().toString().substring(programme.getAnneeAcademique().getDebut().toString().length() - 4) + "/" + programme.getAnneeAcademique().getFin().toString().substring(programme.getAnneeAcademique().getFin().toString().length() - 4)));
            programme = new Programme();
        }
    }

    public String affiche(List<Cours> c) {
        String result = "";
        if (c != null && !(c.isEmpty())) {
            result = "( ";
            for (Cours c1 : c) {
                result += c1.getIntitule() + "; ";
            }
            result = result.substring(0, result.length() - 2);
            result += ")";
        }
        return result;
    }

    public int cout(List<Cours> c) {
        int t = 0;
//        if (c != null) {
//            for (Cours c1 : c) {
//                t += c1.getCredit();
//            }
//        }
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

    public ISemestreService getSemestreService() {
        return semestreService;
    }

    public void setSemestreService(ISemestreService semestreService) {
        this.semestreService = semestreService;
    }

    public List<Semestre> getSemestres() throws ServiceException {
        semestres = semestreService.getAllSemestre();
        return semestres;
    }

    public void setSemestres(List<Semestre> semestres) {
        this.semestres = semestres;
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
        programmes = new LinkedList<Programme>();
        programmes = programmeService.getAllProgrammes();
        if (idN != 0L && idS != 0L && idAca != 0L && idO != 0L) {
            programmes = programmeService.findProgrammeByParcours(idN, idO, idAca, idS);
        }
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

    public Long getIdAca() {
        if (programme != null && programme.getVersion() != 0) {
            idAca = programme.getAnneeAcademique().getId();
        }
        return idAca;
    }

    public void setIdAca(Long idAca) {
        this.idAca = idAca;
    }

    public Long getIdUE() {
        if (programme != null && programme.getVersion() != 0) {
            idUE = programme.getUniteEnseignement().getId();
        }
        return idUE;
    }

    public void setIdUE(Long idUE) {
        this.idUE = idUE;
    }

    public Long getIdP() {
        if (programme != null && programme.getVersion() != 0) {
            idP = programme.getParcours().getId();
        }
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
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

    public Long getIdS() {
        if (programme != null && programme.getSemestre() != null && programme.getId() != null) {
            idS = programme.getSemestre().getId();
        }
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }

    public void handleCountryChange() throws ServiceException {
        if (idD != null && idD != 0L) {
            Departement d = departementService.findDepartementById(idD);
            options = departementService.getAllOptions(d);
            cities = new HashMap<String, Long>();
            for (Option opt : options) {
                cities.put(opt.getCode(), opt.getId());
            }
        } else {
            cities = null;
        }
    }

    public Map<String, Long> getCountries() throws ServiceException {
        List<Departement> departements = departementService.getAllDepartements();
        for (Departement d : departements) {
            countries.put(d.getCode(), d.getId());
        }
        return countries;
    }

    public void setCountries(Map<String, Long> countries) {
        this.countries = countries;
    }

    public Map<String, Long> getCities() throws ServiceException {
        if (cities == null) {
            cities = new HashMap<String, Long>();
            options = optionService.getAllOptions();
            for (Option opt : options) {
                cities.put(opt.getCode(), opt.getId());
            }
        }

        return cities;
    }

    public void setCities(Map<String, Long> cities) {
        this.cities = cities;
    }
}
