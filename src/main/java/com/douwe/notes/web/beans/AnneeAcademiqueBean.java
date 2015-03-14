package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ServiceException;
import static java.awt.SystemColor.text;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named(value = "anneeAcademiqueBean")
@RequestScoped
public class AnneeAcademiqueBean {

    @EJB
    private IAnneeAcademiqueService service;
    private AnneeAcademique anneeAcademique = new AnneeAcademique();
    private List<AnneeAcademique> anneeAcademiques;
    private String message;

    /**
     * Creates a new instance of AnneeAcademiqueBean
     */
    public AnneeAcademiqueBean() {
        message = "";
    }


    public String saveOrUpdateAnneeAcademique() throws ServiceException{
        if (anneeAcademique != null&& anneeAcademique.getDebut().before(anneeAcademique.getFin())) {
            service.saveOrUpdateAnnee(anneeAcademique);
            anneeAcademique = new AnneeAcademique();
        }        
        return "saveOrUpdateAnneeAcademique";
    }

    public String deleteAnneeAcademique() throws ServiceException {
        if (anneeAcademique != null&& anneeAcademique.getDebut().getYear()<= anneeAcademique.getFin().getYear()&& anneeAcademique.getDebut().getMonth()<=anneeAcademique.getFin().getMonth() && anneeAcademique.getDebut().getDay()<anneeAcademique.getFin().getDay()) {
            message = "Suppression reussi";
            service.deleteAnnee(anneeAcademique.getId());
            anneeAcademique = new AnneeAcademique();
        }        
        return "deleteAnneeAcademique";
    }

    public String choix(int n) {
        if (n == 1) {
            anneeAcademique = new AnneeAcademique();
            message = "Enregistrement reussi ";
            return "saveAnneeAcademique";
        } else if (n == 2 && anneeAcademique != null && anneeAcademique.getVersion() >= 1) {
            message = "Mise à jour reussi ";
            return "updateAnneeAcademique";
        }
        anneeAcademique = new AnneeAcademique();
        return "anneeAcademique";
    }

    public void notification(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Succes", message));
    }

    public IAnneeAcademiqueService getService() {
        return service;
    }

    public void setService(IAnneeAcademiqueService service) {
        this.service = service;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = service.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public int inc(int n) {
        return ++n;
    }
}
