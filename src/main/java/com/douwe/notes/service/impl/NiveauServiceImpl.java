package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.service.INiveauService;
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
public class NiveauServiceImpl implements INiveauService{
    
    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private ISemestreDao semestreDao;

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }
    
    

    @Override
    public Niveau saveOrUpdateNiveau(Niveau niveau) throws ServiceException{
        try {
        if(niveau.getId() ==null){
            niveau.setActive(1);
                return niveauDao.create(niveau);         
        }
        else{
            return niveauDao.update(niveau);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw  new ServiceException("La ressource demandée est introuvable");
            }
    }

    @Override
    public void deleteNiveau(Long id) throws ServiceException{
        try {
            Niveau niveau = niveauDao.findById(id);
            niveauDao.delete(niveau);
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Niveau findNiveauById(long id) throws ServiceException{
        try {
            return niveauDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Niveau> getAllNiveaux() throws ServiceException{
        try {
            List <Niveau> list = niveauDao.findAll();            
            return list;
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Niveau findByCode(String code) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findByCode(code);
            if(niveau == null){
                throw  new ServiceException("La ressource demandée est introuvable");
            }
            return niveau;
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Semestre> getAllSemestre(long niveauId) throws ServiceException {
        try {
            Niveau n = niveauDao.findById(niveauId);
            return semestreDao.findByNiveau(n);
        } catch (DataAccessException ex) {
            Logger.getLogger(NiveauServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
