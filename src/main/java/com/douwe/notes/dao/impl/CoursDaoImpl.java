package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CoursDaoImpl extends GenericDao<Cours, Long> implements ICoursDao{

    public void deleteActive(Cours cours) throws DataAccessException {
        getManager().createNamedQuery("Cours.deleteActive").setParameter("idParam", cours.getId());
    }

    @Override
    public List<Cours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Cours.findAllActive").getResultList();
    }

    @Override
    public Cours findByIntitule(String intitule) throws DataAccessException {
        return (Cours)(getManager().createNamedQuery("Cours.findByIntitule").setParameter("param", intitule).getSingleResult());
    }

    @Override
    public List<Cours> findByParcoursAnnee(Parcours parcours, AnneeAcademique academique) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cours> findByUe(UniteEnseignement ue) throws DataAccessException {
        return getManager().createNamedQuery("Cours.findByUE").setParameter("idParam", ue.getId()).getResultList();
    }
    
}
