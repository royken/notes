/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author root
 */
@Named(value = "parcoursBean")
@RequestScoped
public class ParcoursBean {

    @EJB
    private IParcoursService parcoursService;
    @EJB
    private INiveauService niveauService;
    @EJB
    private IOptionService optionService;
    @EJB
    private IUniteEnseignementService uniteEnseignementService;

    private Parcours parcours = new Parcours();
    private List<Parcours> parcourses;
   
    private List<UniteEnseignement> uniteEnseignements;
    private List<UniteEnseignement> uniteEnseignementChoisis;
    private List<Niveau> niveaux;
    private List<Option> Options;
   
    Long[] idUEs;
    String idN, idO;
    int n = 50;

    public ParcoursBean() throws ServiceException {        
        idUEs = new Long[n];
        uniteEnseignementChoisis = new LinkedList<UniteEnseignement>();
//        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        //      dataModel = new UEDataModel(uniteEnseignements);
    }

    public void saveOrUpdateParcours(ActionEvent actionEvent) throws ServiceException {               
            int i;
            for (i = 0; i < idUEs.length; i++) {
                if (idUEs[i] > 0) {
                    UniteEnseignement ue = uniteEnseignementService.findUniteEnseignementById(idUEs[i]);
                    //c.setId(ids[i]+courses.size());                    
                    uniteEnseignementChoisis.add(ue);
                }
            parcours.setActive(1);
            parcours.setUniteEnseignements(uniteEnseignementChoisis);
            parcours.setNiveau(niveauService.findNiveauById(Integer.parseInt(idN)));
            parcours.setOption(optionService.findOptionById(Integer.parseInt(idO)));
            parcoursService.saveOrUpdateParcours(parcours);           
            if (parcours.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été  enregistré "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été mis à jour"));
            }
             parcours = new Parcours();
            idO = new String();
            idN = new String();
            idUEs = new Long[n];
            uniteEnseignementChoisis = new LinkedList<UniteEnseignement>();
        }
    }

    public void deleteParcours(ActionEvent actionEvent) throws ServiceException {
        if (parcours != null && parcours.getId() != null) {
            parcoursService.deleteParcours(parcours.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été supprimé"));
            parcours = new Parcours();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (parcours != null && parcours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez un parcours avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (parcours != null && parcours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un parcours avant de supprimer "));
        }
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public IUniteEnseignementService getUniteEnseignementService() {
        return uniteEnseignementService;
    }

    public void setUniteEnseignementService(IUniteEnseignementService uniteEnseignementService) {
        this.uniteEnseignementService = uniteEnseignementService;
    }

    public List<UniteEnseignement> getUniteEnseignements() throws ServiceException {
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        return uniteEnseignements;
    }

    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
    }

    public List<Niveau> getNiveaux() throws ServiceException {
        niveaux = niveauService.getAllNiveaux();
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Option> getOptions() throws ServiceException {
        Options = optionService.getAllOptions();
        return Options;
    }

    public void setOptions(List<Option> Options) {
        this.Options = Options;
    }

    public String getIdN() {
        if (parcours != null && parcours.getVersion() != 0) {
            idN = parcours.getNiveau().getId().toString();
        }
        return idN;
    }

    public void setIdN(String idN) {
        this.idN = idN;
    }

    public String getIdO() {
        if (parcours != null && parcours.getVersion() != 0) {
            idO = parcours.getOption().getId().toString();
        }
        return idO;
    }

    public void setIdO(String idO) {
        this.idO = idO;
    }

    public List<Parcours> getParcourses() throws ServiceException {
        parcourses = parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public List<UniteEnseignement> getUniteEnseignementChoisis() {
        //uniteEnseignementChoisis = parcours.getUniteEnseignements();
        return uniteEnseignementChoisis;
    }

    public void setUniteEnseignementChoisis(List<UniteEnseignement> uniteEnseignementChoisis) {
        this.uniteEnseignementChoisis = uniteEnseignementChoisis;
    }

    public Long[] getIdUEs() {
         if (parcours!=null && parcours.getUniteEnseignements()!=null) {
            int i=0;
             for (UniteEnseignement next : parcours.getUniteEnseignements()) {
                 idUEs[i] = next.getId();
                 i++;
             }
        }
        return idUEs;
    }

    public void setIdUEs(Long[] idUEs) {
        this.idUEs = idUEs;
    }
   
          public void verifierEtAffiche(ActionEvent actionEvent) throws ServiceException {
        if (parcours != null && parcours.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgAfficheCours').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionnez le parcours donc vous voulez affichez la listes des UE"));
        }
    } 
    
}
