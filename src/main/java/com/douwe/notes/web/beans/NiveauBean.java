/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author root
 */
@Named(value = "niveauBean")
@RequestScoped
public class NiveauBean {

    @EJB
    private IInsfrastructureService service;
    @EJB
    private INiveauService niveauService;
    private Niveau niveau = new Niveau();
    private List<Niveau> niveaux;
    private List<Cycle> cycles;
    private String message;
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

    public List<Niveau> findAll() {
        return service.getAllNiveaux();
    }

    public String saveOrUpdateNiveau() throws ServiceException {
        if (niveau != null) {            
            niveau.setCycle(service.findCycleById(Integer.parseInt(id)));                       
            service.saveOrUpdateNiveau(niveau);
            niveau = new Niveau();
            id = new String();                     
        }
        return "saveOrUpdateNiveau";
    }

    public String deleteNiveau() {
        if (niveau != null && niveau.getId() > 0) {          
            message = "Suppression reussi de "+niveau.getCode();
            service.deleteNiveau(niveau.getId());
            niveau = new Niveau();            
        }
        return "deleteNiveau";
    }

    public String choix(int n) {
        if (n == 1) {
            niveau=new Niveau();
            message="Enregistrement reussi ";
            return "saveNiveau";
        }

        else if (n == 2 && niveau!=null &&niveau.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateNiveau";
        }
        niveau = new Niveau();        
        return "niveau";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }


    public Converter getCycleConverter() {
        return cycleConverter;
    }

    public void setCycleConverter(Converter cycleConverter) {
        this.cycleConverter = cycleConverter;
    }

    private Converter cycleConverter = new Converter() {

        @Override
        public Cycle getAsObject(FacesContext context, UIComponent component, String value) {
            int id = Integer.parseInt(value.trim().substring(0, value.trim().indexOf("-")));
            Cycle c = service.findCycleById(id);            
            return c;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            Cycle c = (Cycle) value;            
            return c.getId() + "-" + c.getNom();

        }
    };

    public IInsfrastructureService getService() {
        return service;
    }

    public void setService(IInsfrastructureService service) {
        this.service = service;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Niveau> getNiveaux() {
        niveaux = findAll();
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Cycle> getCycles() {
        cycles = service.getAllCycles();
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
