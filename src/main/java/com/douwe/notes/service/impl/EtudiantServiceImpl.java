package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.service.IEtudiantService;
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
public class EtudiantServiceImpl implements IEtudiantService{
    
    @Inject
    private IEtudiantDao etudiantDao;

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }
    
    

    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant) {
        try {
            if (etudiant.getId() == null) {
                return etudiantDao.create(etudiant);
            } else {
                return etudiantDao.update(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteEtudiant(Long id) {
        try {
            Etudiant etudiant = etudiantDao.findById(id);
            if (etudiant != null) {
                etudiantDao.delete(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public Etudiant findEtudiantById(long id) {
        try {
            return etudiantDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Etudiant> getAllEtudiant() {
        try {
            return etudiantDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
