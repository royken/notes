package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEnseignementDao;
import com.douwe.notes.entities.Enseignement;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EnseignementDaoImpl extends GenericDao<Enseignement, Long> implements IEnseignementDao{

    @Override
    public void deleteActive(Enseignement enseignement) throws DataAccessException {
        getManager().createNamedQuery("Enseignement.deleteActive").setParameter("idParam", enseignement.getId());
    }

    @Override
    public List<Enseignement> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Enseignement.findAllActive").getResultList();
    }
    
}
