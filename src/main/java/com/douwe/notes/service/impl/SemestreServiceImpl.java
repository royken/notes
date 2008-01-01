package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.service.ISemestreService;
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
public class SemestreServiceImpl implements ISemestreService{
    
    @Inject
    private ISemestreDao semestreDao;

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }

    public Semestre saveOrUpdateSemestre(Semestre semestre) {
        try {
            if (semestre.getId() == null) {
                return semestreDao.create(semestre);
            } else {
                return semestreDao.update(semestre);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(SemestreServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteSemestre(Long id) {
        try {
            Semestre semestre = semestreDao.findById(id);
            if (semestre != null) {
                semestreDao.delete(semestre);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(SemestreServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public Semestre findSemestreById(long id) {
        try {
            return semestreDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(SemestreServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Semestre> getAllSemestre() {
        try {
            return semestreDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(SemestreServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
   
}
