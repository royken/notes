package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Parcours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class ParcoursDaoImpl extends GenericDao<Parcours, Long> implements IParcoursDao{

    public void deleteActive(Parcours parcours) throws DataAccessException {
        getManager().createNamedQuery("Parcours.deleteActive").setParameter("idParam", parcours.getId());
    }

    public List<Parcours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Parcours.findAllActive").getResultList();
    }
    
}
