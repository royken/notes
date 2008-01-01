package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.service.IEvaluationService;
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
public class EvaluationServiceImpl implements IEvaluationService{
    
    @Inject
    private IEvaluationDao evaluationDao;

    public IEvaluationDao getEvaluationDao() {
        return evaluationDao;
    }

    public void setEvaluationDao(IEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }
    
    

    public Evaluation saveOrUpdateEvaluation(Evaluation evaluation) {
        try {
        if(evaluation.getId() != null){
            
                return evaluationDao.create(evaluation);
            
        }
        else{
            return evaluationDao.update(evaluation);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }

    public void deleteEvaluation(long id) {
        try {
            Evaluation evaluation = evaluationDao.findById(id);
            if(evaluation != null){
                evaluationDao.delete(evaluation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Evaluation findEvaluationById(long id) {
        try {
            return evaluationDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Evaluation> getAllEvaluations() {
        try {
            return evaluationDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
