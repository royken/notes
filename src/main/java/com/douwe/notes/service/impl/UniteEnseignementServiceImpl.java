package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IUniteEnseignementService;
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
public class UniteEnseignementServiceImpl implements IUniteEnseignementService {

    @Inject
    private IUniteEnseignementDao uniteEnseignementDao;

    public IUniteEnseignementDao getUniteEnseignementDao() {
        return uniteEnseignementDao;
    }

    public void setUniteEnseignementDao(IUniteEnseignementDao uniteEnseignementDao) {
        this.uniteEnseignementDao = uniteEnseignementDao;
    }

    public UniteEnseignement saveOrUpdateCours(UniteEnseignement uniteEnseignement) throws ServiceException{
        try {
            if (uniteEnseignement.getId() == null) {
                return uniteEnseignementDao.create(uniteEnseignement);
            } else {
                return uniteEnseignementDao.update(uniteEnseignement);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public void deleteUniteEnseignement(Long id) throws ServiceException{
        try {
            UniteEnseignement uniteEnseignement = uniteEnseignementDao.findById(id);
            uniteEnseignementDao.deleteActive(uniteEnseignement);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public UniteEnseignement findUniteEnseignementById(long id) throws ServiceException{
        try {
            return uniteEnseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<UniteEnseignement> getAllUniteEnseignements() throws ServiceException{
        try {
            return uniteEnseignementDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

}
