package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Programme_;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.entities.UniteEnseignement_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        cq.where(cb.and(cb.equal(parcoursPath.get(Parcours_.niveau), n),
                cb.equal(parcoursPath.get(Parcours_.option), o),
                cb.equal(programmeRoot.get(Programme_.semestre), semestre),
                cb.equal(programmeRoot.get(Programme_.anneeAcademique),academique),
                cb.equal(programmeRoot.get(Programme_.active), 1)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Programme findByCours(Cours c, Niveau n, Option o, AnneeAcademique a) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Niveau> niveauPath = parcoursPath.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursPath.get(Parcours_.option);
        Path<UniteEnseignement> uePath = programmeRoot.get(Programme_.uniteEnseignement);
        Expression<List<Cours>> coursPath = uePath.get(UniteEnseignement_.cours);        
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(niveauPath, n));
        predicates.add(cb.equal(optionPath, o));
        predicates.add(cb.isMember(c, coursPath));
        predicates.add(cb.greaterThanOrEqualTo(anneePath.get(AnneeAcademique_.debut), a.getDebut()));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(programmeRoot);
        cq.orderBy(cb.desc(programmeRoot.get(Programme_.anneeAcademique)));
        return getManager().createQuery(cq).setMaxResults(1).getSingleResult();
    }
    
}
