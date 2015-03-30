package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.dao.ITypeCoursDao;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.TypeCours;
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
    
    @Inject
    private IEvaluationDao evaluationDao;

    @Inject
    private IEvaluationDetailsDao evaluationDetailsDao;
    
    @Inject
    private ITypeCoursDao typeCoursDao;

    public ITypeCoursDao getTypeCoursDao() {
        return typeCoursDao;
    }

    public void setTypeCoursDao(ITypeCoursDao typeCoursDao) {
        this.typeCoursDao = typeCoursDao;
    }

    public IEvaluationDetailsDao getDetailsDao() {
        return detailsDao;
    }

    public void setDetailsDao(IEvaluationDetailsDao detailsDao) {
        this.detailsDao = detailsDao;
    }
    
    public IEvaluationDao getEvaluationDao() {
        return evaluationDao;
    }

    public void setEvaluationDao(IEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }

    public IEvaluationDetailsDao getEvaluationDetailsDao() {
        return evaluationDetailsDao;
    }

    public void setEvaluationDetailsDao(IEvaluationDetailsDao evaluationDetailsDao) {
        this.evaluationDetailsDao = evaluationDetailsDao;
    }

    @Override
    public EvaluationDetails saveOrUpdateEvaluationDetails(EvaluationDetails evaluationDetails) throws ServiceException {
        try {
            if (evaluationDetails.getId() == null) {
                evaluationDetails.setActive(1);
                return detailsDao.create(evaluationDetails);
            } else {
                return detailsDao.update(evaluationDetails);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteEvaluationDetails(Long id) throws ServiceException {
        try {
            EvaluationDetails details = detailsDao.findById(id);
            if (details != null) {
                details.setActive(0);
                //detailsDao.update(details);
                detailsDao.delete(details);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public EvaluationDetails findEvaluationDetailsById(long id) throws ServiceException {
        try {
            return detailsDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<EvaluationDetails> getAllEvaluationDetails() throws ServiceException {
        try {
            return detailsDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void addEvaluation(Long typeId, Long evaluationId, int pourcentage) throws ServiceException {
        try {
            TypeCours type = typeCoursDao.findById(typeId);
            Evaluation eval = evaluationDao.findById(evaluationId);
            EvaluationDetails details = new EvaluationDetails();
            details.setActive(1);
            details.setPourcentage(pourcentage);
            details.setEvaluation(eval);
            details.setTypeCours(type);
            evaluationDetailsDao.create(details);
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void modifierEvaluation(Long typeId, Long evaluationId, int pourcentage) throws ServiceException {
        try {
            TypeCours type = typeCoursDao.findById(typeId);
            Evaluation eval = evaluationDao.findById(evaluationId);
            EvaluationDetails details = evaluationDetailsDao.findByTypeAndEvaluation(type, eval);
            details.setPourcentage(pourcentage);
            evaluationDetailsDao.update(details);
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void supprimerEvaluation(Long typeId, Long evaluationId) throws ServiceException {
        try {
            TypeCours type = typeCoursDao.findById(typeId);
            Evaluation eval = evaluationDao.findById(evaluationId);
            EvaluationDetails details = evaluationDetailsDao.findByTypeAndEvaluation(type, eval);
            if (details != null) {
                evaluationDetailsDao.delete(details);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }

    }

    @Override
    public List<EvaluationDetails> findEvaluationDetailsByTypeCours(Long typeId) throws ServiceException {
        try {
            TypeCours type = typeCoursDao.findById(typeId);
            return evaluationDetailsDao.findByTypeCours(type);
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<EvaluationDetails> getAllActive() throws ServiceException {
        try {
            return evaluationDetailsDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EvaluationDetailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

}
