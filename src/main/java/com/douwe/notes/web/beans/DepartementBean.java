package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Named(value = "departementBean")
@RequestScoped
public class DepartementBean {
    
    @EJB
    private IInsfrastructureService service;

    /**
     * Creates a new instance of DepartementBean
     */
    public DepartementBean() {
        
    }
    
    public List<Departement> findAll(){
        return service.getAllDepartements();
    }

    public IInsfrastructureService getService() {
        return service;
    }

    public void setService(IInsfrastructureService service) {
        this.service = service;
    }
    
    
    
}
