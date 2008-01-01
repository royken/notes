package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ICycleDao;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.service.ICycleService;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class CycleServiceImpl implements  ICycleService{
    @Inject
    private ICycleDao cycleDao;

    
    public ICycleDao getCycleDao() {
        return cycleDao;
    }

    public void setCycleDao(ICycleDao cycleDao) {
        this.cycleDao = cycleDao;
    }
    
    public Cycle saveOrUpdateCycle(Cycle cycle) {
        try {
            if (cycle.getId() == null) {
                return cycleDao.create(cycle);
            } else {
                return cycleDao.update(cycle);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteCycle(long id) {
        try {
            Cycle c = cycleDao.findById(id);
            if (c != null) {
                cycleDao.delete(c);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cycle findCycleById(long id) {
        try {
            return cycleDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Cycle> getAllCycles() {
        try {
            return cycleDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    
}
