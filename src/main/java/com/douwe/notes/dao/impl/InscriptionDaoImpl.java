package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IInscriptionDao;
import com.douwe.notes.entities.Inscription;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class InscriptionDaoImpl extends GenericDao<Inscription, Long> implements IInscriptionDao{

    public void deleteActive(Inscription inscription) throws DataAccessException {
        getManager().createNamedQuery("Inscription.deleteActive").setParameter("idPram", inscription.getId());
    }

    public List<Inscription> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Inscription.findAllActive").getResultList();
    }
    
}
