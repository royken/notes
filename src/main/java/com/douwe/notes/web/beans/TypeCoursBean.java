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
    private TreeNode rootWithType;
    private TreeNode[] selectedNodes;
    private TypeCours typeCours = new TypeCours();
    private List<TypeCours> typeCourss;
   
    private TreeNode selectedNode;
    private Long idE = 0L;

    public TypeCoursBean() {
        evaluationDetails = new EvaluationDetails();        
    }


    public void saveOrUpdateTypeCours(ActionEvent actionEvent) throws ServiceException {        
        System.out.println(""+typeCours);
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
        if (typeCours != null && typeCours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un type de cours avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (typeCours != null && typeCours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un type de cours avant de supprimer "));
        }
    }

    public void saveOrUpdateEvaluationDetails(ActionEvent actionEvent) throws ServiceException {
        
        if (evaluationDetails != null ) {
            if (idE != 0L) {
                evaluation = evaluationService.findEvaluationById(idE);
                evaluationDetails.setEvaluation(evaluation);
            }            
            typeCours=typeCoursService.findTypeCoursById(3702L);
            evaluationDetails.setEvaluation(evaluation);
            evaluationDetails.setTypeCours(typeCours);
            evaluationDetailService.saveOrUpdateEvaluationDetails(evaluationDetails);            
            if (evaluationDetails.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", evaluationDetails.getEvaluation().getCode() + " : " + evaluationDetails.getPourcentage() + "%" + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", evaluationDetails.getEvaluation().getCode() + " : " + evaluationDetails.getPourcentage() + "%" + " a été enregistré"));
            }

            evaluationDetails = new EvaluationDetails();
            evaluation = new Evaluation();
            idE = 0L;
            displaySelected();            
            rootWithType = new DefaultTreeNode("default","liste de type de cours", null);
        }
    }

    public void deleteEvaluationDetails(ActionEvent actionEvent) throws ServiceException {
        System.out.println("" + evaluationDetails);
        if (evaluationDetails != null && evaluationDetails.getId() != null) {
            
            evaluationDetailService.deleteEvaluationDetails(evaluationDetails.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", evaluationDetails.getEvaluation().getCode() + " : " + evaluationDetails.getPourcentage() + "%" + " a été supprimé"));
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

    public List<EvaluationDetails> getEvaluationDetailses() {
        return evaluationDetailses;
    }

    public void setEvaluationDetailses(List<EvaluationDetails> evaluationDetailses) {
        this.evaluationDetailses = evaluationDetailses;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
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



        
    public void displaySelected() {
        if (selectedNode != null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString()));
        }

        if (selectedNodes != null && selectedNodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : selectedNodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString()));
        }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", "selectNode est null"));
        selectedNode = null;
        selectedNodes = null;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString()));
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString()));
    }

    public void onNodeSelect(NodeSelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString()));
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString()));

    }

    public void deleteNode() {
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);

        selectedNode = null;
    }


    public TreeNode getRootWithType() throws ServiceException {
                typeCourss = typeCoursService.getAllTypeCours();
                TreeNode[] n = new TreeNode[typeCourss.size()];
                rootWithType = new DefaultTreeNode("default","liste de type de cours", null);
                int i=0;
                
        for (TypeCours typeCours1 : typeCourss) {
            n[i] = new DefaultTreeNode("node", typeCours1.getNom(), rootWithType);            
            //a remplacer
            evaluationDetailses = evaluationDetailService.getAllEvaluationDetails();
            for (EvaluationDetails evaluationDetailse : evaluationDetailses) {
                TreeNode n2 = new DefaultTreeNode("leaf", evaluationDetailse.getEvaluation().getCode() + " : " + evaluationDetailse.getPourcentage() + "%", n[i]);
            }            
            i=i+1;
        }    
        System.out.println("" + evaluationDetailses);
        return rootWithType;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

}
