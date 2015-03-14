package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.resource.IEvaluationResource;
import com.douwe.notes.service.IEvaluationService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            return evaluationService.saveOrUpdateEvaluation(evaluation);
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Evaluation> getAllEvaluations() {
        try {
            return evaluationService.getAllEvaluations();
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Evaluation getEvaluation(long id) {
        try {
            Evaluation evaluation = evaluationService.findEvaluationById(id);
            if(evaluation == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return evaluation;
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Evaluation updateEvaluation(long id, Evaluation evaluation) {
        try {
            Evaluation evaluation1 = evaluationService.findEvaluationById(id);
            if(evaluation1 != null){
                evaluation1.setCode(evaluation.getCode());
                evaluation1.setDescription(evaluation1.getDescription());
                return evaluationService.saveOrUpdateEvaluation(evaluation1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteEvaluation(long id) {
        try {
            evaluationService.deleteEvaluation(id);
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
