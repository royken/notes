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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

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

    public String saveOrUpdateSemestre() throws ServiceException {
        if (semestre != null) { 
            semestre.setNiveau(niveauService.findNiveauById(Integer.parseInt(id)));                        
            semestreService.saveOrUpdateSemestre(semestre);
            semestre = new Semestre();
            id = new String();                     
        }
        return "saveOrUpdateSemestre";
    }

    public String deleteSemestre() throws ServiceException {
        if (semestre != null && semestre.getId() > 0) {          
            message = "Suppression reussi de "+semestre.getIntitule();
            semestreService.deleteSemestre(semestre.getId());
            semestre = new Semestre();            
        }
        return "deleteSemestre";
    }

    public String choix(int n) {
        if (n == 1) {
            semestre=new Semestre();
            message="Enregistrement reussi ";
            return "saveSemestre";
        }

        else if (n == 2 && semestre!=null &&semestre.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateSemestre";
        }
        semestre = new Semestre();        
        return "semestre";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
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
        if(semestre!=null && semestre.getVersion() != 0){        
        id = semestre.getNiveau().getId().toString();        }        
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
