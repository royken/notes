package com.douwe.notes.service;

import com.douwe.notes.entities.Programme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IProgrammeService {
    
    public Programme saveOrUpdateProgramme(Programme programme) throws ServiceException;
    
    public void deleteProgramme(Long id) throws ServiceException;
    
    public Programme findProgrammeById(long id) throws ServiceException;
    
    public List<Programme> getAllProgrammes() throws ServiceException;
    
}
