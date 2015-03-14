package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class UniteEnseignementDaoImpl extends GenericDao<UniteEnseignement, Long> implements IUniteEnseignementDao{

    public void deleteActive(UniteEnseignement ue) throws DataAccessException {
        getManager().createNamedQuery("UE.deleteActive").setParameter("idParam", ue.getId());
    }

    public List<UniteEnseignement> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("UE.findAllActive").getResultList();
    }
    
}
