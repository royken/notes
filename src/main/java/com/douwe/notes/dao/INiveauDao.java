package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Niveau;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface INiveauDao extends IDao<Niveau, Long>{
    
    public Niveau findByCode(String code) throws DataAccessException;
    
}
