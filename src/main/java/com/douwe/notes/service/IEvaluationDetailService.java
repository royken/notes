package com.douwe.notes.service;

import com.douwe.notes.entities.EvaluationDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEvaluationDetailService {
    
    public EvaluationDetails saveOrUpdateEvaluationDetails(EvaluationDetails evaluationDetails) throws ServiceException;
    
    public void deleteEvaluationDetails(Long id) throws ServiceException;
    
    public EvaluationDetails findEvaluationDetailsById(long id) throws ServiceException;
    
    public List<EvaluationDetails> getAllEvaluationDetails() throws ServiceException;
}
