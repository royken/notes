package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.entities.Enseignement_;
import com.douwe.notes.entities.Parcours;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EnseignantDaoImpl extends GenericDao<Enseignant, Long> implements IEnseignantDao {

    @Override
    public void deleteActive(Enseignant enseignant) throws DataAccessException {
        getManager().createNamedQuery("Enseignant.deleteActive").setParameter("idParam", enseignant.getId());
    }

    @Override
    public List<Enseignant> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Enseignant.findAllActive").getResultList();
    }

    @Override
    public List<Enseignant> findByCours(Cours cours, AnneeAcademique annee, Parcours parcours) throws DataAccessException {
        try{
            CriteriaBuilder cb = getManager().getCriteriaBuilder();
        
        CriteriaQuery<Enseignement> cq = cb.createQuery(Enseignement.class);
        Root<Enseignement> enseigmenentRoot = cq.from(Enseignement.class);
        Join<Enseignement, Cours> coursPath = enseigmenentRoot.join(Enseignement_.cours);
        Path<AnneeAcademique> anneePath = enseigmenentRoot.get(Enseignement_.anneeAcademique);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.lessThanOrEqualTo(anneePath.get(AnneeAcademique_.debut), annee.getDebut()));
        predicates.add(cb.equal(coursPath, cours));
        predicates.add(cb.equal(enseigmenentRoot.get(Enseignement_.parcours), parcours));
        cq.orderBy(cb.desc(enseigmenentRoot.get(Enseignement_.anneeAcademique).get(AnneeAcademique_.debut)));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).setMaxResults(1).getSingleResult().getEnseignants();
        }catch(NoResultException nre){
            
        }
        return Collections.EMPTY_LIST;
    }
}
