package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.entities.Niveau;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class NiveauDaoImpl extends GenericDao<Niveau, Long> implements INiveauDao{

    @Override
    public Niveau findByCode(String code) throws DataAccessException {
        return (Niveau)(getManager().createNamedQuery("Niveau.findByCode").setParameter("param", code).getSingleResult());
    }
    
}
