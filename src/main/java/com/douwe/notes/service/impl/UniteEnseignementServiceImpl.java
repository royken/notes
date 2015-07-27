package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
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

    @Inject
    private INiveauDao niveauDao;

    @Inject
    private IOptionDao optionDao;

    @Inject
    private IParcoursDao parcoursDao;

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    public IUniteEnseignementDao getUniteEnseignementDao() {
        return uniteEnseignementDao;
    }

    public void setUniteEnseignementDao(IUniteEnseignementDao uniteEnseignementDao) {
        this.uniteEnseignementDao = uniteEnseignementDao;
    }

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
    }

    @Override
    public UniteEnseignement saveOrUpdateUe(UniteEnseignement uniteEnseignement) throws ServiceException {
        try {
            if (uniteEnseignement.getId() == null) {
                uniteEnseignement.setActive(1);
                return uniteEnseignementDao.create(uniteEnseignement);
            } else {
                return uniteEnseignementDao.update(uniteEnseignement);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteUniteEnseignement(Long id) throws ServiceException {
        try {
            UniteEnseignement uniteEnseignement = uniteEnseignementDao.findById(id);
            if (uniteEnseignement != null) {
                uniteEnseignement.setActive(0);
                uniteEnseignementDao.update(uniteEnseignement);
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public UniteEnseignement findUniteEnseignementById(long id) throws ServiceException {
        try {
            return uniteEnseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<UniteEnseignement> getAllUniteEnseignements() throws ServiceException {
        try {
            return uniteEnseignementDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<UniteEnseignement> findByParcours(long niveauId, long optionId) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            return uniteEnseignementDao.findUniteByNiveauOption(niveau, option);
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public UniteEnseignement addUniteEnseignement(Long niveauId, Long optionId, UniteEnseignement ue) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            Parcours parcours = parcoursDao.findByNiveauOption(niveau, option);
            ue.setParcours(parcours);
            if (ue.getId() == null) {
                ue.setActive(1);
                return uniteEnseignementDao.create(ue);
            } else {
                return uniteEnseignementDao.update(ue);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UniteEnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

}
