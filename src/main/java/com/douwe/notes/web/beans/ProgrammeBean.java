/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.IProgrammeService;
import com.douwe.notes.service.IUniteEnseignementService;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author root
 */
@Named(value = "programmeBean")
@RequestScoped
public class ProgrammeBean {

    @EJB
    private IProgrammeService programmeService;
    @EJB
    private IUniteEnseignementService uniteEnseignementService;
    @EJB
    private IParcoursService parcoursService;
    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;

    private Programme programme = new Programme();
    private List<Programme> programmes;
    private List<AnneeAcademique> anneeAcademiques;
    private List<UniteEnseignement> uniteEnseignements;
    private List<Parcours> parcourses;
    private String message;
    String idAca, idUE, idP;

    public ProgrammeBean() {
    }

    public String saveOrUpdateProgramme() {
        if (programme != null) {

            programme.setAnneeAcademique(anneeAcademiqueService.findAnneeById(Integer.parseInt(idAca)));
            programme.setParcours(parcoursService.findParcoursById(Integer.parseInt(idP)));
            programme.setUniteEnseignement(uniteEnseignementService.findUniteEnseignementById(Integer.parseInt(idUE)));
            programmeService.saveOrUpdateProgramme(programme);
            programme = new Programme();
            idAca = new String();
            idP = new String();
            idUE = new String();
        }
        return "saveOrUpdateProgramme";
    }

    public String deleteProgramme() {
        if (programme != null && programme.getId() > 0) {
            message = "Suppression reussi ";
            programmeService.deleteProgramme(programme.getId());
            programme = new Programme();
        }
        return "deleteProgramme";
    }

    public String choix(int n) {
        if (n == 1) {
            programme = new Programme();
            message = "Enregistrement reussi ";
            return "saveProgramme";
        } else if (n == 2 && programme != null && programme.getVersion() >= 1) {
            message = "Mise à jour reussi ";
            return "updateProgramme";
        }
        programme = new Programme();
        return "programme";
    }

    public void notification(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Succes", message));
    }

    public IProgrammeService getProgrammeService() {
        return programmeService;
    }

    public void setProgrammeService(IProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    public IUniteEnseignementService getUniteEnseignementService() {
        return uniteEnseignementService;
    }

    public void setUniteEnseignementService(IUniteEnseignementService uniteEnseignementService) {
        this.uniteEnseignementService = uniteEnseignementService;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public List<Programme> getProgrammes() {
        programmes = programmeService.getAllProgrammes();
        return programmes;
    }

    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
    }

    public List<AnneeAcademique> getAnneeAcademiques() {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public List<UniteEnseignement> getUniteEnseignements() {
        uniteEnseignements = uniteEnseignementService.getAllUniteEnseignements();
        return uniteEnseignements;
    }

    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
    }

    public List<Parcours> getParcourses() {
        parcourses = parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public String getIdAca() {
        if (programme != null && programme.getVersion() != 0) {
            idAca = programme.getAnneeAcademique().getId().toString();
        }
        return idAca;
    }

    public void setIdAca(String idAca) {
        this.idAca = idAca;
    }

    public String getIdUE() {
        if (programme != null && programme.getVersion() != 0) {
            idUE = programme.getUniteEnseignement().getId().toString();
        }
        return idUE;
    }

    public void setIdUE(String idUE) {
        this.idUE = idUE;
    }

    public String getIdP() {
        if (programme != null && programme.getVersion() != 0) {
            idP = programme.getParcours().getId().toString();
        }
        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

}
