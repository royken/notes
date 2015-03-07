package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ICycleDao;
import com.douwe.notes.entities.Cycle;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CycleDaoImpl extends GenericDao<Cycle, Long> implements ICycleDao{

    public void deleteActive(Cycle cycle) throws DataAccessException {
        getManager().createNamedQuery("Cycle.deleteActive").setParameter("idParam", cycle.getId());
    }

    @Override
    public List<Cycle> getAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Cycle.findAllActive").getResultList();
    }
    
}
