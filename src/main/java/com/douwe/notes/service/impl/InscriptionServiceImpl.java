package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IInscriptionDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.service.IInscriptionService;
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
public class InscriptionServiceImpl implements IInscriptionService {
    
    @Inject
    private IInscriptionDao inscriptionDao;
    
    @Inject
    private IEtudiantDao etudiantDao;
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;
    
    @Inject
    private IParcoursDao parcoursDao;
    
    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private IOptionDao optionDao;
    
    public IInscriptionDao getInscriptionDao() {
        return inscriptionDao;
    }
    
    public void setInscriptionDao(IInscriptionDao inscriptionDao) {
        this.inscriptionDao = inscriptionDao;
    }

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
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
    public Inscription saveOrUpdateInscription(Inscription inscription) throws ServiceException {
        try {
            if (inscription.getId() != null) {
                inscription.setActive(1);
                return inscriptionDao.create(inscription);                
            } else {
                return inscriptionDao.update(inscription);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
    @Override
    public void deleteInscription(long id) throws ServiceException {
        try {
            Inscription inscription = inscriptionDao.findById(id);
            if (inscription != null) {
                inscription.setActive(0);
                inscriptionDao.update(inscription);
                
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
    @Override
    public Inscription findInscriptionById(long id) throws ServiceException {
        try {
            return inscriptionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
    @Override
    public List<Inscription> getAllInscriptions() throws ServiceException {
        try {
            return inscriptionDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    
    private Inscription saveEtudiant(Etudiant etudiant, AnneeAcademique academique, Parcours parcours) throws ServiceException {
        try {
            Etudiant et = etudiantDao.findByMatricule(etudiant.getMatricule());
            if(et == null)
                et = etudiantDao.create(etudiant);          
            Inscription inscription = new  Inscription();
            inscription.setAnneeAcademique(academique);
            inscription.setEtudiant(et);
            inscription.setActive(1);
            inscription.setParcours(parcours);
            return  inscriptionDao.create(inscription);            
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Inscription inscrireEtudiant(Etudiant etudiant, String codeNiveau, String codeOption, Long anneeId) throws ServiceException {
        try {
            
            Niveau niveau = niveauDao.findByCode(codeNiveau);
            if(niveau == null){
                throw new ServiceException("Le niveau demandé est introuvable");
            }
            Option option = optionDao.findByCode(codeOption);
            if(option == null){
                throw new ServiceException("L'option demandée est introuvable");
            }
            AnneeAcademique academique = academiqueDao.findById(anneeId);
            if(academique == null){
                throw new ServiceException("L'année académique demandée est introuvable");
            }
            Parcours parcours = parcoursDao.findByNiveauOption(niveau, option);
            if(parcours == null){
                throw new ServiceException("La ressource demandée est introuvable");
            }
            return  saveEtudiant(etudiant, academique, parcours);
            
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
   
    }

    @Override
    public Inscription findInscriptionByEtudiant(Etudiant etudiant, AnneeAcademique academique) throws ServiceException {
        try {
            return inscriptionDao.findInscriptionByEtudiant(etudiant, academique);
        } catch (DataAccessException ex) {
            Logger.getLogger(InscriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
