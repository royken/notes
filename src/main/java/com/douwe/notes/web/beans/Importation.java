/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Genre;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.ServiceException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
    String idAca = new String();

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

    public String saveData() throws ServiceException, ParseException, IOException, InvalidFormatException {        
        if (file != null && nomFeuille != null && nomFeuille.trim().length() != 0) {
        Workbook workbook = WorkbookFactory.create(file.getInputstream());
        final Sheet sheet = workbook.getSheet(nomFeuille);
        int index = 1;
        Row row = sheet.getRow(index++);                        
            while (row != null) {

                etudiant.setNom(row.getCell(1).getStringCellValue());
                etudiant.setEmail(row.getCell(4).getStringCellValue());
                etudiant.setDateDeNaissance(row.getCell(2).getDateCellValue());
                if (row.getCell(6).getStringCellValue().trim().toLowerCase().compareTo("feminin") == 0) {
                    etudiant.setGenre(Genre.feminin);
                }
                if (row.getCell(6).getStringCellValue().trim().toLowerCase().compareTo("masculin") == 0) {
                    etudiant.setGenre(Genre.masculin);
                }
                etudiant.setLieuDeNaissance(row.getCell(3).getStringCellValue());
                etudiant.setMatricule(row.getCell(0).getStringCellValue());
                etudiant.setNumeroTelephone(row.getCell(5).getNumericCellValue()+"");
                etudiantService.saveOrUpdateEtudiant(etudiant);                               
                System.err.println("------------> " + row.getCell(7).getStringCellValue().toLowerCase());                
                inscriptionService.inscrireEtudiant(etudiant,row.getCell(7).getStringCellValue().toLowerCase(),row.getCell(8).getStringCellValue().toUpperCase(),Long.valueOf(idAca));                
                etudiant = new Etudiant();                
                row = sheet.getRow(index++);
            }

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

    public String getIdAca() {            
        return idAca;
    }

    public void setIdAca(String idAca) {
        this.idAca = idAca;
    }

    public String getNomFeuille() {
        return nomFeuille;
    }

    public void setNomFeuille(String nomFeuille) {
        this.nomFeuille = nomFeuille;
    }



   
}
