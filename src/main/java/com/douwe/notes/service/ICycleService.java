package com.douwe.notes.service;

import com.douwe.notes.entities.Cycle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ICycleService {
    
    public Cycle saveOrUpdateCycle(Cycle cycle);
    
    public void deleteCycle(long id);
    
    public Cycle findCycleById(long id);
    
    public List<Cycle> getAllCycles();
    
}
