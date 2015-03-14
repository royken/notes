package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.service.IEvaluationDetailService;
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
public class EvaluationDetailServiceImpl implements IEvaluationDetailService {

    @Inject
    private IEvaluationDetailsDao detailsDao;

    public IEvaluationDetailsDao getDetailsDao() {
        return detailsDao;
    }

    public void setDetailsDao(IEvaluationDetailsDao detailsDao) {
        this.detailsDao = detailsDao;
    }
    
    

    @Override
    public EvaluationDetails saveOrUpdateEvaluationDetails(EvaluationDetails evaluationDetails) throws ServiceException{
        try {
            if (evaluationDetails.getId() == null) {
                evaluationDetails.setActive(1);
                return detailsDao.create(evaluationDetails);
            } else {
                return detailsDao.update(evaluationDetails);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteEvaluationDetails(Long id) throws ServiceException{
        try {
            EvaluationDetails details = detailsDao.findById(id);
            if(details != null){
                details.setActive(0);
                detailsDao.update(details);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public EvaluationDetails findEvaluationDetailsById(long id) throws ServiceException{
        try {
            return detailsDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<EvaluationDetails> getAllEvaluationDetails() throws ServiceException{
        try {
            return detailsDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

}
