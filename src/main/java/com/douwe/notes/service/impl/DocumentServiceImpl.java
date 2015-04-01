package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.projection.PvHeader;
import com.douwe.notes.service.IDocumentService;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class DocumentServiceImpl implements IDocumentService{
    
    @EJB
    private INoteService noteService;
    
    @Inject 
    private INiveauDao niveauDao;
    
    @Inject
    private  IOptionDao optionDao;
    
    @Inject
    private ICoursDao coursDao;
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }
    
    
    
    @Override
    public Document produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, Long session) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findById(niveauId);
            
            Option option = optionDao.findById(optionId);
            
            Cours cours = coursDao.findById(coursId);
            
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            
            List<EtudiantNotes> notes = noteService.getAllNotesEtudiants(niveau, option, cours, null, anne, Session.normale);
            
            PvHeader head = new PvHeader();
            head.setAnneeAcademique(anne.toString());
            //head.setCodeUe(cours.);
            head.setCours(cours.getIntitule());
            head.setCredit(cours.getCredit());
            head.setEnseignants(null);
            head.setSession(null);
            head.setNiveau(niveau.getCode());
            Document pv =docPv(notes, head);
            return pv;
            //  head.s
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private Document docPv(List<EtudiantNotes> notes, PvHeader head) throws DocumentException, FileNotFoundException, BadElementException, IOException{
        
        String documentName = "pv"+head.getCours()+head.getNiveau()+head.getOption()+head.getDepartement()+head.getAnneeAcademique()+".pdf";
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(documentName));
        doc.open();
        doc.setPageSize(new Rectangle(400, 100));
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
        
        
        // Définition de l'entete du document
        String francais = "République du Cameroun\n"
                + "****\n"
                + "Paix -- Travail -- Patrie\n"
                + "****\n"
                + "Ministère lEnseignement Supérieur\n"
                + "****\n"
                + "Université de Maroua\n"
                + "****\n"
                + "Institut Supérieur du Sahel";
        Phrase french = new Phrase(francais,bf12);
        Paragraph frecnch = new Paragraph(french);
        frecnch.setAlignment(Element.ALIGN_CENTER);
        float widths2[] = {3, 7, 3};
        PdfPTable header = new PdfPTable(widths2);
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase(frecnch));
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell1);
        Image logo = Image.getInstance(String.format("logo.png"));
        logo.scaleToFit(200, 100);
        PdfPCell cel = new PdfPCell(logo);
        cel.setBorderColor(BaseColor.WHITE);
        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cel.setVerticalAlignment(Element.ALIGN_CENTER);
        header.addCell(cel);
        String english = "Republic of Cameroon\n"
                + "****\n"
                + "Peace -- Work -- Fatherland\n"
                + "****\n"
                + "Ministry of Higher Education\n"
                + "****\n"
                + "University of Maroua\n"
                + "****\n"
                + "The Higher Institute of the Sahel";
        
        
        Phrase zo = new Phrase(english, bf12);
        Paragraph eng = new Paragraph(zo);
        eng.setAlignment(Element.ALIGN_CENTER);
        cell1 = new PdfPCell(new Phrase(eng));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorderColor(BaseColor.WHITE);
        header.addCell(cell1);
        doc.add(header);
        
        // Fin de l'entete
        
        // Définition du formulaire d'entete
        PdfPTable table2 = new PdfPTable(6);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Mention : " + head.getDepartement(), bf12));
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Titre de l'UE : " + head.getCours(), bf12));
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Mention :" + head.getDepartement(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(2);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Parcours : " + head.getNiveau()+head.getOption(), bf12));
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Code de l'UE :" + head.getCodeUe(), bf12));
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("OPTION : " + head.getOption(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Credit : " + head.getCredit(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Niveau : " + head.getNiveau(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Année Académique :" + head.getAnneeAcademique(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("SEMESTRE :" + head.getSemestre(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("SESSION :" + head.getSession(), bf12));
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);



        cell = new PdfPCell(new Phrase("Enseignants :" + head.getEnseignants(), bf12));
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);

        doc.add(table2);
        doc.add(new Paragraph("\n\n"));
        
        
        // Fin du formulaire
        
        doc.add(new Paragraph("\n\n"));
        
        // Tableau de notes
        
        Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        float relativeWidths[] = {2, 4, 11, 5, 5, 5, 5, 3};
        Font bf13 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        PdfPTable table = new PdfPTable(relativeWidths);
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.getDefaultCell().setFixedHeight(20);
        //  table.getDefaultCell().set

        table.addCell(new Phrase("No", bf));
        table.addCell(new Phrase("Matricule", bf));
        table.addCell(new Phrase("NOM(s) et PRENOM(s)", bf));
        for (Map.Entry<String, Double> e : notes.get(0).getNote().entrySet()) {
               //     System.out.print(String.format("%s - %.2f\t", e.getKey(), e.getValue()));
                    table.addCell(new Phrase(String.format("%s/20 ", e.getKey()), bf));
        }
      //  table.addCell(new Phrase("CC/20", bf));
       // table.addCell(new Phrase("TPE/20", bf));
       // table.addCell(new Phrase("EX/20", bf));
        table.addCell(new Phrase("Moy/20", bf));
        table.addCell(new Phrase("Grade", bf));
        doc.add(table);
        // float relativeWidths[] = {2,5,8,5,5,5,5,5};
        PdfPTable table3 = new PdfPTable(relativeWidths);
        table3.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        table3.getDefaultCell().setFixedHeight(20);
        for (int i = 0; i < notes.size(); i++) {
            table3.addCell(new Phrase(String.valueOf(i + 1), bf13));
            table3.addCell(new Phrase(notes.get(i).getMatricule(), bf13));
            table3.addCell(new Phrase(notes.get(i).getNom(), bf13));
            for (Map.Entry<String, Double> e : notes.get(i).getNote().entrySet()) {
                   // System.out.print(String.format("%s - %.2f\t", e.getKey(), e.getValue()));
                    table3.addCell(new Phrase(String.format("%.2f", e.getValue()), bf13));
                }
         //   table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("CC")), bf13));
           // table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("TPE")), bf13));
           // table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("EE")), bf13));
            table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getMoyenne(), bf13)));
            
            table3.addCell(new Phrase(transformNoteGrade(notes.get(i).getMoyenne()), bf13));
        }
        doc.add(table3);
        doc.add(new Phrase("\n"));
        // Fin du tableau de notes
        
        // Partie signatures
        
        float widths[] = {18, 18, 13};
        PdfPTable signature = new PdfPTable(widths);
        PdfPCell celle;
        celle = new PdfPCell(new Phrase("Le Président (e)", bf12));
        celle.setBorderColor(BaseColor.WHITE);
        signature.addCell(celle);
        celle = new PdfPCell(new Phrase("Vice Président(e)", bf12));
        celle.setBorderColor(BaseColor.WHITE);
        signature.addCell(celle);
        celle = new PdfPCell(new Phrase("Membre(s)", bf12));
        celle.setBorderColor(BaseColor.WHITE);
        signature.addCell(celle);
        doc.add(signature);

        doc.add(new Phrase("\n\n"));
        // Fin partie signatures
        doc.close();
        return doc;
        
    }
    
    private static String transformNoteGrade(double note){
        if(note <= 20 && note >= 16){
            return "A+";
        }
        if(note < 16 && note >= 15){
            return "A";
        }
        if(note < 15 && note >= 14){
            return "B+";
        }
        if(note < 14 && note >= 13){
            return "B";
        }
        if(note < 13 && note >= 12){
            return "B-";
        }
        if(note < 12 && note >= 11){
            return "C+";
        }
        if(note < 11 && note >= 10){
            return "C";
        }
        if(note < 10 && note >= 9){
            return "C-";
        }
        if(note < 9 && note >= 8){
            return "D+";
        }
        if(note < 8 && note >= 7){
            return "D";
        }
        if(note < 7 && note >= 6){
            return "E";
        }
        
            return "F";
        
    }

    
    
}
