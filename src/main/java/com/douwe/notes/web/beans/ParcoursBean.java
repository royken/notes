/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IUniteEnseignementService;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.inject.Named;

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
    private String message;
    String idN, idO;
    UEDataModel dataModel;

    public ParcoursBean() {
        uniteEnseignementChoisis = new LinkedList<UniteEnseignement>();
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        dataModel = new UEDataModel(uniteEnseignements);
    }

    public String saveOrUpdateParcours() {
        if (parcours != null) {

            //parcours.setUniteEnseignements(uniteEnseignementService.findUniteEnseignementById(Integer.parseInt(id)));
            if (uniteEnseignementChoisis != null) {
                for (UniteEnseignement UE : uniteEnseignementChoisis) {
                    System.out.println("-------------------------> interieur" + UE);
                }
                parcours.setUniteEnseignements(uniteEnseignementChoisis);
            }
            System.out.println("-------------------------> exterieur");
            parcours.setNiveau(niveauService.findNiveauById(Integer.parseInt(idN)));
            parcours.setOption(optionService.findOptionById(Integer.parseInt(idO)));
            parcoursService.saveOrUpdateParcours(parcours);
            parcours = new Parcours();
            idO = new String();
            idN = new String();
        }
        return "saveOrUpdateParcours";
    }

    public String deleteParcours() {
        if (parcours != null && parcours.getId() > 0) {
            message = "Suppression reussi ";
            parcoursService.deleteParcours(parcours.getId());
            parcours = new Parcours();
        }
        return "deleteParcours";
    }

    public String choix(int n) {
        if (n == 1) {
            parcours = new Parcours();
            message = "Enregistrement reussi ";
            return "saveParcours";
        } else if (n == 2 && parcours != null && parcours.getVersion() >= 1) {
            message = "Mise à jour reussi ";
            return "updateParcours";
        }
        parcours = new Parcours();
        return "parcours";
    }

    public void notification(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Succes", message));
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

    public List<UniteEnseignement> getUniteEnseignements() {
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        return uniteEnseignements;
    }

    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
    }

    public List<Niveau> getNiveaux() {
        niveaux = niveauService.getAllNiveaux();
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Option> getOptions() {
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

    public List<Parcours> getParcourses() {
        parcourses = parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public List<UniteEnseignement> getUniteEnseignementChoisis() {
        uniteEnseignementChoisis = parcours.getUniteEnseignements();
        return uniteEnseignementChoisis;
    }

    public void setUniteEnseignementChoisis(List<UniteEnseignement> uniteEnseignementChoisis) {
        this.uniteEnseignementChoisis = uniteEnseignementChoisis;
    }

    public UEDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(UEDataModel dataModel) {
        this.dataModel = dataModel;
    }

}
