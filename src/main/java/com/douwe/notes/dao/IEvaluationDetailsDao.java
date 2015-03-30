package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.TypeCours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEvaluationDetailsDao extends IDao<EvaluationDetails, Long>{

    public EvaluationDetails findByTypeAndEvaluation(TypeCours type, Evaluation eval) throws DataAccessException;

    public List<EvaluationDetails> findByTypeCours(TypeCours type) throws DataAccessException;
    
    public List<EvaluationDetails> findAllActive() throws DataAccessException;
    
}
