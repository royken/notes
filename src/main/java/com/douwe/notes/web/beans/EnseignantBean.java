package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.service.IEnseignantService;
import com.douwe.notes.service.ServiceException;
import static java.awt.SystemColor.text;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 

@Named(value = "enseignantBean")
@RequestScoped
public class EnseignantBean {

    @EJB
    private IEnseignantService service;
    private Enseignant enseignant = new Enseignant();
    private List<Enseignant> enseignants; 
    private String message;

    /**
     * Creates a new instance of EnseignantBean
     */
    public EnseignantBean() {        
        message="";
    }


    public String saveOrUpdateEnseignant() throws ServiceException {
        if (enseignant != null) {            
            service.saveOrUpdateEnseignant(enseignant);
            enseignant = new Enseignant();                      
        }
        return "saveOrUpdateEnseignant";
    }

    public String deleteEnseignant() throws ServiceException {
        if (enseignant != null) {          
            message = "Suppression reussi de "+enseignant.getNom();
            service.deleteEnseignant(enseignant.getId());
            enseignant = new Enseignant();            
        }
        return "deleteEnseignant";
    }

    public String choix(int n) {
        if (n == 1) {
            enseignant=new Enseignant();
            message="Enregistrement reussi ";
            return "saveEnseignant";
        }

        else if (n == 2 && enseignant!=null &&enseignant.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateEnseignant";
        }
        enseignant = new Enseignant();        
        return "enseignant";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }
    public IEnseignantService getService() {
        return service;
    }

    public void setService(IEnseignantService service) {
        this.service = service;
    }


    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {        
        this.enseignant = enseignant;
    }

    public List<Enseignant> getEnseignants() throws ServiceException {        
        enseignants = service.getAllEnseignants();
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }
}
