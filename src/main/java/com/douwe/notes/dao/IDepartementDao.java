package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IDepartementDao extends IDao<Departement, Long>{
    
    public List<Option> getAllOptions(Departement departement) throws DataAccessException;
    
    public void deleteActive(Departement departement) throws DataAccessException;
    
    public List<Departement> findAllActive()throws DataAccessException;
    
    public Departement findByCode(String code) throws DataAccessException;
    
}
