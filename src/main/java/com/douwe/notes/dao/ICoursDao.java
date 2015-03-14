package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Cours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ICoursDao extends IDao<Cours, Long>{
    
    public void deleteActive(Cours cours) throws DataAccessException;
    
    public List<Cours> findAllActive() throws DataAccessException;
    
    public Cours findByIntitule(String intitule) throws DataAccessException;
    
}
