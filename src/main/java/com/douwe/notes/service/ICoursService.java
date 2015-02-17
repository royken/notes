package com.douwe.notes.service;

import com.douwe.notes.entities.Cours;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ICoursService {
    
    public Cours saveOrUpdateCours(Cours cours);
    
    public void deleteCours(Long id);
    
    public Cours findCoursById(long id);
    
    public List<Cours> getAllCours();
}
