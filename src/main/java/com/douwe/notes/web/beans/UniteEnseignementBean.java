package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
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

@Named(value = "uniteEnseignementBean")
@RequestScoped
public class UniteEnseignementBean {

    @EJB
    private IUniteEnseignementService uniteEnseignementService;
    @EJB
    private ICoursService coursService;
    private UniteEnseignement uniteEnseignement = new UniteEnseignement();
    private List<UniteEnseignement> uniteEnseignements;
    private List<Cours> courses;
    private List<Cours> coursChoisis;
    private Long[] ids;
    int n = 5;

    public UniteEnseignementBean() {
        this.ids = new Long[n];
        coursChoisis = new LinkedList<Cours>();
    }

    public void saveOrUpdateUniteEnseignement(ActionEvent actionEvent) throws ServiceException {
        if (uniteEnseignement != null && uniteEnseignement.getCode() != null) {
            int i;
            for (i = 0; i < ids.length; i++) {
                if (ids[i] > 0) {
                    Cours c = coursService.findCoursById(ids[i]);
                    coursChoisis.add(c);
                }

            }

            uniteEnseignement.setCourses(coursChoisis);
            System.out.println("---" + uniteEnseignement);
            uniteEnseignementService.saveOrUpdateUe(uniteEnseignement);
            if (uniteEnseignement.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", uniteEnseignement.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", uniteEnseignement.getCode() + " a été enregistré"));
            }

            uniteEnseignement = new UniteEnseignement();
            ids = new Long[n];
        }
    }

    public void deleteUniteEnseignement(ActionEvent actionEvent) throws ServiceException {
        if (uniteEnseignement != null && uniteEnseignement.getId() != null) {
            uniteEnseignementService.deleteUniteEnseignement(uniteEnseignement.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", uniteEnseignement.getCode() + " a été supprimé"));
            uniteEnseignement = new UniteEnseignement();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (uniteEnseignement != null && uniteEnseignement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez une unité d'enseignement avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (uniteEnseignement != null && uniteEnseignement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez une unité d'enseignement avant de supprimer "));
        }
    }

    public void verifierEtAffiche(ActionEvent actionEvent) throws ServiceException {
        if (uniteEnseignement != null && uniteEnseignement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgAfficheCours').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez l'unité d'enseignement donc vous voulez affichez la listes des cours"));
        }
    }

    public void annuler() {
        uniteEnseignement = new UniteEnseignement();
    }

    public IUniteEnseignementService getUniteEnseignementService() {
        return uniteEnseignementService;
    }

    public void setUniteEnseignementService(IUniteEnseignementService uniteEnseignementService) {
        this.uniteEnseignementService = uniteEnseignementService;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    public List<UniteEnseignement> getUniteEnseignements() throws ServiceException {
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        return uniteEnseignements;
    }

    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
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

    public Long[] getIds() {
        if (uniteEnseignement != null && uniteEnseignement.getCourses() != null) {
            int i = 0;
            for (Iterator<Cours> iterator = uniteEnseignement.getCourses().iterator(); iterator.hasNext();) {
                Cours next = iterator.next();
                ids[i] = next.getId();
                i++;
            }
        }
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

}
