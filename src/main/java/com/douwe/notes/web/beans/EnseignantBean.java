package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.service.IEnseignantService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 
import org.primefaces.context.RequestContext;


@Named(value = "enseignantBean")
@RequestScoped
public class EnseignantBean {

    @EJB
    private IEnseignantService enseignantService;
    private Enseignant enseignant = new Enseignant();
    private List<Enseignant> enseignants; 
    

   
    public EnseignantBean() {        
    
    }


public void saveOrUpdateEnseignant(ActionEvent actionEvent) throws ServiceException {
        if (enseignant != null && enseignant.getNom() != null) {
            enseignantService.saveOrUpdateEnseignant(enseignant);
            if (enseignant.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", enseignant.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", enseignant.getNom()+ " a été enregistré"));
            }

            enseignant = new Enseignant();
        }
    }

    public void deleteEnseignant(ActionEvent actionEvent) throws ServiceException {
        if (enseignant != null && enseignant.getId() != null) {
            enseignantService.deleteEnseignant(enseignant.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", enseignant.getNom() + " a été supprimé"));
            enseignant = new Enseignant();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (enseignant != null && enseignant.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un enseignant avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (enseignant != null && enseignant.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un enseignant avant de supprimer "));
        }
    }

    public IEnseignantService getEnseignantService() {
        return enseignantService;
    }

    public void setEnseignantService(IEnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }
 
    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {        
        this.enseignant = enseignant;
    }

    public List<Enseignant> getEnseignants() throws ServiceException {        
        enseignants = enseignantService.getAllEnseignants();        
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }
}