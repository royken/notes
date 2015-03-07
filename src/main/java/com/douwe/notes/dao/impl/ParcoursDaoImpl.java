package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
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

    @Override
    public List<Parcours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Parcours.findAllActive").getResultList();
    }

    @Override
    public Parcours findByNiveauOption(Niveau niveau, Option option) throws DataAccessException {
        return (Parcours)(getManager().createNamedQuery("Parcours.findByNiveauOption").setParameter("param1", niveau.getId()).setParameter("param2", option.getId()).getSingleResult());
    }
    
}
