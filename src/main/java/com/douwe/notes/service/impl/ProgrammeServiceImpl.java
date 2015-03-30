package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Programme;
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
            if(programme != null){
                programme.setActive(0);
                programmeDao.update(programme);
            }
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

    public Programme findByNiveauOption(Niveau n, Option o) throws ServiceException {
        try {
            Programme programme = programmeDao.findByNiveauOption(n, o);
            if(programme != null){
                return programme;
            }
            return null;
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Programme> findProgrammeByParcours(Long niveauId, Long optionId) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
