/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.service.ICycleService;
import com.douwe.notes.service.INiveauService;
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
@Named(value = "niveauBean")
@RequestScoped
public class NiveauBean {
   
    @EJB
    private INiveauService niveauService;
    @EJB
    private ICycleService cycleService;
    private Niveau niveau = new Niveau();
    private List<Niveau> niveaux;
    private List<Cycle> cycles;  
    String id;

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }
    
    

    public NiveauBean() {
        niveau.setCycle(new Cycle());
    }

public void saveOrUpdateNiveau(ActionEvent actionEvent) throws ServiceException {
        if (niveau != null && niveau.getCode() != null) {
            niveau.setCycle(cycleService.findCycleById(Integer.parseInt(id)));                       
            niveauService.saveOrUpdateNiveau(niveau);                        
            if (niveau.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", niveau.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", niveau.getCode()+ " a été enregistré"));
            }
            id = new String();
            niveau = new Niveau();
        }
    }

    public void deleteNiveau(ActionEvent actionEvent) throws ServiceException {
        if (niveau != null && niveau.getId() != null) {
            niveauService.deleteNiveau(niveau.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", niveau.getCode() + " a été supprimé"));
            niveau = new Niveau();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (niveau != null && niveau.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un niveau avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (niveau != null && niveau.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un niveau avant de supprimer "));
        }
    }   

    public ICycleService getCycleService() {
        return cycleService;
    }

    public void setCycleService(ICycleService cycleService) {
        this.cycleService = cycleService;
    }

  
    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Niveau> getNiveaux() throws ServiceException {
        niveaux = niveauService.getAllNiveaux();
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Cycle> getCycles() throws ServiceException {
        cycles = cycleService.getAllCycles();
        return cycles;
    }

    public void setCycles(List<Cycle> cycles) {
        this.cycles = cycles;
    }

    public String getId() {
        if(niveau!=null && niveau.getVersion() != 0){        
        id = niveau.getCycle().getId().toString();        }        
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
