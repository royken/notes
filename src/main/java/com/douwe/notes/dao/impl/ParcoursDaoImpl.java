package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class ParcoursDaoImpl extends GenericDao<Parcours, Long> implements IParcoursDao{

    @Override
    public void deleteActive(Parcours parcours) throws DataAccessException {
        getManager().createNamedQuery("Parcours.deleteActive").setParameter("idParam", parcours.getId());
    }

    @Override
    public List<Parcours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Parcours.findAllActive").getResultList();
    }

    @Override
    public Parcours findByNiveauOption(Niveau niveau, Option option) throws DataAccessException {
        System.out.println("Le niveau "+ niveau);
        System.out.println("L'option "+option);
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Parcours> cq = cb.createQuery(Parcours.class);
        Root<Parcours> noteRoot = cq.from(Parcours.class);
        Path<Niveau> niveauPath = noteRoot.get(Parcours_.niveau);   
        Path<Option> optionPath = noteRoot.get(Parcours_.option);
        cq.where(cb.and(cb.equal(niveauPath, niveau),cb.equal(optionPath, option),cb.equal(noteRoot.get(Parcours_.active), 1)));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
