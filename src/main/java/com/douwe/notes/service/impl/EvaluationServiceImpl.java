package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEvaluationDao;
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
    
    public IEvaluationDao getEvaluationDao() {
        return evaluationDao;
    }
    
    public void setEvaluationDao(IEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }
    
    @Override
    public Evaluation saveOrUpdateEvaluation(Evaluation evaluation) throws ServiceException {
        try {
            if (evaluation.getId() != null) {
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
                evaluationDao.delete(evaluation);
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
    
}
