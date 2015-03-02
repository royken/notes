package com.douwe.notes.service;

import com.douwe.notes.entities.Etudiant;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEtudiantService {
    
    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant) throws ServiceException;
    
    public void deleteEtudiant(Long id) throws ServiceException;
    
    public Etudiant findEtudiantById(long id) throws ServiceException;
    
    public List<Etudiant> getAllEtudiant() throws ServiceException;
    
}
