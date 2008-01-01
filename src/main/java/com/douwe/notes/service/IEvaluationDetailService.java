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
    
    public EvaluationDetails saveOrUpdateEvaluationDetails(EvaluationDetails evaluationDetails);
    
    public void deleteEvaluationDetails(Long id);
    
    public EvaluationDetails findEvaluationDetailsById(long id);
    
    public List<EvaluationDetails> getAllEvaluationDetails();
}
