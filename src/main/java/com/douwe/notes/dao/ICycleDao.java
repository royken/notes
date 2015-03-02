package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Cycle;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ICycleDao extends IDao<Cycle, Long>{
    
    public void deleteActive(Cycle cycle)throws DataAccessException;
    
    public List<Cycle> getAllActive()throws DataAccessException;
    
}
