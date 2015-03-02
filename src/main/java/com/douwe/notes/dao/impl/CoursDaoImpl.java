package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.entities.Cours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CoursDaoImpl extends GenericDao<Cours, Long> implements ICoursDao{

    public void deleteActive(Cours cours) throws DataAccessException {
        getManager().createNamedQuery("Cours.deleteActive").setParameter("idParam", cours.getId());
    }

    public List<Cours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Cours.findAllActive").getResultList();
    }
    
}
