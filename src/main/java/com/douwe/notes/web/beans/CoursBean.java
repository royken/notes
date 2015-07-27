/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.service.ITypeCoursService;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author root
 */
@ManagedBean
@RequestScoped
public class CoursBean {

    @EJB
    private ICoursService coursService;
    @EJB
    private ITypeCoursService typeCoursService;
    private Cours cours = new Cours();
    private List<Cours> courss;
    private List<TypeCours> typeCourss;  
    String id, credit;

    public CoursBean() {
        cours.setTypeCours(new TypeCours());
    }

    public void saveOrUpdateCours(ActionEvent actionEvent) throws ServiceException {
        if (cours != null && cours.getIntitule() != null) {
            //cours.setCredit(Integer.parseInt(credit));
            cours.setTypeCours(typeCoursService.findTypeCoursById(Integer.parseInt(id)));
            coursService.saveOrUpdateCours(cours);
            id = new String();
            if (cours.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", cours.getIntitule() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", cours.getIntitule() + " a été enregistré"));
            }
            credit = new String();
            cours = new Cours();
        }
    }

    public void deleteCours(ActionEvent actionEvent) throws ServiceException {
        if (cours != null && cours.getId() != null) {
            coursService.deleteCours(cours.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", cours.getIntitule() + " a été supprimé"));
            cours = new Cours();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (cours != null && cours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un cours avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (cours != null && cours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un cours avant de supprimer "));
        }
    }
        public void verifierCreditEtSave(ActionEvent actionEvent) throws ServiceException {
        if (cours != null && credit.isEmpty()==false && isInterger(credit.trim())==true) {
            saveOrUpdateCours(actionEvent);
        } else {
            credit = new String();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "le nombre de credit est un entier. exemple : 45"));
        }
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
        if (cours != null && cours.getVersion() != 0) {
            id = cours.getTypeCours().getId().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getCredit() {
//        if (cours != null && cours.getVersion() != 0)
//            credit = cours.getCredit().toString();
//        return credit;
//    }
//
//    public void setCredit(String credit) {
//        this.credit = credit;
//    }

    private boolean isInterger(String elt) {
        int i=0;
        while (i<elt.length()) {            
            if(!((elt.charAt(i)=='1')||(elt.charAt(i)=='2')||(elt.charAt(i)=='3')||(elt.charAt(i)=='4')||(elt.charAt(i)=='5')||(elt.charAt(i)=='6')||(elt.charAt(i)=='7')||(elt.charAt(i)=='8')||(elt.charAt(i)=='9')))
                return false;
            i++;
        }  
        return true;
    }

}
