package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.service.IEvaluationDetailService;
import com.douwe.notes.service.IEvaluationService;
import com.douwe.notes.service.ITypeCoursService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named(value = "typeCoursBean")
@RequestScoped
public class TypeCoursBean {

    @EJB
    private ITypeCoursService typeCoursService;
    @EJB
    private IEvaluationDetailService evaluationDetailService;

    private EvaluationDetails evaluationDetails;
    private List<EvaluationDetails> evaluationDetailses;
    @EJB
    private IEvaluationService evaluationService;
    private List<Evaluation> evaluations;
    private Evaluation evaluation;
    private TypeCours typeCours = new TypeCours();
    private List<TypeCours> typeCourss;
    private Long idE = 0L;

    public TypeCoursBean() {
        evaluationDetails = new EvaluationDetails();
        evaluation = new Evaluation();
    }

    public void saveOrUpdateTypeCours(ActionEvent actionEvent) throws ServiceException {
        System.out.println("" + typeCours);
        if (typeCours != null && typeCours.getNom() != null) {
            typeCoursService.saveOrUpdateTpyeCours(typeCours);
            if (typeCours.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", typeCours.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", typeCours.getNom() + " a été enregistré"));
            }

            typeCours = new TypeCours();
        }
    }

    public void deleteTypeCours(ActionEvent actionEvent) throws ServiceException {
        System.out.println("" + typeCours);
        if (typeCours != null && typeCours.getId() != null) {
            typeCoursService.deleteTypeCours(typeCours.getId());            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", typeCours.getNom() + " a été supprimé"));
            typeCours = new TypeCours();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (evaluationDetails != null && evaluationDetails.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdateEV').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une évaluation avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (evaluationDetails != null && evaluationDetails.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('c').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une évaluation avant de supprimer "));
        }
    }

    public void verifierEtOuvrir(ActionEvent actionEvent) throws ServiceException {
        System.out.println("" + typeCours);
        if (typeCours != null && typeCours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", " ne marche pas "));
        }
    }

    public void saveEvaluationDetails(ActionEvent actionEvent) throws ServiceException {
        evaluationDetailService.addEvaluation(typeCours.getId(), idE, evaluationDetails.getPourcentage());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", "l'évaluation a été enregistrée"));
        evaluationDetails = new EvaluationDetails();
        evaluation = new Evaluation();
        typeCours = new TypeCours();
        idE = 0L;
    }

    public void updateEvaluationDetails(ActionEvent actionEvent) throws ServiceException {
        int p = evaluationDetails.getPourcentage();        
        evaluationDetails=evaluationDetailService.findEvaluationDetailsById(evaluationDetails.getId());
        System.out.println(""+typeCours.getId()+" " +evaluationDetails.getEvaluation().getId()+" "+ p);
        evaluationDetailService.modifierEvaluation(typeCours.getId(), evaluationDetails.getEvaluation().getId(), p);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", "l'évaluation a été enregistrée"));
        evaluationDetails = new EvaluationDetails();
        evaluation = new Evaluation();
        typeCours = new TypeCours();
        idE = 0L;
    }

    public void deleteEvaluationDetails(ActionEvent actionEvent) throws ServiceException {
        System.out.println("deleteEvaluationDetails" + evaluationDetails + typeCours + " idE " + idE);
        if (evaluationDetails != null && evaluationDetails.getId() != null) {
            //evaluationDetailService.supprimerEvaluation(typeCours.getId(), evaluation.getId());
            evaluationDetailService.deleteEvaluationDetails(evaluationDetails.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", "l'évaluation a été supprimé"));
            evaluationDetails = new EvaluationDetails();
        }
    }

    public ITypeCoursService getTypeCoursService() {
        return typeCoursService;
    }

    public void setTypeCoursService(ITypeCoursService typeCoursService) {
        this.typeCoursService = typeCoursService;
    }

    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }

    public List<TypeCours> getTypeCourss() throws ServiceException {
        typeCourss = typeCoursService.getAllTypeCours();
        return typeCourss;
    }

    public void setTypeCourss(List<TypeCours> typeCourss) {
        this.typeCourss = typeCourss;
    }

    public IEvaluationDetailService getEvaluationDetailService() {
        return evaluationDetailService;
    }

    public void setEvaluationDetailService(IEvaluationDetailService evaluationDetailService) {
        this.evaluationDetailService = evaluationDetailService;
    }

    public EvaluationDetails getEvaluationDetails() {
        return evaluationDetails;
    }

    public void setEvaluationDetails(EvaluationDetails evaluationDetails) {
        this.evaluationDetails = evaluationDetails;
    }
    public List<EvaluationDetails> findAll(TypeCours typeCours) throws ServiceException{
    return evaluationDetailService.findEvaluationDetailsByTypeCours(typeCours.getId());
    }
    public List<EvaluationDetails> getEvaluationDetailses() throws ServiceException {
        evaluationDetailses = evaluationDetailService.findEvaluationDetailsByTypeCours(idE);
        return evaluationDetailses;
    }

    public void setEvaluationDetailses(List<EvaluationDetails> evaluationDetailses) {
        this.evaluationDetailses = evaluationDetailses;
    }

    public IEvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public List<Evaluation> getEvaluations() throws ServiceException {
        evaluations = evaluationService.getAllEvaluations();
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Long getIdE() {
        if (evaluation != null && evaluation.getId() != null) {
            idE = evaluation.getId();
        }
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }
}
