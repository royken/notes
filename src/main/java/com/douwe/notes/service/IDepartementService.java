package com.douwe.notes.service;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IDepartementService {
    
    public Departement saveOrUpdateDepartement(Departement departement) throws ServiceException;
    
    public void deleteDepartement(Long id) throws ServiceException;
    
    public Departement findDepartementById(long id) throws ServiceException;
    
    public List<Departement> getAllDepartements() throws ServiceException;
    
    public List<Option> getAllOptions(Departement departement) throws ServiceException;
    
    
}
