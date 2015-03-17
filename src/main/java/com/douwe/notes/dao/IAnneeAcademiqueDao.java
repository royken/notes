package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IAnneeAcademiqueDao extends IDao<AnneeAcademique, Long>{
    
    public List<AnneeAcademique> findAllActive() throws DataAccessException;
    
    public AnneeAcademique findByString(String date) throws DataAccessException;
}
