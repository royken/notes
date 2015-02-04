package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.resource.ICycleResource;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CycleResource implements ICycleResource{

    @EJB
    private IInsfrastructureService infranstructureService;
    
    public Cycle createCycle(Cycle cycle) {
        return infranstructureService.saveOrUpdateCycle(cycle);
    }

    public List<Cycle> getAllCycle() {
        return infranstructureService.getAllCycles();
    }

    public Cycle getCycle(long id) {
        return infranstructureService.findCycleById(id);
    }

    public Cycle updateCycle(long id, Cycle cycle) {
        Cycle c = infranstructureService.findCycleById(id);
        if(c != null){
            c.setNom(cycle.getNom());
            return infranstructureService.saveOrUpdateCycle(c);
        }
        return null;
    }

    public void deleteCycle(long id) {
        infranstructureService.deleteCycle(id);
    }

    public IInsfrastructureService getInfranstructureService() {
        return infranstructureService;
    }

    public void setInfranstructureService(IInsfrastructureService infranstructureService) {
        this.infranstructureService = infranstructureService;
    }
    
    
    
}
