package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

@Named(value = "anneeAcademiqueBean")
@RequestScoped
public class AnneeAcademiqueBean {

    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
    private AnneeAcademique anneeAcademique = new AnneeAcademique();
    private List<AnneeAcademique> anneeAcademiques;    

    /**
     * Creates a new instance of AnneeAcademiqueBean
     */
    public AnneeAcademiqueBean() {        
    }

public void saveOrUpdateAnneeAcademique(ActionEvent actionEvent) throws ServiceException {     
        if (anneeAcademique != null && anneeAcademique.getDebut().before(anneeAcademique.getFin())) {
            anneeAcademiqueService.saveOrUpdateAnnee(anneeAcademique);
            if (anneeAcademique.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", anneeAcademique.getDebut().toString().substring(anneeAcademique.getDebut().toString().length() -4)+" / "+anneeAcademique.getFin().toString().substring(anneeAcademique.getFin().toString().length() -4)+" a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", anneeAcademique.getDebut().toString().substring(anneeAcademique.getDebut().toString().length() -4)+" / "+anneeAcademique.getFin().toString().substring(anneeAcademique.getFin().toString().length() -4) + " a été enregistré"));
            }

            anneeAcademique = new AnneeAcademique();
        }
    }

    public void deleteAnneeAcademique(ActionEvent actionEvent) throws ServiceException {
        if (anneeAcademique != null && anneeAcademique.getDebut().before(anneeAcademique.getFin())) {            
            anneeAcademiqueService.deleteAnnee(anneeAcademique.getId());            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", anneeAcademique.getDebut().toString().substring(anneeAcademique.getDebut().toString().length() -4)+" / "+anneeAcademique.getFin().toString().substring(anneeAcademique.getFin().toString().length() -4) + " a été supprimé"));
            anneeAcademique = new AnneeAcademique();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (anneeAcademique != null && anneeAcademique.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une année académique avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (anneeAcademique != null && anneeAcademique.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une année académique avant de supprimer "));
        }
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public int inc(int n) {
        return ++n;
    }
}
