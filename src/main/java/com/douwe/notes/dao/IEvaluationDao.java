package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Evaluation;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEvaluationDao extends IDao<Evaluation, Long>{
    List<Evaluation> evaluationForCourses(Cours cours) throws DataAccessException;
    
    public Evaluation findByCode(String code) throws DataAccessException;

    public Evaluation findExamen()  throws DataAccessException;
}
