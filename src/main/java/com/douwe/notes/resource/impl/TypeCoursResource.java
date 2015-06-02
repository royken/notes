package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.resource.ITypeCoursResource;
import com.douwe.notes.service.IEvaluationDetailService;
import com.douwe.notes.service.ITypeCoursService;
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
@Path("/typeCours")
public class TypeCoursResource implements ITypeCoursResource{
    
    @EJB
    private ITypeCoursService typeCoursService;
    
    @EJB
    private IEvaluationDetailService detailService;

    public ITypeCoursService getTypeCoursService() {
        return typeCoursService;
    }

    public IEvaluationDetailService getDetailService() {
        return detailService;
    }

    public void setDetailService(IEvaluationDetailService detailService) {
        this.detailService = detailService;
    }
    
    

    public void setTypeCoursService(ITypeCoursService typeCoursService) {
        this.typeCoursService = typeCoursService;
    }

    @Override
    public TypeCours createTypeCours(TypeCours typeCours) {
        try {
            return typeCoursService.saveOrUpdateTpyeCours(typeCours);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public TypeCours getTypeCours(long id) {
        try {
            TypeCours typeCours = typeCoursService.findTypeCoursById(id);
            if(typeCours == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return typeCours;
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public TypeCours updateTypeCours(long id, TypeCours typeCours) {
        try {
            TypeCours typeCours1 = typeCoursService.findTypeCoursById(id);
            if(typeCours1 != null){
                typeCours1.setNom(typeCours.getNom());
                return typeCoursService.saveOrUpdateTpyeCours(typeCours1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<TypeCours> getAllTypeCours() {
        try {
            return typeCoursService.getAllTypeCours();
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteTypeCours(long id) {
        try {
            typeCoursService.deleteTypeCours(id);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public List<EvaluationDetails> getEvaluationByTypeCours(long id) {
        try {
            return detailService.findEvaluationDetailsByTypeCours(id);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public EvaluationDetails saveEvaluationByTypeCours(long id, EvaluationDetails details) {
        try {
            TypeCours tCours = typeCoursService.findTypeCoursById(id);
            if(tCours == null){
                throw  new ServiceException("Ressource non trouvée");
            }
            details.setTypeCours(tCours);
            return detailService.saveOrUpdateEvaluationDetails(details);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public EvaluationDetails updateEvalDetailByTypeCours(long idTCours, long idEvaluation, EvaluationDetails evaluationDetails) {
        try {
            EvaluationDetails details = detailService.findEvaluationDetailsById(idEvaluation);
            if(details == null){
                throw new ServiceException("Resource not found");
            }
            details.setPourcentage(evaluationDetails.getPourcentage());
            return detailService.saveOrUpdateEvaluationDetails(details);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteEvalDetailByTypeCours(long idTCours, long idEvaluation) {
        try {
            detailService.deleteEvaluationDetails(idEvaluation);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public EvaluationDetails getEvalDetailById(long idTCours, long idEvaluation) {
        try {
            EvaluationDetails details = detailService.findEvaluationDetailsById(idEvaluation);
            if(details == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return details;
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
