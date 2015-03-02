package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.resource.ICycleResource;
import com.douwe.notes.service.ICycleService;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            return cycleService.saveOrUpdateCycle(cycle);
        } catch (ServiceException ex) {
            Logger.getLogger(CycleResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Cycle> getAllCycle() {
        try {
            return cycleService.getAllCycles();
        } catch (ServiceException ex) {
            Logger.getLogger(CycleResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Cycle getCycle(long id) {
        try {
            return cycleService.findCycleById(id);
        } catch (ServiceException ex) {
            Logger.getLogger(CycleResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Cycle updateCycle(long id, Cycle cycle) {
        try {
            Cycle c = cycleService.findCycleById(id);
            if(c != null){
                c.setNom(cycle.getNom());
                return cycleService.saveOrUpdateCycle(c);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(CycleResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteCycle(long id) {
        try {
            cycleService.deleteCycle(id);
        } catch (ServiceException ex) {
            Logger.getLogger(CycleResource.class.getName()).log(Level.SEVERE, null, ex);
        }
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
