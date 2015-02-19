package com.douwe.notes.service;

import com.douwe.notes.entities.Enseignant;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEnseignantService {
    
    public Enseignant saveOrUpdateEnseignant(Enseignant enseignant);
    
    public void deleteEnseignant(Long id);
    
    public Enseignant findEnseignantById(long id);
    
    public List<Enseignant> getAllEnseignants();
    
}
