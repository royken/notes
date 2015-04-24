package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Parcours;
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
public class CoursServiceImpl implements ICoursService {

    @Inject
    private ICoursDao coursDao;
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;
    
    @Inject
    private IParcoursDao parcoursDao;

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
    }
    
    

    @Override
    public Cours saveOrUpdateCours(Cours cours) throws ServiceException {

        try {
            if (cours.getId() == null) {
                cours.setActive(1);
                return coursDao.create(cours);
            } else {
                return coursDao.update(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteCours(Long id) throws ServiceException {
        try {
            Cours cours = coursDao.findById(id);
            if (cours != null) {
                cours.setActive(0);
                coursDao.update(cours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Cours findCoursById(long id) throws ServiceException {
        try {
            return coursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Cours> getAllCours() throws ServiceException {
        try {
            return coursDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Cours findByIntitule(String intitule) throws ServiceException {
        try {
            Cours cours = coursDao.findByIntitule(intitule);
            return cours;

        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Cours> findByParcoursAnnee(Long idParcours, Long idAnne) throws ServiceException {
        try {
            Parcours parcours = parcoursDao.findById(idParcours);
            
            if(parcours == null){
                throw  new ServiceException("Resource not found");
            }   
            AnneeAcademique academique = academiqueDao.findById(idAnne);
            if(academique == null){
                throw  new ServiceException("Resource not found");
            }
            
            return coursDao.findByParcoursAnnee(parcours, academique);
        } catch (DataAccessException ex) {
            Logger.getLogger(CoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
