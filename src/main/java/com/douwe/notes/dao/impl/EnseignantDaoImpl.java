package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignant;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EnseignantDaoImpl extends GenericDao<Enseignant, Long> implements IEnseignantDao{

    @Override
    public void deleteActive(Enseignant enseignant) throws DataAccessException {
        getManager().createNamedQuery("Enseignant.deleteActive").setParameter("idParam", enseignant.getId());
    }

    @Override
    public List<Enseignant> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Enseignant.findAllActive").getResultList();
    }

    @Override
    public List<Enseignant> findByCours(Cours cours) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
