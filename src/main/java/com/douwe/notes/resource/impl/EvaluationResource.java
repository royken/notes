package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.resource.IEvaluationResource;
import com.douwe.notes.service.IEvaluationService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@Path("/evaluations")
public class EvaluationResource implements IEvaluationResource{
    
    @EJB
    private IEvaluationService evaluationService;

    public IEvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }
    
    

    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationService.saveOrUpdateEvaluation(evaluation);
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }

    public Evaluation getEvaluation(long id) {
        Evaluation evaluation = evaluationService.findEvaluationById(id);
        if(evaluation == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return evaluation;
    }

    public Evaluation updateEvaluation(long id, Evaluation evaluation) {
        Evaluation evaluation1 = evaluationService.findEvaluationById(id);
        if(evaluation1 != null){
            evaluation1.setCode(evaluation.getCode());
            evaluation1.setDescription(evaluation1.getDescription());
            return evaluationService.saveOrUpdateEvaluation(evaluation1);
        }
        return null;
    }

    public void deleteEvaluation(long id) {
        evaluationService.deleteEvaluation(id);
    }
    
}
