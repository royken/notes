package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEnseignementDao;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.service.IEnseignementService;
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
public class EnseignementServiceImpl implements IEnseignementService{
    
    @Inject
    private IEnseignementDao enseignementDao;

    public IEnseignementDao getEnseignementDao() {
        return enseignementDao;
    }

    public void setEnseignementDao(IEnseignementDao enseignementDao) {
        this.enseignementDao = enseignementDao;
    }
    
    

    public Enseignement saveOrUpdateEnseignement(Enseignement enseignement) throws ServiceException{
         try {
        if(enseignement.getId() == null){
                return enseignementDao.create(enseignement);
        }
        else{
            return enseignementDao.update(enseignement);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw  new ServiceException("La ressource demandée est introuvable");
            }
    }

    public void deleteEnseignement(Long id) throws ServiceException{
        try {
            Enseignement enseignement = enseignementDao.findById(id);
            enseignementDao.deleteActive(enseignement);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Enseignement findEnseignementById(long id) throws ServiceException{
        try {
            return enseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Enseignement> getAllEnseignements() throws ServiceException{
        try {
            return enseignementDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
