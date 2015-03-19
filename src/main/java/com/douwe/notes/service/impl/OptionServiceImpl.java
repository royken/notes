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
import javax.inject.Named;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
@Named
public class OptionServiceImpl implements IOptionService{

    @Inject
    private IOptionDao optionDao;

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }
    
    
    @Override
    public Option saveOrUpdateOption(Option option) throws ServiceException{
        try {
            if (option.getId() == null) {
                option.setActive(1);
                return optionDao.create(option);
            } else {
                return optionDao.update(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteOption(Long id) throws ServiceException{
        try {
            Option option = optionDao.findById(id);
            if (option != null) {
                option.setActive(0);
                optionDao.update(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Option findOptionById(long id) throws ServiceException{
        try {
            return optionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Option> getAllOptions() throws ServiceException{
        try {
            return optionDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Departement getDepartement(Option option) throws ServiceException{
        try {
            return optionDao.findDepartement(option);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Option findByCode(String code) throws ServiceException {
        try {
            return optionDao.findByCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
