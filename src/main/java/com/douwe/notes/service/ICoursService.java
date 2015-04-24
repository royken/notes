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
    
    public Cours saveOrUpdateCours(Cours cours) throws ServiceException;
    
    public void deleteCours(Long id) throws ServiceException;
    
    public Cours findCoursById(long id) throws ServiceException;
    
    public List<Cours> getAllCours() throws ServiceException;
    
    public Cours findByIntitule(String intitule) throws ServiceException;
    
    public List<Cours> findByParcoursAnnee(Long idParcours, Long idAnne) throws ServiceException;
}
