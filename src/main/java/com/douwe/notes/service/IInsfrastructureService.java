package com.douwe.notes.service;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Departement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Local
public interface IInsfrastructureService {
    
    public Departement saveOrUpdateDepartement(Departement departement);
    
    public void deleteDepartement(Long id);
    
    public Departement findDepartementById(long id);
    
    public List<Departement> getAllDepartements();
    
    // Les operations en relation avec les cycles
    
    public Cycle saveOrUpdateCycle(Cycle cycle);
    
    public void deleteCycle(long id);
    
    public Cycle findCycleById(long id);
    
    public List<Cycle> getAllCycles();
    
}
