package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
@Named
public class DepartementServiceImpl implements IDepartementService{
    @Inject
    private IDepartementDao departementDao;
    
    @Override
    public Departement saveOrUpdateDepartement(Departement departement) throws ServiceException{
        try {
            if (departement.getId() == null) {
                return departementDao.create(departement);
            } else {
                return departementDao.update(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteDepartement(Long id) throws ServiceException{
        try {
            Departement departement = departementDao.findById(id);
            if (departement != null) {
                departementDao.deleteActive(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Departement> getAllDepartements() throws ServiceException{
        try {
            return departementDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    @Override
    public Departement findDepartementById(long id) throws ServiceException{
        try {
            return departementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
        
    }

    @Override
    public List<Option> getAllOptions(Departement departement) throws ServiceException{
        try {
            return departementDao.getAllOptions(departement);
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
}
