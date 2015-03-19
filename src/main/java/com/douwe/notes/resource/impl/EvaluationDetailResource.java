package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.resource.IEvaluationDetailResource;
import com.douwe.notes.service.IEvaluationDetailService;
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
    
    

    @Override
    public EvaluationDetails createEvalDetail(EvaluationDetails evaluationDetails) {
        try {
            return detailService.saveOrUpdateEvaluationDetails(evaluationDetails);
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationDetailResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<EvaluationDetails> getAllEvalDetails() {
        try {
            return detailService.getAllEvaluationDetails();
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationDetailResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public EvaluationDetails getEvalDetail(long id) {
        try {
            EvaluationDetails details = detailService.findEvaluationDetailsById(id);
            if(details == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return details;
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationDetailResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public EvaluationDetails updateEvalDetail(long id, EvaluationDetails evaluationDetails) {
        try {
            EvaluationDetails details = detailService.findEvaluationDetailsById(id);
            if(details != null){
                details.setPourcentage(evaluationDetails.getPourcentage());
                details.setEvaluation(evaluationDetails.getEvaluation());
                details.setTypeCours(evaluationDetails.getTypeCours());
                return detailService.saveOrUpdateEvaluationDetails(details);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationDetailResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteEvalDetail(long id) {
        try {
            detailService.deleteEvaluationDetails(id);
        } catch (ServiceException ex) {
            Logger.getLogger(EvaluationDetailResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
