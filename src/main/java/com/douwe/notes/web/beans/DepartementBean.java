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
import org.primefaces.context.RequestContext;


@Named(value = "departementBean")
@RequestScoped
public class DepartementBean {

    @EJB
    private IDepartementService departementService;
    private Departement departement = new Departement();
    private List<Departement> departements; 
    

   
    public DepartementBean() {        
    
    }


public void saveOrUpdateDepartement(ActionEvent actionEvent) throws ServiceException {
        if (departement != null && departement.getCode() != null) {
            departementService.saveOrUpdateDepartement(departement);
            if (departement.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", departement.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", departement.getCode()+ " a été enregistré"));
            }

            departement = new Departement();
        }
    }

    public void deleteDepartement(ActionEvent actionEvent) throws ServiceException {
        if (departement != null && departement.getId() != null) {
            departementService.deleteDepartement(departement.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", departement.getCode() + " a été supprimé"));
            departement = new Departement();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (departement != null && departement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un departement avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (departement != null && departement.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un departement avant de supprimer "));
        }
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }
 
    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {        
        this.departement = departement;
    }

    public List<Departement> getDepartements() throws ServiceException {        
        departements = departementService.getAllDepartements();        
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }
}