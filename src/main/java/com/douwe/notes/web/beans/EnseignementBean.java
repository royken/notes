package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEnseignantService;
import com.douwe.notes.service.IEnseignementService;
import com.douwe.notes.service.ServiceException;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

@Named(value = "enseignementBean")
@RequestScoped
public class EnseignementBean {

    @EJB
    private IEnseignementService enseignementService;
    @EJB
    private IEnseignantService enseignantService;
    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
    @EJB
    private ICoursService coursService;
    private Enseignement enseignement = new Enseignement();
    private List<Enseignement> enseignements;
    private List<Cours> courses;
    private List<Enseignant> enseignants;
    private List<Enseignant> enseignantsChoisis;
    private List<AnneeAcademique> anneeAcademiques;
    private Long idAca;
    private Long idC;
    private Long idEnseignement;
    private Long[] idEnseignants;
    int n = 5;

    public EnseignementBean() {
        this.idEnseignants = new Long[n];
        enseignement = new Enseignement();
        enseignantsChoisis = new LinkedList<Enseignant>();
    }

    public void saveOrUpdateEnseignement(ActionEvent actionEvent) throws ServiceException {
        if (idEnseignants != null) {
            int i;
            for (i = 0; i < idEnseignants.length; i++) {
                if (idEnseignants[i] > 0) {
                    Enseignant e = enseignantService.findEnseignantById(idEnseignants[i]);
                    enseignantsChoisis.add(e);
                }

            }

            enseignement.setEnseignants(enseignantsChoisis);
            if (idAca != null) {
                AnneeAcademique a = anneeAcademiqueService.findAnneeById(idAca);
                enseignement.setAnneeAcademique(a);
            }
            if (idC != null) {
                Cours c = coursService.findCoursById(idC);
                enseignement.setCours(c);

            }
            enseignementService.saveOrUpdateEnseignement(enseignement);
            if (enseignement.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "le cours " + enseignement.getCours().getIntitule() + " est desormais dispensé par " +afficheEnseignant(enseignement.getEnseignants())+ " pour l'année " + enseignement.getAnneeAcademique()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "le cours " + enseignement.getCours().getIntitule() + " est desormais dispensé par " +afficheEnseignant(enseignement.getEnseignants())+ " pour l'année " + enseignement.getAnneeAcademique()));
            }

            enseignement = new Enseignement();
            idEnseignants = new Long[n];
        }
    }

    public void deleteEnseignement(ActionEvent actionEvent) throws ServiceException {
        if (enseignement != null && enseignement.getId() != null) {
            enseignementService.deleteEnseignement(enseignement.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "le cours " + enseignement.getCours().getIntitule() + " ne sera pas dispensé par " +afficheEnseignant(enseignement.getEnseignants())+ " pour l'année " + enseignement.getAnneeAcademique()));
            enseignement = new Enseignement();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (enseignement != null && enseignement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez une unité d'enseignement avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (enseignement != null && enseignement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez une unité d'enseignement avant de supprimer "));
        }
    }
public String afficheEnseignant(List<Enseignant> esgts){
     String result = "";
        if (esgts != null && !(esgts.isEmpty())) {            
            for (Enseignant e : esgts) {
                result += e.getNom() + ", ";
            }
            result = result.substring(0, result.length() - 2);            
        }
        return result;
}
    

    public IEnseignementService getEnseignementService() {
        return enseignementService;
    }

    public void setEnseignementService(IEnseignementService enseignementService) {
        this.enseignementService = enseignementService;
    }

    public Enseignement getEnseignement() {
        return enseignement;
    }

    public void setEnseignement(Enseignement enseignement) {
        this.enseignement = enseignement;
    }

    public List<Enseignement> getEnseignements() throws ServiceException {
        enseignements = enseignementService.getAllEnseignements();
        return enseignements;
    }

    public void setEnseignements(List<Enseignement> enseignements) {
        this.enseignements = enseignements;
    }

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }

    public List<Cours> getCourses() throws ServiceException {
        courses = coursService.getAllCours();
        return courses;
    }

    public void setCourses(List<Cours> courses) {
        this.courses = courses;
    }

    public Long[] getIdEnseignants() {
        if (enseignement != null && enseignement.getEnseignants() != null) {
            int i = 0;
            for (Iterator<Enseignant> iterator = enseignement.getEnseignants().iterator(); iterator.hasNext();) {
                Enseignant next = iterator.next();
                idEnseignants[i] = next.getId();
                i++;
            }
        }
        return idEnseignants;
    }

    public void setIdEnseignants(Long[] idEnseignants) {
        this.idEnseignants = idEnseignants;
    }

    public IEnseignantService getEnseignantService() {
        return enseignantService;
    }

    public void setEnseignantService(IEnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public List<Enseignant> getEnseignants() throws ServiceException {
        enseignants = enseignantService.getAllEnseignants();
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public List<Enseignant> getEnseignantsChoisis() {
        return enseignantsChoisis;
    }

    public void setEnseignantsChoisis(List<Enseignant> enseignantsChoisis) {
        this.enseignantsChoisis = enseignantsChoisis;
    }

    public Long getIdAca() {
        if (enseignement != null && enseignement.getAnneeAcademique() != null) {
            idAca = enseignement.getAnneeAcademique().getId();
        }
        return idAca;
    }

    public void setIdAca(Long idAca) {

        this.idAca = idAca;
    }

    public Long getIdC() {
        if (enseignement != null && enseignement.getCours() != null) {
            idC = enseignement.getCours().getId();
        }
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public Long getIdEnseignement() {
        return idEnseignement;
    }

    public void setIdEnseignement(Long idEnseignement) {
        this.idEnseignement = idEnseignement;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

}
