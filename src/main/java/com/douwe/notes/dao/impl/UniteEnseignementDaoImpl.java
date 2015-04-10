package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.UEnseignementCredit;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class UniteEnseignementDaoImpl extends GenericDao<UniteEnseignement, Long> implements IUniteEnseignementDao{

    public void deleteActive(UniteEnseignement ue) throws DataAccessException {
        getManager().createNamedQuery("UE.deleteActive").setParameter("idParam", ue.getId());
    }

    @Override
    public List<UniteEnseignement> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("UE.findAllActive").getResultList();
    }

   

    @Override
    public List<UEnseignementCredit> findByNiveauOptionSemestre(Niveau niveau, Option option, Semestre semestre) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
