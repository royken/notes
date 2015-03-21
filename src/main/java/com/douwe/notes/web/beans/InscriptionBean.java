/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
import java.util.Iterator;
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
@Named(value = "inscriptionBean")
@RequestScoped
public class InscriptionBean {

    @EJB
    private IInscriptionService inscriptionService;
    @EJB
    private IEtudiantService etudiantService;
    @EJB
    private IParcoursService parcoursService;
    @EJB
    private IAnneeAcademiqueService academiqueService;

    private Inscription inscription = new Inscription();
    private List<Inscription> inscriptions = new LinkedList<Inscription>();
   
    private List<Etudiant> etudiants = new LinkedList<Etudiant>();
    private List<Parcours> parcourses = new LinkedList<Parcours>();
    private List<AnneeAcademique> anneeAcademiques = new LinkedList<AnneeAcademique>();
   
    Long idP=0L,idE=0L,idA=0L;

    public InscriptionBean() throws ServiceException {               
    }

    public void saveOrUpdateInscription(ActionEvent actionEvent) throws ServiceException {                           
            inscription.setActive(1);            
            inscription.setParcours(parcoursService.findParcoursById(idP));
            inscription.setEtudiant(etudiantService.findEtudiantById(idE));
            inscription.setAnneeAcademique(academiqueService.findAnneeById(idA));
            inscriptionService.saveOrUpdateInscription(inscription);           
            if (inscription.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", inscription.getEtudiant().getNom() + " a été inscrit en " + inscription.getParcours().getOption().getCode() + " pour l'année académique " + inscription.getAnneeAcademique()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", inscription.getEtudiant().getNom() + " a été inscrit en " + inscription.getParcours().getOption().getCode() + " pour l'année académique " + inscription.getAnneeAcademique()));
            }
             inscription = new Inscription();            
        }
    

    public void deleteInscription(ActionEvent actionEvent) throws ServiceException {
        if (inscription != null && inscription.getId() != null) {
            inscriptionService.deleteInscription(inscription.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Operation reussie", inscription.getEtudiant().getNom() + " a été désinscrit en " + inscription.getParcours().getOption().getCode() + " pour l'année académique " + inscription.getAnneeAcademique()));
            inscription = new Inscription();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (inscription != null && inscription.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez une inscription avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (inscription != null && inscription.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une inscription avant de supprimer "));
        }
    }

    public IInscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(IInscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    public IEtudiantService getEtudiantService() {
        return etudiantService;
    }

    public void setEtudiantService(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public IAnneeAcademiqueService getAcademiqueService() {
        return academiqueService;
    }

    public void setAcademiqueService(IAnneeAcademiqueService academiqueService) {
        this.academiqueService = academiqueService;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public List<Inscription> getInscriptions() throws ServiceException {
        inscriptions = inscriptionService.getAllInscriptions();
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public List<Etudiant> getEtudiants() throws ServiceException {
        etudiants = etudiantService.getAllEtudiant();
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Parcours> getParcourses() throws ServiceException {
        parcourses=parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = academiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public Long getIdP() {
        if (inscription!=null && inscription.getParcours()!=null) {
       idP=inscription.getParcours().getId();
        }
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public Long getIdE() {
         if (inscription!=null && inscription.getEtudiant()!=null) {
            idE=inscription.getEtudiant().getId();
        }
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public Long getIdA() {
         if (inscription!=null && inscription.getAnneeAcademique()!=null) {
            idA=inscription.getAnneeAcademique().getId();
        }
        return idA;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
    }
  
    
    
}
