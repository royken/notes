package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;

import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.entities.AnneeAcademique;

import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.service.IProgrammeService;
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
public class ProgrammeServiceImpl implements IProgrammeService {

    @Inject
    private IProgrammeDao programmeDao;
    
    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private IOptionDao optionDao;
    
    @Inject
    private ICoursDao coursDao;
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;
    
    @Inject
    private ISemestreDao semestreDao;


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

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public IProgrammeDao getProgrammeDao() {
        return programmeDao;
    }

    public void setProgrammeDao(IProgrammeDao programmeDao) {
        this.programmeDao = programmeDao;
    }

    @Override
    public Programme saveOrUpdateProgramme(Programme programme) throws ServiceException{
        try {
            if (programme.getId() == null) {
                programme.setActive(1);
                return programmeDao.create(programme);
            } else {
                return programmeDao.update(programme);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteProgramme(Long id) throws ServiceException{
        try {
            Programme programme = programmeDao.findById(id);
            /*if(programme != null){
                programme.setActive(0);
                programmeDao.update(programme);
            }*/
            programmeDao.delete(programme);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Programme findProgrammeById(long id) throws ServiceException{
        try {
            return programmeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Programme> getAllProgrammes() throws ServiceException{
        try {
            return programmeDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public List<Programme> findProgrammeByParcours(Long niveauId, Long optionId, Long idAnnee, Long idSemestre) throws ServiceException {
        
        try {
            Niveau niveau = niveauDao.findById(niveauId);
            if(niveau == null){
                throw  new ServiceException("Ressource introuvable");
            }
            
            Option option = optionDao.findById(optionId);
            if(option == null){
                throw new ServiceException("Le service demandé est introuvable");
            }
            
            AnneeAcademique academique = academiqueDao.findById(idAnnee);
            if(academique == null){
                throw new ServiceException("Ressource introuvable");
            }
            
            Semestre semestre = semestreDao.findById(idSemestre);
            if(semestre == null){
                throw  new ServiceException("Ressource introuvable");
            }
            return programmeDao.findByNiveauOption(niveau, option, academique, semestre);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("");
        }
        }

    @Override
    public Programme findByCours(long coursId, long niveauId, long optionId, long anneeId) throws ServiceException {
        try {
            Cours c = coursDao.findById(coursId);
            Niveau n = niveauDao.findById(niveauId);
            Option o = optionDao.findById(optionId);
            AnneeAcademique a = academiqueDao.findById(anneeId);
            return programmeDao.findByCours(c, n, o, a);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Programme> findByOptionAnnee(long anneeId, long niveauId, long optionId, long semestreId) throws ServiceException {
        try {
            AnneeAcademique annee = academiqueDao.findById(anneeId);
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            Semestre semestre = semestreDao.findById(semestreId);
            return programmeDao.findByNiveauOption(niveau, option, annee, semestre);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex);
        }
    }

}
