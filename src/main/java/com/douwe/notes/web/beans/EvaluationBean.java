package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.service.IEvaluationService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped; 
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent; 
import org.primefaces.context.RequestContext;


@Named(value = "evaluationBean")
@RequestScoped
public class EvaluationBean {

    @EJB
    private IEvaluationService evaluationService;
    private Evaluation evaluation = new Evaluation();
    private List<Evaluation> evaluations; 
    

   
    public EvaluationBean() {        
    
    }


public void saveOrUpdateEvaluation(ActionEvent actionEvent) throws ServiceException {
        if (evaluation != null && evaluation.getCode() != null) {
            System.out.println("------"+evaluation);
            evaluationService.saveOrUpdateEvaluation(evaluation);
            if (evaluation.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", evaluation.getCode() + " a été enregistré"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", evaluation.getCode()+ " a été mis à jour "));
            }

            evaluation = new Evaluation();
        }
    }

    public void deleteEvaluation(ActionEvent actionEvent) throws ServiceException {
        if (evaluation != null && evaluation.getId() != null) {
            evaluationService.deleteEvaluation(evaluation.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", evaluation.getCode() + " a été supprimé"));
            evaluation = new Evaluation();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (evaluation != null && evaluation.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un evaluation avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (evaluation != null && evaluation.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un evaluation avant de supprimer "));
        }
    }

    public IEvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }
 
    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {        
        this.evaluation = evaluation;
    }

    public List<Evaluation> getEvaluations() throws ServiceException {        
        evaluations = evaluationService.getAllEvaluations();        
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}