package com.douwe.notes.service;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEnseignementService {
    
    public Enseignement saveOrUpdateEnseignement(Enseignement enseignement);
    
    public void deleteEnseignement(Long id);
    
    public Enseignement findEnseignementById(long id);
    
    public List<Enseignement> getAllEnseignements();
    
}
