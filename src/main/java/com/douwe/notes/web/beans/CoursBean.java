/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.service.ITypeCoursService;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.ICoursService;
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
@Named(value = "coursBean")
@RequestScoped
public class CoursBean {

    @EJB
    private ICoursService coursService;
    @EJB
    private ITypeCoursService typeCoursService;
    private Cours cours = new Cours();
    private List<Cours> courss;
    private List<TypeCours> typeCourss;
    private String message;
    String id;

    public CoursBean() {
        cours.setTypeCours(new TypeCours());
    }

    public List<Cours> findAll() throws ServiceException {
        return coursService.getAllCours();
    }

    public String saveOrUpdateCours() throws ServiceException {
        if (cours != null) { 
            cours.setTypeCours(typeCoursService.findTypeCoursById(Integer.parseInt(id)));                        
            coursService.saveOrUpdateCours(cours);
            cours = new Cours();
            id = new String();                     
        }
        return "saveOrUpdateCours";
    }

    public String deleteCours() throws ServiceException {
        if (cours != null && cours.getId() > 0) {          
            message = "Suppression reussi de "+cours.getIntitule();
            coursService.deleteCours(cours.getId());
            cours = new Cours();            
        }
        return "deleteCours";
    }

    public String choix(int n) {
        if (n == 1) {
            cours=new Cours();
            message="Enregistrement reussi ";
            return "saveCours";
        }

        else if (n == 2 && cours!=null &&cours.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateCours";
        }
        cours = new Cours();        
        return "cours";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }

    public ITypeCoursService getTypeCoursService() {
        return typeCoursService;
    }

    public void setTypeCoursService(ITypeCoursService typeCoursService) {
        this.typeCoursService = typeCoursService;
    }

    public List<Cours> getCourss() throws ServiceException {
        courss = coursService.getAllCours();
        return courss;
    }

    public void setCourss(List<Cours> courss) {
        this.courss = courss;
    }
    

    public List<TypeCours> getTypeCourss() throws ServiceException {
        typeCourss = typeCoursService.getAllTypeCours();
        return typeCourss;
    }

    public void setTypeCourss(List<TypeCours> typeCourss) {
        this.typeCourss = typeCourss;
    }

    public String getId() {
        if(cours!=null && cours.getVersion() != 0){        
        id = cours.getTypeCours().getId().toString();        }        
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
