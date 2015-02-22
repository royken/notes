package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.resource.IEvaluationDetailResource;
import com.douwe.notes.service.IEvaluationDetailService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/evalDetail")
public class EvaluationDetailResource implements IEvaluationDetailResource{
    
    @EJB
    private IEvaluationDetailService detailService;

    public IEvaluationDetailService getDetailService() {
        return detailService;
    }

    public void setDetailService(IEvaluationDetailService detailService) {
        this.detailService = detailService;
    }
    
    

    public EvaluationDetails createEvalDetail(EvaluationDetails evaluationDetails) {
        return detailService.saveOrUpdateEvaluationDetails(evaluationDetails);
    }

    public List<EvaluationDetails> getAllEvalDetails() {
        return detailService.getAllEvaluationDetails();
    }

    public EvaluationDetails getEvalDetail(long id) {
        EvaluationDetails details = detailService.findEvaluationDetailsById(id);
        if(details == null){
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return details;
    }

    public EvaluationDetails updateEvalDetail(long id, EvaluationDetails evaluationDetails) {
        EvaluationDetails details = detailService.findEvaluationDetailsById(id);
        if(details != null){
            details.setPourcentage(evaluationDetails.getPourcentage());
            details.setEvaluation(evaluationDetails.getEvaluation());
            details.setTypeCours(evaluationDetails.getTypeCours());
            return detailService.saveOrUpdateEvaluationDetails(details);
        }
        return null;
    }

    public void deleteEvalDetail(long id) {
        detailService.deleteEvaluationDetails(id);
    }
    
    
}
