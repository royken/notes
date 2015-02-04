package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.resource.INiveauResource;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class NiveauResource implements INiveauResource{
    
    @Inject
    private IInsfrastructureService insfrastructureService;

    public Niveau createNiveau(Niveau niveau) {
        System.out.println("Le cycle "+niveau.getCycle());
        return insfrastructureService.saveOrUpdateNiveau(niveau);
    }

    public List<Niveau> getAllNiveaux() {
        return insfrastructureService.getAllNiveaux();
    }

    public Niveau getNiveau(long id) {
        return insfrastructureService.findNiveauById(id);
    }

    public Niveau updateNiveau(long id, Niveau cycle) {
        Niveau n = insfrastructureService.findNiveauById(id);
        if(n != null){
            n.setCode(cycle.getCode());
            n.setCycle(cycle.getCycle());
            return insfrastructureService.saveOrUpdateNiveau(n);
        }
        return null;
    }

    public void deleteNiveau(long id) {
        insfrastructureService.deleteCycle(id);
    }

    public IInsfrastructureService getInsfrastructureService() {
        return insfrastructureService;
    }

    public void setInsfrastructureService(IInsfrastructureService insfrastructureService) {
        this.insfrastructureService = insfrastructureService;
    }
    
}
