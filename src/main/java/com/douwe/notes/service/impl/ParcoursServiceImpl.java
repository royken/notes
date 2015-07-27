package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.service.IParcoursService;
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
public class ParcoursServiceImpl implements IParcoursService {

    @Inject
    private IParcoursDao parcoursDao;
    
    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private IOptionDao optionDao;

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
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
    

    @Override
    public Parcours saveOrUpdateParcours(Parcours parcours) throws ServiceException{
        try {
            parcours.setActive(1);
            if (parcours.getId() != null) {
                
                return parcoursDao.create(parcours);
            } else {
                return parcoursDao.update(parcours);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }

    }

    @Override
    public void deleteParcours(Long id) throws ServiceException{
        try {
            Parcours parcours = parcoursDao.findById(id);
            if (parcours != null) {
                parcours.setActive(0);
                parcoursDao.update(parcours);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Parcours findParcoursById(long id) throws ServiceException{
        try {
            return parcoursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Parcours> getAllParcours() throws ServiceException{
        try {
            return parcoursDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Parcours findByNiveauOption(long niveauId, long optionId) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            return parcoursDao.findByNiveauOption(niveau, option);
        } catch (DataAccessException ex) {
            Logger.getLogger(ParcoursServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

}
