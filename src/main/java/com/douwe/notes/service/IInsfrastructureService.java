package com.douwe.notes.service;

import com.douwe.notes.entities.Departement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Local
public interface IInsfrastructureService {
    
    public Departement saveOrUpdateDepartement(Departement departement);
    
    public void deleteDepartement(Long id);
    
    public List<Departement> getAllDepartements();
    
}
