package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Semestre;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ISemestreDao extends IDao<Semestre, Long>{

    public List<Semestre> findByNiveau(Niveau n) throws DataAccessException;
    
}