package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.resource.ICycleResource;
import com.douwe.notes.service.ICycleService;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/cycles")
public class CycleResource implements ICycleResource{

    @EJB
    private IInsfrastructureService infranstructureService;
    
  
    @EJB
    private ICycleService cycleService;
    
    public Cycle createCycle(Cycle cycle) {
        return cycleService.saveOrUpdateCycle(cycle);
    }

    public List<Cycle> getAllCycle() {
        return cycleService.getAllCycles();
    }

    public Cycle getCycle(long id) {
        return cycleService.findCycleById(id);
    }

    public Cycle updateCycle(long id, Cycle cycle) {
        Cycle c = cycleService.findCycleById(id);
        if(c != null){
            c.setNom(cycle.getNom());
            return cycleService.saveOrUpdateCycle(c);
        }
        return null;
    }

    public void deleteCycle(long id) {
        cycleService.deleteCycle(id);
    }

    public IInsfrastructureService getInfranstructureService() {
        return infranstructureService;
    }

    public void setInfranstructureService(IInsfrastructureService infranstructureService) {
        this.infranstructureService = infranstructureService;
    }

    public ICycleService getCycleService() {
        return cycleService;
    }

    public void setCycleService(ICycleService cycleService) {
        this.cycleService = cycleService;
    }
    
    
    
}
