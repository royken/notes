package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEnseignementDao;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.service.IEnseignementService;
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
public class EnseignementServiceImpl implements IEnseignementService{
    
    @Inject
    private IEnseignementDao enseignementDao;

    public IEnseignementDao getEnseignementDao() {
        return enseignementDao;
    }

    public void setEnseignementDao(IEnseignementDao enseignementDao) {
        this.enseignementDao = enseignementDao;
    }
    
    

    public Enseignement saveOrUpdateEnseignement(Enseignement enseignement) {
         try {
        if(enseignement.getId() == null){
                return enseignementDao.create(enseignement);
        }
        else{
            return enseignementDao.update(enseignement);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }

    public void deleteEnseignement(Long id) {
        try {
            Enseignement enseignement = enseignementDao.findById(id);
            enseignementDao.delete(enseignement);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Enseignement findEnseignementById(long id) {
        try {
            return enseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Enseignement> getAllEnseignements() {
        try {
            return enseignementDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
