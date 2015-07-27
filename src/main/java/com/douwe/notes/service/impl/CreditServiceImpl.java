package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICreditDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Credit;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.ICreditService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Stateless
public class CreditServiceImpl implements ICreditService {

    @Inject
    private ICreditDao creditDao;

    @Inject
    private INiveauDao niveauDao;

    @Inject
    private IOptionDao optionDao;
    
    @Inject
    private IAnneeAcademiqueDao anneeDao;

    public ICreditDao getCreditDao() {
        return creditDao;
    }

    public void setCreditDao(ICreditDao creditDao) {
        this.creditDao = creditDao;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    public IAnneeAcademiqueDao getAnneeDao() {
        return anneeDao;
    }

    public void setAnneeDao(IAnneeAcademiqueDao anneeDao) {
        this.anneeDao = anneeDao;
    }
    
    @Override
    public Credit saveOrUpdateCredit(Credit credit) throws ServiceException {
        try {
            if (credit.getId() == null) {
                return creditDao.create(credit);
            } else {
                return creditDao.update(credit);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CreditServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteCredit(long id) throws ServiceException {
        
        try {
            Credit credit = creditDao.findById(id);
            if(credit != null)
                creditDao.delete(credit);
        } catch (DataAccessException ex) {
            Logger.getLogger(CreditServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Credit> getAll() throws ServiceException {
        try {
            return creditDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(CreditServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Credit> getAll(long anneeId, long niveauId, long optionId) throws ServiceException {
        try {
            AnneeAcademique annee = anneeDao.findById(anneeId);
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            return creditDao.findByParcours(annee, niveau, option);
        } catch (DataAccessException ex) {
            Logger.getLogger(CreditServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Credit findById(long id) throws ServiceException {
        try {
            return creditDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CreditServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

}
