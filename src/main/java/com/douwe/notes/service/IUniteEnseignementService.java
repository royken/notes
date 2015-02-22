package com.douwe.notes.service;

import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IUniteEnseignementService {
    
    public UniteEnseignement saveOrUpdateCours(UniteEnseignement uniteEnseignement);
    
    public void deleteUniteEnseignement(Long id);
    
    public UniteEnseignement findUniteEnseignementById(long id);
    
    public List<UniteEnseignement> getAllUniteEnseignements();
    
    
}
