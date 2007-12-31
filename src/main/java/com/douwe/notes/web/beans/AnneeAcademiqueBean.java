/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author root
 */
@Named(value = "anneeAcademiqueBean")
@RequestScoped
public class AnneeAcademiqueBean {
    @EJB
    private IInsfrastructureService service;    
    private List<AnneeAcademique> anneeAcademiques;
    private  AnneeAcademique anneeAcademique =  new AnneeAcademique();

    public AnneeAcademiqueBean() {
    }
    public void saveOrUpdateAnneeAcademiques() {
        if (anneeAcademique != null) {
            service.saveOrUpdateAnneeAcademique(anneeAcademique);
            anneeAcademique = new AnneeAcademique();
        }
    }

    public void deleteAnneeAcademique() {
        if (anneeAcademique != null) {
            service.deleteAnneeAcademique(anneeAcademique.getId());
            anneeAcademique = new AnneeAcademique();
        }
    }

    public IInsfrastructureService getService() {
        return service;
    }

    public void setService(IInsfrastructureService service) {
        this.service = service;
    }

    public List<AnneeAcademique> getAnneeAcademiques() {
        anneeAcademiques = service.getAllAnneeAcademiques();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }
}
