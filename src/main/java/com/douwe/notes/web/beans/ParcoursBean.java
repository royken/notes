/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.ServiceException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
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
    private IDepartementService departementService;

    private Parcours parcours = new Parcours();

    private List<Parcours> parcourses;
    private List<Niveau> niveaux;
    private List<Option> Options;
    private List<Departement> departements;

    private SelectItem[] manufacturerOptions;

    Long idN = 0L, idO = 0L, idD = 0L;

    public ParcoursBean() throws ServiceException {
        departements = new ArrayList<Departement>();
    }

    private void init() throws ServiceException {
        departements = departementService.getAllDepartements();
        manufacturerOptions = new SelectItem[departements.size() + 1];
        manufacturerOptions[0] = new SelectItem("", "Selectionner");
        for (int j = 0; j < departements.size(); j++) {
            manufacturerOptions[j + 1] = new SelectItem(departements.get(j).getCode(), departements.get(j).getCode());
        }
    }

    public void saveOrUpdateParcours(ActionEvent actionEvent) throws ServiceException {
        parcours.setActive(1);
        if (idN != null) {
            parcours.setNiveau(niveauService.findNiveauById(idN));
        }
        if (idO != null) {
            parcours.setOption(optionService.findOptionById(idO));
        }
        parcoursService.saveOrUpdateParcours(parcours);
        if (parcours.getId() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été  enregistré "));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été mis à jour"));
        }
        idD = parcours.getOption().getDepartement().getId();
        parcours = new Parcours();
        idO = 0L;
        idN = 0L;

    }

    public void deleteParcours(ActionEvent actionEvent) throws ServiceException {
        if (parcours != null && parcours.getId() != null) {
            parcoursService.deleteParcours(parcours.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", parcours.getNiveau().getCode() + "/" + parcours.getOption().getCode() + " a été supprimé"));
            idD = parcours.getOption().getDepartement().getId();
            parcours = new Parcours();
        }
    }

    public String filtrer() throws ServiceException {
        return "parcours";
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

    public List<Niveau> getNiveaux() throws ServiceException {
        niveaux = niveauService.getAllNiveaux();
        init();
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

    public Long getIdN() {
        if (parcours != null && parcours.getId() != null) {
            idN = parcours.getNiveau().getId();
        }
        return idN;
    }

    public void setIdN(Long idN) {
        this.idN = idN;
    }

    public Long getIdO() {
        if (parcours != null && parcours.getId() != null) {
            idN = parcours.getOption().getId();
        }
        return idO;
    }

    public void setIdO(Long idO) {
        this.idO = idO;
    }

    public List<Parcours> getParcourses() throws ServiceException {
        if (idD != 0L) {
            //parcourses = parcoursService.findParcoursByDepartement(Long idD);
            parcours = parcoursService.findParcoursById(6003L);
            parcourses = new LinkedList<Parcours>();
            parcourses.add(parcours);
            parcours = new Parcours();
        } else {
            parcourses = parcoursService.getAllParcours();
        }
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    public SelectItem[] getManufacturerOptions() {
        return manufacturerOptions;
    }

    public void setManufacturerOptions(SelectItem[] manufacturerOptions) {
        this.manufacturerOptions = manufacturerOptions;
    }

    public List<Departement> getDepartements() throws ServiceException {
        departements = departementService.getAllDepartements();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }
}
