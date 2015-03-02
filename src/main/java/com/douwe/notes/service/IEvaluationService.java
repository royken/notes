
package com.douwe.notes.service;

import com.douwe.notes.entities.Evaluation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEvaluationService {
    
    public Evaluation saveOrUpdateEvaluation(Evaluation evaluation) throws ServiceException;
    
    public void deleteEvaluation(long id) throws ServiceException;
    
    public Evaluation findEvaluationById(long id) throws ServiceException;
    
    public List<Evaluation> getAllEvaluations() throws ServiceException;
    
}
