package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.entities.AnneeAcademique;
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
        return getManager().createNamedQuery("Programme.findByNiveauOption").setParameter("param1", n).setParameter("param2", o).setParameter("param3", academique).setParameter("param4", semestre).getResultList();

    }

    @Override
    public Programme findByCours(Cours c, Niveau n, Option o, AnneeAcademique a) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Programme> cq = cb.createQuery(Programme.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Niveau> niveauPath = parcoursPath.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursPath.get(Parcours_.option);
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        Path<UniteEnseignement> unitePath = programmeRoot.get(Programme_.uniteEnseignement);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(unitePath.get(UniteEnseignement_.courses).in(c));
        predicates.add(cb.equal(niveauPath, n));
        predicates.add(cb.equal(optionPath, o));
        predicates.add(cb.equal(anneePath, a));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(programmeRoot);
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
