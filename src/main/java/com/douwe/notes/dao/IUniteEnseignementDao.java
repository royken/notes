package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IUniteEnseignementDao extends IDao<UniteEnseignement, Long>{
    
    public void deleteActive(UniteEnseignement ue) throws DataAccessException;
    
    public List<UniteEnseignement> findAllActive() throws DataAccessException;
    
}
