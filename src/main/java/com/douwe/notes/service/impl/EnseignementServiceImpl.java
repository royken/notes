package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.IEnseignementDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
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
public class EnseignementServiceImpl implements IEnseignementService {

    @Inject
    private IEnseignementDao enseignementDao;
    
    @Inject
    private IAnneeAcademiqueDao  anneeDao;
    
    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private IOptionDao optionDao;

    public IEnseignementDao getEnseignementDao() {
        return enseignementDao;
    }

    public void setEnseignementDao(IEnseignementDao enseignementDao) {
        this.enseignementDao = enseignementDao;
    }

    public IAnneeAcademiqueDao getAnneeDao() {
        return anneeDao;
    }

    public void setAnneeDao(IAnneeAcademiqueDao anneeDao) {
        this.anneeDao = anneeDao;
    }

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

    @Override
    public Enseignement saveOrUpdateEnseignement(Enseignement enseignement) throws ServiceException {
        try {
            if (enseignement.getId() == null) {
                enseignement.setActive(1);
                return enseignementDao.create(enseignement);
            } else {
                return enseignementDao.update(enseignement);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteEnseignement(Long id) throws ServiceException {
        try {
            Enseignement enseignement = enseignementDao.findById(id);
            if (enseignement != null) {
                enseignement.setActive(0);
                enseignementDao.update(enseignement);
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Enseignement findEnseignementById(long id) throws ServiceException {
        try {
            return enseignementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Enseignement> getAllEnseignements() throws ServiceException {
        try {
            return enseignementDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Enseignement> getEnseignementByOption(Long anneeId, Long niveauId, Long optionId) throws ServiceException {
        try {
            AnneeAcademique annee = anneeDao.findById(anneeId);
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            return enseignementDao.findByOption(annee, niveau, option);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnseignementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

}
