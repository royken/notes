package com.douwe.notes.web.beans;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.service.ITypeCoursService;
import static java.awt.SystemColor.text;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 


@Named(value = "typeCoursBean")
@RequestScoped
public class TypeCoursBean {

    @EJB
    private ITypeCoursService service;
    private TypeCours typeCours = new TypeCours();
    private List<TypeCours> typeCourss; 
    private String message;

    /**
     * Creates a new instance of TypeCoursBean
     */
    public TypeCoursBean() {        
        message="";
    }


    public String saveOrUpdateTypeCours() {
        if (typeCours != null) {            
            service.saveOrUpdateTpyeCours(typeCours);
            typeCours = new TypeCours();                      
        }
        return "saveOrUpdateTypeCours";
    }

    public String deleteTypeCours() {
        if (typeCours != null) {          
            message = "Suppression reussi de "+typeCours.getNom();
            service.deleteTypeCours(typeCours.getId());
            typeCours = new TypeCours();            
        }
        return "deleteTypeCours";
    }

    public String choix(int n) {
        if (n == 1) {
            typeCours=new TypeCours();
            message="Enregistrement reussi ";
            return "saveTypeCours";
        }

        else if (n == 2 && typeCours!=null &&typeCours.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateTypeCours";
        }
        typeCours = new TypeCours();        
        return "typeCours";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
    }
    public ITypeCoursService getService() {
        return service;
    }

    public void setService(ITypeCoursService service) {
        this.service = service;
    }


    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {        
        this.typeCours = typeCours;
    }

    public List<TypeCours> getTypeCourss() {        
        typeCourss = service.getAllTypeCours();
        return typeCourss;
    }

    public void setTypeCourss(List<TypeCours> typeCourss) {
        this.typeCourss = typeCourss;
    }
}
