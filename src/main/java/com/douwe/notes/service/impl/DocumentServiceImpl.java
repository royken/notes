package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Departement;
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
import java.io.OutputStream;
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
public class DocumentServiceImpl implements IDocumentService {

    @EJB
    private INoteService noteService;

    @Inject
    private INiveauDao niveauDao;

    @Inject
    private IOptionDao optionDao;

    @Inject
    private ICoursDao coursDao;

    @Inject
    private IAnneeAcademiqueDao academiqueDao;
    
    @Inject
    private IDepartementDao departementDao;

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

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }
    
    

    @Override
    public String produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, int session, OutputStream stream) throws ServiceException {
        try {
            Niveau niveau = niveauDao.findById(niveauId);

            Option option = optionDao.findById(optionId);
            
            System.out.println("\n\n une option *********\n\n "+option+"\n\n");
            
            Departement departement = optionDao.findDepartement(option);

            Cours cours = coursDao.findById(coursId);

            AnneeAcademique anne = academiqueDao.findById(academiqueId);

            Session s = Session.values()[session];

            List<EtudiantNotes> notes = noteService.getAllNotesEtudiants(niveau, option, cours, null, anne, s);

            PvHeader head = new PvHeader();
            head.setAnneeAcademique(anne.toString());
            //head.setCodeUe(cours.);
            head.setOption(option.getDescription());
            head.setParcours(niveau.getCode() + " "+ option.getCode());
            head.setDepartement(departement.getDescription());
            head.setCours(cours.getIntitule());
            head.setCredit(cours.getCredit());
            head.setEnseignants(null);
            head.setSession(session+1);
            head.setNiveau(niveau.getCode());
            String documentName = "pv" + head.getCours() + head.getNiveau() + head.getOption() + head.getDepartement() + head.getAnneeAcademique() + ".pdf";

            System.out.println("FILENAME ============ " + documentName);
            Document pv = docPv(notes, head, stream);
            
            return documentName;
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

    private Document docPv(List<EtudiantNotes> notes, PvHeader head, OutputStream stream) throws DocumentException, FileNotFoundException, BadElementException, IOException {

        Document doc = new Document();
        PdfWriter.getInstance(doc, stream);
        doc.open();
        doc.setPageSize(new Rectangle(400, 100));
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        int moySup15 = 0;
        int moySup10Inf15 = 0;
        int moyInf10 = 0;
        double maxMoyenne = 0;
        double minMoyenne = 0;

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
        Phrase french = new Phrase(francais, bf12);
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
        doc.add(new Phrase("\n"));

        // Fin de l'entete
        // Définition du formulaire d'entete
        PdfPTable table2 = new PdfPTable(6);
        table2.setWidthPercentage(100);
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
        cell = new PdfPCell(new Phrase("Parcours : " + head.getParcours(), bf12));
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
       

        // Fin du formulaire
        doc.add(new Paragraph("\n\n"));

        // Tableau de notes
        Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        float relativeWidths[] = {2, 4, 11, 5, 5, 5, 5, 3};
        Font bf13 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        PdfPTable table = new PdfPTable(relativeWidths);
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        //table.getDefaultCell().setFixedHeight(20);
        //  table.getDefaultCell().set
        System.out.println("J'ai ==============================" + notes.get(0).getNote().size());
        /* for (Map.Entry<String, Double> e : notes.get(0).getNote().entrySet()) {
         //     System.out.print(String.format("%s - %.2f\t", e.getKey(), e.getValue()));
         System.out.println("HELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOO");    
         System.out.println("J'ai trouvé un "+ e.getKey());
            
            
         table.addCell(new Phrase(String.format("%s/20 ", e.getKey()), bf));
         }*/

        table.addCell(new Phrase("No", bf));
        table.addCell(new Phrase("Matricule", bf));
        table.addCell(new Phrase("NOM(s) et PRENOM(s)", bf));
        for (Map.Entry<String, Double> e : notes.get(1).getNote().entrySet()) {
            //     System.out.print(String.format("%s - %.2f\t", e.getKey(), e.getValue()));
            System.out.println("HELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOO");
            System.out.println("J'ai trouvé un " + e.getKey());

            table.addCell(new Phrase(String.format("%s/20 ", e.getKey()), bf));
        }
      //  table.addCell(new Phrase("CC/20", bf));
        //  table.addCell(new Phrase("TPE/20", bf));
        //  table.addCell(new Phrase("EE/20", bf));
        table.addCell(new Phrase("Moy/20", bf));
        table.addCell(new Phrase("Grade", bf));
        doc.add(table);

        // float relativeWidths[] = {2,5,8,5,5,5,5,5};
        PdfPTable table3 = new PdfPTable(relativeWidths);
        table3.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        table3.getDefaultCell().setFixedHeight(20);
        maxMoyenne = minMoyenne = notes.get(0).getMoyenne();
        for (int i = 0; i < notes.size(); i++) {
            PdfPCell cellule = new PdfPCell();
            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellule = new PdfPCell(new Phrase(String.valueOf(i + 1), bf12));
            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cellule);
            table3.addCell(new Phrase(notes.get(i).getMatricule(), bf12));
            cellule = new PdfPCell(new Phrase(notes.get(i).getNom(), bf12));
            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cellule);
            for (Map.Entry<String, Double> e : notes.get(i).getNote().entrySet()) {
                // System.out.print(String.format("%s - %.2f\t", e.getKey(), e.getValue()));
                cellule = new PdfPCell(new Phrase(String.format("%.2f", e.getValue()), bf12));
                cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
                table3.addCell(cellule);
            }
         //   table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("CC")), bf13));
            // table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("TPE")), bf13));
            // table3.addCell(new Phrase(String.format("%.2f", notes.get(i).getNote().get("EE")), bf13));
            if(notes.get(i).getMoyenne() >= 15){
                moySup15 ++;
            }
            
            if((notes.get(i).getMoyenne()) < 15 && (notes.get(i).getMoyenne() >= 10)){
                moySup10Inf15 ++;
            }
            
            if(notes.get(i).getMoyenne() < 10){
                moyInf10 ++;
            }
            
            if(notes.get(i).getMoyenne() >= maxMoyenne){
                maxMoyenne = notes.get(i).getMoyenne();
            }
            
            if(notes.get(i).getMoyenne() < minMoyenne){
                minMoyenne = notes.get(i).getMoyenne();
            }
            cellule = new PdfPCell(new Phrase(String.format("%.2f", notes.get(i).getMoyenne()), bf12));
            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cellule);

            cellule = new PdfPCell(new Phrase(transformNoteGrade(notes.get(i).getMoyenne()), bf12));
            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
            //cellule.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(cellule);
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

        float widths1[] = {5, 3, 3};
        PdfPTable pourcentage = new PdfPTable(widths1);
        //pourcentage.setWidthPercentage(50);
        PdfPCell cell2;
        cell2 = new PdfPCell(new Phrase("Moyenne Comprise entre 15 et 20", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(moySup15)), bf12));
        pourcentage.addCell(new Phrase(String.format(String.valueOf(100*((moySup15*1.0)/notes.size()))), bf12));
        cell2 = new PdfPCell(new Phrase("Moyenne Comprise entre 14,99 et 10", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(moySup10Inf15), bf12)));
        pourcentage.addCell(new Phrase(String.format(String.valueOf(100*((moySup10Inf15*1.0)/notes.size()))), bf12));
        cell2 = new PdfPCell(new Phrase("Moyenne < 10", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(moyInf10), bf12)));
        pourcentage.addCell(new Phrase(String.format(String.valueOf(100*((moyInf10*1.0)/notes.size()))), bf12));
        cell2 = new PdfPCell(new Phrase("Effectif Total des Etudiants", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(notes.size())), bf12));
        PdfPCell cell3 = new PdfPCell();
        //cell2.setBorderColor(BaseColor.WHITE);
       /* cell3.setBorderColorLeft(BaseColor.BLACK);
        cell3.setBorderColorTop(BaseColor.BLACK);
        cell3.setBorderColorRight(BaseColor.WHITE);
        cell3.setBorderColorBottom(BaseColor.WHITE);*/
        pourcentage.addCell(cell3);
        cell2 = new PdfPCell(new Phrase("Plus Grande MOY (Max)", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(maxMoyenne)), bf12));
        cell3 = new PdfPCell();
        //cell2.setBorderColor(BaseColor.WHITE);
        /*cell3.setBorderColorTop(BaseColor.WHITE);
        cell3.setBorderColorLeft(BaseColor.BLACK);
        cell3.setBorderColorRight(BaseColor.WHITE);*/
        pourcentage.addCell(cell3);
        cell2 = new PdfPCell(new Phrase("Plus Petite MOY (Min)", bf));
        pourcentage.addCell(cell2);
        pourcentage.addCell(new Phrase(String.format(String.valueOf(minMoyenne)), bf12));
        cell3 = new PdfPCell();
        //cell2.setBorderColor(BaseColor.WHITE);
        /*cell3.setBorderColorTop(BaseColor.WHITE);
        cell3.setBorderColorLeft(BaseColor.WHITE);
        cell3.setBorderColorRight(BaseColor.WHITE);*/
        pourcentage.addCell(cell3);
        doc.add(pourcentage);

        doc.add(new Paragraph("\n"));
        //float widths1[] = {5, 3, 3};
        PdfPTable fin = new PdfPTable(widths1);
        //fin.setWidthPercentage(50);
        PdfPCell cell4;
        cell4 = new PdfPCell(new Phrase("Taux de Réussite >=10", bf));
        cell4.setColspan(2);
        fin.addCell(cell4);
        cell4 = new PdfPCell(new Phrase(String.format(String.valueOf(100 * ((moySup15+moySup10Inf15)*1.0/notes.size()))), bf12));
        //cell4.setColspan(2);
        fin.addCell(cell4);
        doc.add(fin);
        doc.add(new Phrase("\n"));

        doc.close();
        return doc;

    }

    private static String transformNoteGrade(double note) {
        if (note <= 20 && note >= 16) {
            return "A+";
        }
        if (note < 16 && note >= 15) {
            return "A";
        }
        if (note < 15 && note >= 14) {
            return "B+";
        }
        if (note < 14 && note >= 13) {
            return "B";
        }
        if (note < 13 && note >= 12) {
            return "B-";
        }
        if (note < 12 && note >= 11) {
            return "C+";
        }
        if (note < 11 && note >= 10) {
            return "C";
        }
        if (note < 10 && note >= 9) {
            return "C-";
        }
        if (note < 9 && note >= 8) {
            return "D+";
        }
        if (note < 8 && note >= 7) {
            return "D";
        }
        if (note < 7 && note >= 6) {
            return "E";
        }

        return "F";

    }

}
