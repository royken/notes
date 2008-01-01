package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 


@Named(value = "departementBean")
@RequestScoped
public class DepartementBean {

    @EJB
    private IDepartementService service;
    private Departement departement = new Departement();
    private List<Departement> departements; 
    private String message;

    /**
     * Creates a new instance of DepartementBean
     */
    public DepartementBean() {        
        message="";
    }


    public void saveOrUpdateDepartement() throws ServiceException {
        if (departement != null) {       
            //departement.setActive(1);
            service.saveOrUpdateDepartement(departement);
            departement = new Departement();                      
        }
        //return "saveOrUpdateDepartement";
    }

    public void deleteDepartement() throws ServiceException {
        if (departement != null) {  
            
            message = "Suppression reussi de "+departement.getCode();            
            service.deleteDepartement(departement.getId());
            departement = new Departement();            
        }
        //return "deleteDepartement";
    }

    public void choix(int n) {
        if (n == 1) {
            departement=new Departement();
            message="Enregistrement reussi ";
            //return "saveDepartement";
        }

        else if (n == 2 && departement!=null &&departement.getVersion() >= 1) {
            message="Mise à jour reussi ";
            //return "updateDepartement";
        }
        //departement = new Departement();        
        //return "departement";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }
    public IDepartementService getService() {
        return service;
    }

    public void setService(IDepartementService service) {
        this.service = service;
    }


    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {        
        this.departement = departement;
    }

    public List<Departement> getDepartements() throws ServiceException {        
        departements = service.getAllDepartements();
        departement = new Departement();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }
}
