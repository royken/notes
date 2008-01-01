package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.service.ICoursService;
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

    public Cours saveOrUpdateCours(Cours cours) {
        System.out.println("Exécution de la methode saveOrUpdate");
        try {
            if (cours.getId() == null) {
                return coursDao.create(cours);
            } else {
                return coursDao.update(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteCours(Long id) {
        try {
            Cours cours = coursDao.findById(id);
            if (cours != null) {
                coursDao.delete(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public Cours findCoursById(long id) {
        try {
            return coursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Cours> getAllCours() {
        try {
            return coursDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
