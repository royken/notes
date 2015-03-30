package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Cours_;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.EvaluationDetails_;
import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.entities.TypeCours_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EvaluationDaoImpl extends GenericDao<Evaluation, Long> implements IEvaluationDao{

    @Override
    public List<Evaluation> evaluationForCourses(Cours cours) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Evaluation> cq = cb.createQuery(Evaluation.class);
        Root<EvaluationDetails> detailsRoot = cq.from(EvaluationDetails.class);
        Root<Cours> coursRoot = cq.from(Cours.class);
        Path<Evaluation> evaluationPath = detailsRoot.get(EvaluationDetails_.evaluation);
        Path<TypeCours> typePath = detailsRoot.get(EvaluationDetails_.typeCours);
        cq.where(cb.and(cb.equal(typePath, coursRoot.get(Cours_.typeCours)),
                cb.equal(coursRoot, cours)));
        cq.select(evaluationPath);
        return getManager().createQuery(cq).getResultList();
    }
    
}
