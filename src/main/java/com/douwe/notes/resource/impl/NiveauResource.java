package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.resource.INiveauResource;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.INiveauService;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/niveaux")
public class NiveauResource implements INiveauResource{
    
    @Inject
    private IInsfrastructureService insfrastructureService;
    
    @EJB
    private INiveauService service;

    public INiveauService getService() {
        return service;
    }

    public void setService(INiveauService service) {
        this.service = service;
    }
    
    

    public Niveau createNiveau(Niveau niveau) {
        System.out.println("Le cycle "+niveau.getCycle());
        return service.saveOrUpdateNiveau(niveau);
    }

    public List<Niveau> getAllNiveaux() {
        return service.getAllNiveaux();
    }

    public Niveau getNiveau(long id) {
        return service.findNiveauById(id);
    }

    public Niveau updateNiveau(long id, Niveau niveau) {
        Niveau n = service.findNiveauById(id);
        if(n != null){
            n.setCode(niveau.getCode());
            n.setCycle(niveau.getCycle());
            return service.saveOrUpdateNiveau(n);
        }
        return null;
    }

    public void deleteNiveau(long id) {
        service.deleteNiveau(id);
    }

    public IInsfrastructureService getInsfrastructureService() {
        return insfrastructureService;
    }

    public void setInsfrastructureService(IInsfrastructureService insfrastructureService) {
        this.insfrastructureService = insfrastructureService;
    }
    
}
