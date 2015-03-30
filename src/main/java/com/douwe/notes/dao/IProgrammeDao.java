package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Programme;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IProgrammeDao extends IDao<Programme, Long>{
    
    public void deleteActiv(Programme programme)throws DataAccessException;
    
    public List<Programme> findAllActive() throws DataAccessException;
    
    public List<Programme> findByNiveauOption(Niveau n, Option o) throws DataAccessException;
    
}
