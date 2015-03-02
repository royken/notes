package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.ServiceException;
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
public class ParcoursServiceImpl implements IParcoursService {

    @Inject
    private IParcoursDao parcoursDao;

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
    }

    public Parcours saveOrUpdateParcours(Parcours parcours) throws ServiceException{
        try {
            if (parcours.getId() != null) {
                return parcoursDao.create(parcours);
            } else {
                return parcoursDao.update(parcours);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }

    }

    public void deleteParcours(Long id) throws ServiceException{
        try {
            Parcours parcours = parcoursDao.findById(id);
            if (parcours != null) {
                parcoursDao.deleteActive(parcours);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Parcours findParcoursById(long id) throws ServiceException{
        try {
            return parcoursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Parcours> getAllParcours() throws ServiceException{
        try {
            return parcoursDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

}
