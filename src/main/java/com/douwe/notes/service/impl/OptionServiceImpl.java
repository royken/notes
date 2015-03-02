package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IOptionService;
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
public class OptionServiceImpl implements IOptionService{

    @Inject
    private IOptionDao optionDao;

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }
    
    
    public Option saveOrUpdateOption(Option option) throws ServiceException{
        try {
            if (option.getId() == null) {
                return optionDao.create(option);
            } else {
                return optionDao.update(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public void deleteOption(Long id) throws ServiceException{
        try {
            Option option = optionDao.findById(id);
            if (option != null) {
                optionDao.delete(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Option findOptionById(long id) throws ServiceException{
        try {
            return optionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Option> getAllOptions() throws ServiceException{
        try {
            return optionDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Departement getDepartement(Option option) throws ServiceException{
        try {
            return optionDao.findDepartement(option);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
