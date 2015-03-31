package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.EvaluationDetails_;
import com.douwe.notes.entities.Evaluation_;
import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.entities.TypeCours_;
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
public class EvaluationDetailsDaoImpl extends GenericDao<EvaluationDetails, Long> implements IEvaluationDetailsDao {

    @Override
    public EvaluationDetails findByTypeAndEvaluation(TypeCours type, Evaluation eval)  throws DataAccessException{
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<EvaluationDetails> cq = cb.createQuery(EvaluationDetails.class);
        Root<EvaluationDetails> noteRoot = cq.from(EvaluationDetails.class);
        Path<TypeCours> typePath = noteRoot.get(EvaluationDetails_.typeCours);
        Path<Evaluation> evaluationPath = noteRoot.get(EvaluationDetails_.evaluation);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(noteRoot.get(EvaluationDetails_.active), 1));
        if (type != null) {
            predicates.add(cb.equal(typePath, type));
            predicates.add(cb.equal(typePath.get(TypeCours_.active), 1));
        }
        if (eval != null) {
            predicates.add(cb.equal(evaluationPath, eval));
            predicates.add(cb.equal(evaluationPath.get(Evaluation_.active), 1));
        }
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<EvaluationDetails> findByTypeCours(TypeCours type) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<EvaluationDetails> cq = cb.createQuery(EvaluationDetails.class);
        Root<EvaluationDetails> noteRoot = cq.from(EvaluationDetails.class);
        Path<TypeCours> typePath = noteRoot.get(EvaluationDetails_.typeCours);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(noteRoot.get(EvaluationDetails_.active), 1));
        if (type != null) {
            predicates.add(cb.equal(typePath, type));
            predicates.add(cb.equal(typePath.get(TypeCours_.active), 1));
        }
        
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }

}
