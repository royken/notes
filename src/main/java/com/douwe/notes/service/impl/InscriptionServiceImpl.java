package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IInscriptionDao;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.service.IInscriptionService;
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
public class InscriptionServiceImpl implements IInscriptionService{
    
    @Inject
    private IInscriptionDao inscriptionDao;

    public IInscriptionDao getInscriptionDao() {
        return inscriptionDao;
    }

    public void setInscriptionDao(IInscriptionDao inscriptionDao) {
        this.inscriptionDao = inscriptionDao;
    }
    
    

    public Inscription saveOrUpdateInscription(Inscription inscription) throws ServiceException{
        try {
        if(inscription.getId() != null){
                return inscriptionDao.create(inscription);  
        }
        else{
            return inscriptionDao.update(inscription);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw  new ServiceException("La ressource demandée est introuvable");
            }
    }

    public void deleteInscription(long id) throws ServiceException{
        try {
            Inscription inscription = inscriptionDao.findById(id);
            if(inscription != null){
                inscriptionDao.deleteActive(inscription);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Inscription findInscriptionById(long id) throws ServiceException{
        try {
            return inscriptionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Inscription> getAllInscriptions() throws ServiceException{
        try {
            return inscriptionDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
