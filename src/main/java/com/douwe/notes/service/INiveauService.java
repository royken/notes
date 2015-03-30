package com.douwe.notes.service;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Semestre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface INiveauService {
    
    public Niveau saveOrUpdateNiveau(Niveau niveau) throws ServiceException;
    
    public void deleteNiveau(Long id) throws ServiceException;
    
    public Niveau findNiveauById(long id) throws ServiceException;
    
    public List<Niveau> getAllNiveaux() throws ServiceException;
    
    public Niveau findByCode(String code) throws ServiceException;

    public List<Semestre> getAllSemestre(long niveauId) throws ServiceException;
}
