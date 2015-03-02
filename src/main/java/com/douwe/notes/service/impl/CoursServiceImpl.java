package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.service.ICoursService;
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
public class CoursServiceImpl implements ICoursService{
    @Inject
    private ICoursDao coursDao;

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public Cours saveOrUpdateCours(Cours cours) throws ServiceException{
        
        try {
            if (cours.getId() == null) {
                return coursDao.create(cours);
            } else {
                return coursDao.update(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public void deleteCours(Long id) throws ServiceException{
        try {
            Cours cours = coursDao.findById(id);
            if (cours != null) {
                coursDao.deleteActive(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Cours findCoursById(long id) throws ServiceException{
        try {
            return coursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Cours> getAllCours() throws ServiceException{
        try {
            return coursDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
}
