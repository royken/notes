/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.ISemestreService;
import com.douwe.notes.service.ServiceException;
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
@Named(value = "semestreBean")
@RequestScoped
public class SemestreBean {

    @EJB
    private ISemestreService semestreService;
    @EJB
    private IInsfrastructureService niveauService;
    private Semestre semestre = new Semestre();
    private List<Semestre> semestres;
    private List<Niveau> niveaux;
    private String message;
    String id;

    public SemestreBean() {
        semestre.setNiveau(new Niveau());
    }

    public List<Semestre> findAll() throws ServiceException {
        return semestreService.getAllSemestre();
    }

    public void saveOrUpdateSemestre(ActionEvent actionEvent) throws ServiceException {
        if (semestre != null && semestre.getIntitule() != null) {
            semestre.setNiveau(niveauService.findNiveauById(Integer.parseInt(id)));
            semestreService.saveOrUpdateSemestre(semestre);
            if (semestre.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", semestre.getIntitule() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", semestre.getIntitule() + " a été enregistré"));
            }

            semestre = new Semestre();
            id = new String(); 
        }
    }

    public void deleteSemestre(ActionEvent actionEvent) throws ServiceException {
        if (semestre != null && semestre.getId() != null) {
            semestreService.deleteSemestre(semestre.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", semestre.getIntitule() + " a été supprimé"));
            semestre = new Semestre();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (semestre != null && semestre.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un semestre avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (semestre != null && semestre.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un semestre avant de supprimer "));
        }
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public ISemestreService getSemestreService() {
        return semestreService;
    }

    public void setSemestreService(ISemestreService semestreService) {
        this.semestreService = semestreService;
    }

    public IInsfrastructureService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(IInsfrastructureService niveauService) {
        this.niveauService = niveauService;
    }

    public List<Niveau> getNiveaux() {
        niveaux = niveauService.getAllNiveaux();
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Semestre> getSemestres() throws ServiceException {
        semestres = semestreService.getAllSemestre();
        return semestres;
    }

    public void setSemestres(List<Semestre> semestres) {
        this.semestres = semestres;
    }

    public String getId() {
        if (semestre != null && semestre.getVersion() != 0) {
            id = semestre.getNiveau().getId().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
