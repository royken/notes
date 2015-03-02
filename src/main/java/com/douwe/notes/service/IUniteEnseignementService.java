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
    
    public UniteEnseignement saveOrUpdateCours(UniteEnseignement uniteEnseignement) throws ServiceException;
    
    public void deleteUniteEnseignement(Long id) throws ServiceException;
    
    public UniteEnseignement findUniteEnseignementById(long id) throws ServiceException;
    
    public List<UniteEnseignement> getAllUniteEnseignements() throws ServiceException;
    
    
}
