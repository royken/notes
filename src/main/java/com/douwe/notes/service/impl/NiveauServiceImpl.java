package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.service.INiveauService;
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
public class NiveauServiceImpl implements INiveauService{
    
    @Inject
    private INiveauDao niveauDao;

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public Niveau saveOrUpdateNiveau(Niveau niveau) {
        try {
        if(niveau.getId() ==null){
            
                return niveauDao.create(niveau);
            
        }
        else{
            return niveauDao.update(niveau);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }

    public void deleteNiveau(Long id) {
        try {
            Niveau niveau = niveauDao.findById(id);
            niveauDao.delete(niveau);
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Niveau findNiveauById(long id) {
        try {
            return niveauDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Niveau> getAllNiveaus() {
        try {
            return niveauDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
