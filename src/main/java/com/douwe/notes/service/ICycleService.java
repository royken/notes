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
    
    public Cycle saveOrUpdateCycle(Cycle cycle) throws ServiceException;
    
    public void deleteCycle(long id) throws ServiceException;
    
    public Cycle findCycleById(long id) throws ServiceException;
    
    public List<Cycle> getAllCycles() throws ServiceException;
    
}
