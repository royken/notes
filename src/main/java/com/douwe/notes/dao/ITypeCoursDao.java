package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.TypeCours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ITypeCoursDao extends IDao<TypeCours, Long>{
    
    public List<TypeCours> findAllActive() throws DataAccessException;
    
}
