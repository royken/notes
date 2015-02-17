package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
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
public class DepartementServiceImpl implements IDepartementService{
    @Inject
    private IDepartementDao departementDao;
    
    public Departement saveOrUpdateDepartement(Departement departement) {
        System.out.println("Exécution de la methode saveOrUpdate");
        try {
            if (departement.getId() == null) {
                return departementDao.create(departement);
            } else {
                return departementDao.update(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteDepartement(Long id) {
        try {
            Departement departement = departementDao.findById(id);
            if (departement != null) {
                departementDao.delete(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public List<Departement> getAllDepartements() {
        try {
            return departementDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public Departement findDepartementById(long id) {
        try {
            return departementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Option> getAllOptions(Departement departement) {
        try {
            return departementDao.getAllOptions(departement);
        } catch (DataAccessException ex) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
