package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Programme;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IProgrammeDao extends IDao<Programme, Long>{
    
    public void deleteActiv(Programme programme)throws DataAccessException;
    
    public List<Programme> findAllActive() throws DataAccessException;
    
}
