/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.IOptionService;
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
@Named(value = "optionBean")
@RequestScoped
public class OptionBean {

    @EJB
    private IOptionService optionService;
    @EJB
    private IDepartementService departementService;
    private Option option = new Option();
    private List<Option> options;
    private List<Departement> departements;
    private String message;
    String id;

    public OptionBean() {
        option.setDepartement(new Departement());
    }

    public List<Option> findAll() throws ServiceException {
        return optionService.getAllOptions();
    }

    public String saveOrUpdateOption() throws ServiceException {
        if (option != null) { 
            option.setDepartement(departementService.findDepartementById(Integer.parseInt(id)));                        
            optionService.saveOrUpdateOption(option);
            option = new Option();
            id = new String();                     
        }
        return "saveOrUpdateOption";
    }

    public String deleteOption() throws ServiceException {
        if (option != null && option.getId() > 0) {          
            message = "Suppression reussi de "+option.getCode();
            optionService.deleteOption(option.getId());
            option = new Option();            
        }
        return "deleteOption";
    }

    public String choix(int n) {
        if (n == 1) {
            option=new Option();
            message="Enregistrement reussi ";
            return "saveOption";
        }

        else if (n == 2 && option!=null &&option.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateOption";
        }
        option = new Option();        
        return "option";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    public List<Option> getOptions() throws ServiceException {
        options = optionService.getAllOptions();
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    

    public List<Departement> getDepartements() throws ServiceException {
        departements = departementService.getAllDepartements();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public String getId() {
        if(option!=null && option.getVersion() != 0){        
        id = option.getDepartement().getId().toString();        }        
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
