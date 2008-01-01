package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.service.IEvaluationDetailService;
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
public class EvaluationDetailServiceImpl implements IEvaluationDetailService {

    @Inject
    private IEvaluationDetailsDao detailsDao;

    public IEvaluationDetailsDao getDetailsDao() {
        return detailsDao;
    }

    public void setDetailsDao(IEvaluationDetailsDao detailsDao) {
        this.detailsDao = detailsDao;
    }
    
    

    public EvaluationDetails saveOrUpdateEvaluationDetails(EvaluationDetails evaluationDetails) {
        try {
            if (evaluationDetails.getId() == null) {
                return detailsDao.create(evaluationDetails);
            } else {
                return detailsDao.update(evaluationDetails);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteEvaluationDetails(Long id) {
        try {
            EvaluationDetails details = detailsDao.findById(id);
            if(details != null)
                detailsDao.delete(details);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EvaluationDetails findEvaluationDetailsById(long id) {
        try {
            return detailsDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<EvaluationDetails> getAllEvaluationDetails() {
        try {
            return detailsDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
