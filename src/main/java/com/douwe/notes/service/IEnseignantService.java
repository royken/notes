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
    
    public Enseignant saveOrUpdateEnseignant(Enseignant enseignant) throws ServiceException;
    
    public void deleteEnseignant(Long id) throws ServiceException;
    
    public Enseignant findEnseignantById(long id) throws ServiceException;
    
    public List<Enseignant> getAllEnseignants() throws ServiceException;
    
}
