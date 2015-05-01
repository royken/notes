package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Cours_;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Programme_;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CoursDaoImpl extends GenericDao<Cours, Long> implements ICoursDao {

    @Override
    public void deleteActive(Cours cours) throws DataAccessException {
        getManager().createNamedQuery("Cours.deleteActive").setParameter("idParam", cours.getId());
    }

    @Override
    public List<Cours> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Cours.findAllActive").getResultList();
    }

    @Override
    public Cours findByIntitule(String intitule) throws DataAccessException {
        return (Cours) (getManager().createNamedQuery("Cours.findByIntitule").setParameter("param", intitule).getSingleResult());
    }

    @Override
    public List<Cours> findByParcoursAnnee(Parcours parcours, AnneeAcademique academique, Semestre semestre) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Cours> cq = cb.createQuery(Cours.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Root<Cours> coursRoot = cq.from(Cours.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Semestre> semestrePath = programmeRoot.get(Programme_.semestre);
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        ListJoin<Cours, UniteEnseignement> ab = coursRoot.join(Cours_.uniteEnseignements);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(anneePath, academique));
        predicates.add(cb.equal(parcoursPath, parcours));
        if (semestre != null) {
            predicates.add(cb.equal(semestrePath, semestre));
        }
        predicates.add(cb.equal(programmeRoot.get(Programme_.uniteEnseignement),ab));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(coursRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Cours> findByUe(UniteEnseignement ue) throws DataAccessException {
        return getManager().createNamedQuery("Cours.findByUE").setParameter("idParam", ue.getId()).getResultList();
    }

}
