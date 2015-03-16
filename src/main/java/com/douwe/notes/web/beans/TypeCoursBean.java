package com.douwe.notes.web.beans;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.service.ITypeCoursService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 
import org.primefaces.context.RequestContext;


@Named(value = "typeCoursBean")
@RequestScoped
public class TypeCoursBean {

    @EJB
    private ITypeCoursService typeCoursService;
    private TypeCours typeCours = new TypeCours();
    private List<TypeCours> typeCourss; 
    

   
    public TypeCoursBean() {        
    
    }


public void saveOrUpdateTypeCours(ActionEvent actionEvent) throws ServiceException {
        if (typeCours != null && typeCours.getNom() != null) {
            typeCoursService.saveOrUpdateTpyeCours(typeCours);
            if (typeCours.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", typeCours.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", typeCours.getNom()+ " a été enregistré"));
            }

            typeCours = new TypeCours();
        }
    }

    public void deleteTypeCours(ActionEvent actionEvent) throws ServiceException {
        if (typeCours != null && typeCours.getId() != null) {
            typeCoursService.deleteTypeCours(typeCours.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", typeCours.getNom() + " a été supprimé"));
            typeCours = new TypeCours();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (typeCours != null && typeCours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un type de cours avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (typeCours != null && typeCours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un type de cours avant de supprimer "));
        }
    }

    public ITypeCoursService getTypeCoursService() {
        return typeCoursService;
    }

    public void setTypeCoursService(ITypeCoursService typeCoursService) {
        this.typeCoursService = typeCoursService;
    }
 
    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {        
        this.typeCours = typeCours;
    }

    public List<TypeCours> getTypeCourss() throws ServiceException {        
        typeCourss = typeCoursService.getAllTypeCours();        
        return typeCourss;
    }

    public void setTypeCourss(List<TypeCours> typeCourss) {
        this.typeCourss = typeCourss;
    }
}