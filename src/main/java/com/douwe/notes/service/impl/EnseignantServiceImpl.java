package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.service.IEnseignantService;
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
public class EnseignantServiceImpl implements IEnseignantService{
    
    @Inject
    private IEnseignantDao enseignantDao;

    public IEnseignantDao getEnseignantDao() {
        return enseignantDao;
    }

    public void setEnseignantDao(IEnseignantDao enseignantDao) {
        this.enseignantDao = enseignantDao;
    }
    
    

    @Override
    public Enseignant saveOrUpdateEnseignant(Enseignant enseignant) throws ServiceException{
        try {
            if (enseignant.getId() == null) {
                enseignant.setActive(1);
                return enseignantDao.create(enseignant);
            } else {
                return enseignantDao.update(enseignant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteEnseignant(Long id) throws ServiceException{
        try {
            Enseignant enseignant = enseignantDao.findById(id);
            if(enseignant != null){
                enseignant.setActive(0);
                enseignantDao.update(enseignant);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Enseignant findEnseignantById(long id) throws ServiceException{
        try {
            return enseignantDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Enseignant> getAllEnseignants() throws ServiceException{
        try {
            return enseignantDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
