package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;

import com.douwe.notes.entities.Genre;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.ServiceException;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author root
 */
@Named(value = "importationBean")
@RequestScoped
public class Importation {

    private UploadedFile file;
   

    private Etudiant etudiant;
    

    private List<AnneeAcademique> anneeAcademiques;

    @EJB
    private IEtudiantService etudiantService;

    @EJB
    private IInscriptionService inscriptionService;

    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
    Long idAca;

    String nomFeuille = new String();

    public Importation() {
        etudiant = new Etudiant();

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String saveData() throws ServiceException, IOException  {        
        if (file != null && nomFeuille != null && nomFeuille.trim().length() != 0) {

            etudiantService.importEtudiants(file.getInputstream(), idAca);
        }
        return "importationEtudiant";
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }


    public Long getIdAca() {            
        return idAca;
    }

    public void setIdAca(Long idAca) {
        this.idAca = idAca;
    }

    public String getNomFeuille() {
        return nomFeuille;
    }

    public void setNomFeuille(String nomFeuille) {
        this.nomFeuille = nomFeuille;
    }

    public IEtudiantService getEtudiantService() {
        return etudiantService;
    }

    public void setEtudiantService(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public IInscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(IInscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }



   
}
