/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Genre;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.ServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author root
 */
@Named(value = "importationBean")
@RequestScoped
public class Importation {

    private UploadedFile file;

    private Inscription inscription;

    private Etudiant etudiant;

    private AnneeAcademique anneeAcademique;

    private List<AnneeAcademique> anneeAcademiques;

    private Niveau niveau;

    private Option option;

    private Parcours parcours;

    @EJB
    private IEtudiantService etudiantService;

    @EJB
    private IInscriptionService inscriptionService;

    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;

    @EJB
    private INiveauService niveauService;

    @EJB
    private IParcoursService parcoursService;
    @EJB
    private IOptionService optionService;
    
    String idAca = new String();
    
    String nomFeuille = new String();

    public Importation() {
        parcours = new Parcours();
        niveau = new Niveau();
        option = new Option();
        etudiant = new Etudiant();
        inscription = new Inscription();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String saveData() throws ServiceException, ParseException, IOException {
        System.err.println("-------------- 1 "+ file.getInputstream());
        if (file != null && nomFeuille!=null && nomFeuille.trim().length()!=0) {
            //final Workbook workbook = WorkbookFactory.create(file);
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputstream());
            //POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("etudiant.xlsx"));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            final HSSFSheet sheet = wb.getSheet("etudiant");
            //final HSSFSheet sheet = wb.getSheetName("etudiant");
            int index = 1;
            HSSFRow row = sheet.getRow(index++);
            System.err.println("-------------- 2 ");
            while (row != null) {

                etudiant.setNom(row.getCell(1).getStringCellValue());
                etudiant.setEmail(row.getCell(4).getStringCellValue());                
                etudiant.setDateDeNaissance(new SimpleDateFormat().parse(row.getCell(2).getStringCellValue()));
                if (row.getCell(6).getStringCellValue().trim().toLowerCase().compareTo("feminin") == 0) {
                    etudiant.setGenre(Genre.feminin);
                }
                if (row.getCell(6).getStringCellValue().trim().toLowerCase().compareTo("masculin") == 0) {
                    etudiant.setGenre(Genre.masculin);
                }
                etudiant.setLieuDeNaissance(row.getCell(3).getStringCellValue());
                etudiant.setMatricule(row.getCell(0).getStringCellValue());
                etudiant.setNumeroTelephone(row.getCell(5).getStringCellValue());
                etudiantService.saveOrUpdateEtudiant(etudiant);
                inscription.setParcours(null);
//                niveau = niveauService.findNiveauByCode(row.getCell(7).getStringCellValue());
//                option = optionService.findOptionByCode(row.getCell(8).getStringCellValue());
//                parcours = parcoursService.findParcoursByNiveauAndOption(niveau.getId(), option.getId());
//                inscription.setParcours(parcours);
                anneeAcademique = anneeAcademiqueService.findAnneeById(Integer.parseInt(idAca));
                inscription.setAnneeAcademique(anneeAcademique);
                inscription.setEtudiant(etudiant);
                inscriptionService.saveOrUpdateInscription(inscription);
                parcours = new Parcours();
                niveau = new Niveau();
                option = new Option();
                etudiant = new Etudiant();
                inscription = new Inscription();
                row = sheet.getRow(index++);
            }

        }
        return "importationEtudiant";
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
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

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public String getIdAca() {
        if(anneeAcademique!=null)
            idAca=anneeAcademique.getId().toString();
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
