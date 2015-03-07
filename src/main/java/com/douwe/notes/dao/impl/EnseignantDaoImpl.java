package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.entities.Enseignant;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EnseignantDaoImpl extends GenericDao<Enseignant, Long> implements IEnseignantDao{

    public void deleteActive(Enseignant enseignant) throws DataAccessException {
        getManager().createNamedQuery("Enseignant.deleteActive").setParameter("idParam", enseignant.getId());
    }

    @Override
    public List<Enseignant> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Enseignant.findAllActive").getResultList();
    }

    
    
    
}
