package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IInscriptionDao;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.service.IInscriptionService;
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
    
    

    public Inscription saveOrUpdateInscription(Inscription inscription) {
        try {
        if(inscription.getId() != null){
                return inscriptionDao.create(inscription);  
        }
        else{
            return inscriptionDao.update(inscription);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }

    public void deleteInscription(long id) {
        try {
            Inscription inscription = inscriptionDao.findById(id);
            if(inscription != null){
                inscriptionDao.delete(inscription);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Inscription findInscriptionById(long id) {
        try {
            return inscriptionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Inscription> getAllInscriptions() {
        try {
            return inscriptionDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
