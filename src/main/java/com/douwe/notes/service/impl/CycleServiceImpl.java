package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ICycleDao;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.service.ICycleService;
import com.douwe.notes.service.ServiceException;
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
    
    @Override
    public Cycle saveOrUpdateCycle(Cycle cycle) throws ServiceException{
        try {
            if (cycle.getId() == null) {
                cycle.setActive(1);
                return cycleDao.create(cycle);
            } else {
                return cycleDao.update(cycle);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteCycle(long id) throws ServiceException{
        try {
            Cycle c = cycleDao.findById(id);
            if (c != null) {
                c.setActive(0);
                cycleDao.update(c);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Cycle findCycleById(long id) throws ServiceException{
        try {
            return cycleDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Cycle> getAllCycles() throws ServiceException{
        try {
            return cycleDao.getAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    
}
