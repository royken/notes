package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IUniteEnseignementService;
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
public class UniteEnseignementServiceImpl implements IUniteEnseignementService {

    @Inject
    private IUniteEnseignementDao uniteEnseignementDao;

    public IUniteEnseignementDao getUniteEnseignementDao() {
        return uniteEnseignementDao;
    }

    public void setUniteEnseignementDao(IUniteEnseignementDao uniteEnseignementDao) {
        this.uniteEnseignementDao = uniteEnseignementDao;
    }

    public UniteEnseignement saveOrUpdateCours(UniteEnseignement uniteEnseignement) {
        try {
            if (uniteEnseignement.getId() == null) {
                return uniteEnseignementDao.create(uniteEnseignement);
            } else {
                return uniteEnseignementDao.update(uniteEnseignement);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteUniteEnseignement(Long id) {
        try {
            UniteEnseignement uniteEnseignement = uniteEnseignementDao.findById(id);
            uniteEnseignementDao.delete(uniteEnseignement);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UniteEnseignement findUniteEnseignementById(long id) {
        try {
            return uniteEnseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<UniteEnseignement> getAllUniteEnseignements() {
        try {
            return uniteEnseignementDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
