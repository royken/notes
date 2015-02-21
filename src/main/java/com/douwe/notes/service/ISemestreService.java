package com.douwe.notes.service;

import com.douwe.notes.entities.Semestre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ISemestreService {
    
    public Semestre saveOrUpdateSemestre(Semestre semestre);
    
    public void deleteSemestre(Long id);
    
    public Semestre findSemestreById(long id);
    
    public List<Semestre> getAllSemestre();
}
