package com.douwe.notes.service;

import com.douwe.notes.entities.Niveau;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface INiveauService {
    
    public Niveau saveOrUpdateNiveau(Niveau niveau);
    
    public void deleteNiveau(Long id);
    
    public Niveau findNiveauById(long id);
    
    public List<Niveau> getAllNiveaux();
}
