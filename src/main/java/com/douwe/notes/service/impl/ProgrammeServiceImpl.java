package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.service.IProgrammeService;
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
public class ProgrammeServiceImpl implements IProgrammeService{
    
    @Inject
    private IProgrammeDao programmeDao;

    public IProgrammeDao getProgrammeDao() {
        return programmeDao;
    }

    public void setProgrammeDao(IProgrammeDao programmeDao) {
        this.programmeDao = programmeDao;
    }
    
    

    public Programme saveOrUpdateProgramme(Programme programme) {
        try {
        if(programme.getId() == null){
            return programmeDao.create(programme);
        }
        else{
            return programmeDao.update(programme);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }

    public void deleteProgramme(Long id) {
        try {
            Programme programme = programmeDao.findById(id);
            programmeDao.delete(programme);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Programme findProgrammeById(long id) {
        try {
            return  programmeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Programme> getAllProgrammes() {
        try {
            return programmeDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ProgrammeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
