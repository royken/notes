package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Inscription;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IInscriptionDao extends IDao<Inscription, Long>{
    
    public void deleteActive(Inscription inscription) throws DataAccessException;
    
    public List<Inscription> findAllActive() throws DataAccessException;
    
}
