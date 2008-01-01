package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.service.IEnseignantService;
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
    
    

    public Enseignant saveOrUpdateEnseignant(Enseignant enseignant) {
        try {
            if (enseignant.getId() == null) {
                return enseignantDao.create(enseignant);
            } else {
                return enseignantDao.update(enseignant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteEnseignant(Long id) {
        try {
            Enseignant enseignant = enseignantDao.findById(id);
            if(enseignant != null){
                enseignantDao.delete(enseignant);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Enseignant findEnseignantById(long id) {
        try {
            return enseignantDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Enseignant> getAllEnseignants() {
        try {
            return enseignantDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
