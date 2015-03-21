package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Option_;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class OptionDaoImpl extends GenericDao<Option, Long> implements IOptionDao{

    @Override
    public Departement findDepartement(Option option) throws DataAccessException {
       return (Departement) getManager().createNamedQuery("Option.findDepartement").setParameter("idParam", option.getDepartement().getId()).getSingleResult();
    }

    @Override
    public Option findByCode(String code) throws DataAccessException {
        return (Option)(getManager().createNamedQuery("Option.findByCode").setParameter("param", code).getSingleResult());
    }

    @Override
    public List<Option> findByDepartementNiveau(Departement dep, Niveau niv) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Option> cq = cb.createQuery(Option.class);
        Root<Parcours> parcoursRoot = cq.from(Parcours.class);
        Path<Niveau> niveauPath = parcoursRoot.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursRoot.get(Parcours_.option);
        Path<Departement> departementPath = optionPath.get(Option_.departement);
        cq.where(cb.and(cb.equal(niveauPath, niv),cb.equal(optionPath.get(Option_.active), 1), cb.equal(departementPath, dep)));
        cq.select(optionPath);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Option> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Option.findAllActive").getResultList();
    }
    
}
