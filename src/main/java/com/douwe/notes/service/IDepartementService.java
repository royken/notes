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
    
    public Departement saveOrUpdateDepartement(Departement departement);
    
    public void deleteDepartement(Long id);
    
    public Departement findDepartementById(long id);
    
    public List<Departement> getAllDepartements();
    
    public List<Option> getAllOptions(Departement departement);
    
    
}
