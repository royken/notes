package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Semestre;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class ProgrammeDaoImpl extends GenericDao<Programme, Long> implements IProgrammeDao{

    @Override
    public void deleteActiv(Programme programme) throws DataAccessException {
        getManager().createNamedQuery("Programme.deleteActive").setParameter("idParam", programme.getId());
    }

    @Override
    public List<Programme> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Programme.findAllActive").getResultList();
    }


    @Override

    public List<Programme> findByNiveauOption(Niveau n, Option o, AnneeAcademique academique, Semestre semestre) throws DataAccessException {
        return getManager().createNamedQuery("Programme.findByNiveauOption").setParameter("param1", n).setParameter("param2", o).setParameter("param3", academique).setParameter("param4", semestre).getResultList();

    }
    
}
