package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IOptionService;
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
    
    
    public Option saveOrUpdateOption(Option option) {
        try {
            if (option.getId() == null) {
                return optionDao.create(option);
            } else {
                return optionDao.update(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteOption(Long id) {
        try {
            Option option = optionDao.findById(id);
            if (option != null) {
                optionDao.delete(option);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public Option findOptionById(long id) {
        try {
            return optionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Option> getAllOptions() {
        try {
            return optionDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Departement getDepartement(Option option) {
        try {
            return optionDao.findDepartement(option);
        } catch (DataAccessException ex) {
            Logger.getLogger(OptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
