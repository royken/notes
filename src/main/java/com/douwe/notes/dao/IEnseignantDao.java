package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Enseignant;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEnseignantDao extends IDao<Enseignant, Long>{
    
    public void deleteActive(Enseignant enseignant) throws DataAccessException;
    
    public List<Enseignant> findAllActive()throws DataAccessException;
    
}
