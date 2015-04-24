package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.service.IEvaluationService;
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
public class EvaluationServiceImpl implements IEvaluationService {
    
    @Inject
    private IEvaluationDao evaluationDao;
    
    @Inject
    private ICoursDao coursDao;
    
    public IEvaluationDao getEvaluationDao() {
        return evaluationDao;
    }
    
    public void setEvaluationDao(IEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }
    
    
    
    @Override
    public Evaluation saveOrUpdateEvaluation(Evaluation evaluation) throws ServiceException {
        try {
            if (evaluation.getId() == null) {
                evaluation.setActive(1);
                return evaluationDao.create(evaluation);
                
            } else {
                return evaluationDao.update(evaluation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
    @Override
    public void deleteEvaluation(long id) throws ServiceException {
        try {
            Evaluation evaluation = evaluationDao.findById(id);
            if (evaluation != null) {
                evaluation.setActive(0);
                evaluationDao.update(evaluation);
                //evaluationDao.delete(evaluation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
        
    }
    
    @Override
    public Evaluation findEvaluationById(long id) throws ServiceException {
        try {
            return evaluationDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
    @Override
    public List<Evaluation> getAllEvaluations() throws ServiceException {
        try {
            return evaluationDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Evaluation> getAllEvaluationByCours(Long id) throws ServiceException {
        try {
            Cours cours = coursDao.findById(id);
            return evaluationDao.evaluationForCourses(cours);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
