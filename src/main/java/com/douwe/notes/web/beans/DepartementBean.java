package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Named(value = "departementBean")
@RequestScoped
public class DepartementBean {

    @EJB
    private IInsfrastructureService service;
    private Departement departement = new Departement();
    private List<Departement> departements;

    /**
     * Creates a new instance of DepartementBean
     */
    public DepartementBean() {

    }

    public List<Departement> findAll() {
        departements = service.getAllDepartements();
        return service.getAllDepartements();
    }

    public String saveOrUpdateDepartement() {
        if (departement != null) {
            System.err.println("Le departement "+departement.getCode()+" et la version "+departement.getVersion());
            service.saveOrUpdateDepartement(departement);            
            departement = new Departement();
        }
        return "saveOrUpdateDepartement";               
    }

    public String deleteDepartement() {
        if (departement != null) {
             System.err.println("Le departement "+departement.getId() +" code "+departement.getCode()+" et la version "+departement.getVersion());
            service.deleteDepartement(departement.getId());
            departement = new Departement();
        }
        return "deleteDepartement";
    }

    public IInsfrastructureService getService() {
        return service;
    }

    public void setService(IInsfrastructureService service) {
        this.service = service;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Departement> getDepartements() {
        departements = findAll();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

}
