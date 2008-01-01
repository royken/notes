package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IOptionDao extends IDao<Option, Long>{
    
    public Departement findDepartement(Option option) throws DataAccessException;
}
