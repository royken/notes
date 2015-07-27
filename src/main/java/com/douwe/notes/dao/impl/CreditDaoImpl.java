package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ICreditDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Cours_;
import com.douwe.notes.entities.Credit;
import com.douwe.notes.entities.Credit_;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CreditDaoImpl extends GenericDao<Credit, Long> implements ICreditDao{

    @Override
    public List<Credit> findByParcours(AnneeAcademique annee, Niveau niveau, Option option) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Credit> cq = cb.createQuery(Credit.class);
        Root<Credit> creditRoot = cq.from(Credit.class);
        cq.select(creditRoot);
        cq.where(cb.and(cb.equal(creditRoot.get(Credit_.academique), annee),
                cb.equal(creditRoot.get(Credit_.parcours).get(Parcours_.option), option),
                cb.equal(creditRoot.get(Credit_.parcours).get(Parcours_.niveau), niveau)));
        cq.orderBy(cb.asc(creditRoot.get(Credit_.cours).get(Cours_.intitule)));
        cq.distinct(true);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Credit findByCours(Cours cours, Niveau niveau, Option option, AnneeAcademique anne) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Credit> cq = cb.createQuery(Credit.class);
        Root<Credit> creditRoot = cq.from(Credit.class);
        cq.select(creditRoot);
        cq.where(cb.and(
                cb.equal(creditRoot.get(Credit_.parcours).get(Parcours_.option), option),
                cb.equal(creditRoot.get(Credit_.parcours).get(Parcours_.niveau), niveau),
                cb.equal(creditRoot.get(Credit_.cours), cours)));
        cq.orderBy(cb.desc(creditRoot.get(Credit_.academique).get(AnneeAcademique_.debut)));
        cq.distinct(true);
        return getManager().createQuery(cq).setMaxResults(1).getSingleResult();
    }
    
}
