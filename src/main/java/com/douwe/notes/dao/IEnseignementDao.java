package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Enseignement;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEnseignementDao extends IDao<Enseignement, Long>{
    
    public void deleteActive(Enseignement enseignement) throws DataAccessException;
    
    public List<Enseignement> findAllActive() throws  DataAccessException;
    
}
