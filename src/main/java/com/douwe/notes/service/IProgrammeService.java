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
    
    public Programme saveOrUpdateProgramme(Programme programme);
    
    public void deleteProgramme(Long id);
    
    public Programme findProgrammeById(long id);
    
    public List<Programme> getAllProgrammes();
    
}
