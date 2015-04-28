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
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.projection.PvHeader;
import com.douwe.notes.projection.StatistiquesNote;
import com.douwe.notes.service.IDocumentService;
import com.douwe.notes.service.IEvaluationService;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.springframework.core.io.ClassPathResource;

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

    @Inject
    private IEvaluationService evaluationService;

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
    
    

    public IEvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Override
    public String produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, int session, OutputStream stream) throws ServiceException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.open();
            doc.setPageSize(PageSize.A4);
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
 //           Document pv = docPv(notes, head, stream);
            
   //         return documentName;
            //  head.s
            produceHeader(doc, cours, niveau, option, anne, s);
            StatistiquesNote stats = produceBody(doc, cours, niveau, option, anne, s, true);
            produceFooter(doc, stats);
            doc.newPage();
            produceHeader(doc, cours, niveau, option, anne, s);
            produceBody(doc, cours, niveau, option, anne, s, false);
            doc.close();
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    private void produceHeader(Document doc, Cours c, Niveau n, Option o, AnneeAcademique a, Session s) throws Exception {
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        // Définition de l'entete du document
        StringBuilder builder = new StringBuilder("République du Cameroun\n");
        builder.append("****\n");
        builder.append("Paix -- Travail -- Patrie\n");
        builder.append("****\n");
        builder.append("Ministère l'Enseignement Supérieur\n");
        builder.append("****\n");
        builder.append("Université de Maroua\n");
        builder.append("****\n");
        builder.append("Institut Supérieur du Sahel");
        Paragraph frecnch = new Paragraph(new Phrase(builder.toString(), bf12));
        frecnch.setAlignment(Element.ALIGN_CENTER);
        builder = new StringBuilder();
        builder.append("Republic of Cameroon\n");
        builder.append("****\n");
        builder.append("Peace -- Work -- Fatherland\n");
        builder.append("****\n");
        builder.append("Ministry of Higher Education\n");
        builder.append("****\n");
        builder.append("University of Maroua\n");
        builder.append("****\n");
        builder.append("The Higher Institute of the Sahel");
        Paragraph eng = new Paragraph(new Phrase(builder.toString(), bf12));
        eng.setAlignment(Element.ALIGN_CENTER);
        builder = new StringBuilder();
        builder.append("B.P. / P.O. Box: 46 Maroua\n");
        builder.append("Email: institutsupsahel.uma@gmail.com\n");
        builder.append("Site: http://www.uni-maroua.citi.cm");
        Paragraph coordonnees = new Paragraph(new Phrase(builder.toString(), bf12));
        coordonnees.setAlignment(Element.ALIGN_CENTER);
        float widths2[] = {3, 4, 3};
        PdfPTable header = new PdfPTable(widths2);
        header.setWidthPercentage(100);
        PdfPCell cell1;
        cell1 = new PdfPCell(frecnch);
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell1);
        URL url = new ClassPathResource("logo.png").getURL();
        Image logo = Image.getInstance(url);
        logo.scalePercent(65f);
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(new Paragraph(new Chunk(logo, 0, -15, true)));
        PdfPCell cel = new PdfPCell(p);
        cel.setBorderColor(BaseColor.WHITE);
        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cel.setVerticalAlignment(Element.ALIGN_CENTER);
        header.addCell(cel);
        cell1 = new PdfPCell(eng);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorderColor(BaseColor.WHITE);
        header.addCell(cell1);
        cell1 = new PdfPCell(coordonnees);
        cell1.setColspan(10);
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell1);
        doc.add(header);
        PdfPTable table2 = new PdfPTable(6);
        table2.setWidthPercentage(100);
        PdfPCell cell;
        Phrase phrase;
        phrase = new Phrase();
        phrase.add(new Chunk("Mention : ", fontEntete));
        phrase.add(new Chunk(o.getDepartement().getDescription(), bf12));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Titre de l'UE : ", fontEntete));
        phrase.add(new Chunk(c.getIntitule(), bf12));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Année Académique : ", fontEntete));
        phrase.add(new Chunk(a.toString(), bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(2);
        table2.addCell(cell);

    //    cell = new PdfPCell(new Phrase("Parcours : " + head.getParcours(), bf12));

        phrase = new Phrase();
        phrase.add(new Chunk("Parcours : ", fontEntete));
        phrase.add(new Chunk(o.getDepartement().getCode(), bf12));
        cell = new PdfPCell(phrase);

        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        // Il faut retrouver le code de l'UE
        phrase = new Phrase();
        phrase.add(new Chunk("Code de l'UE : ", fontEntete));
        phrase.add(new Chunk("ITEL 115", bf12));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Option : ", fontEntete));
        phrase.add(new Chunk(o.getCode(), bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Credit : ", fontEntete));
        phrase.add(new Chunk("" + c.getCredit(), bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Niveau : ", fontEntete));
        phrase.add(new Chunk(n.getCode(), bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(2);
        table2.addCell(cell);

        // I faut retrouver le semestre du cours
        phrase = new Phrase();
        phrase.add(new Chunk("Semestre : ", fontEntete));
        phrase.add(new Chunk("" + 1, bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        phrase = new Phrase();
        phrase.add(new Chunk("Session : ", fontEntete));
        phrase.add(new Chunk("" + (s.ordinal() + 1), bf12));
        cell = new PdfPCell(phrase);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        // il faut retrouver les enseignants du cours
        phrase = new Phrase();
        phrase.add(new Chunk("Enseignant(s) : ", fontEntete));
        phrase.add(new Chunk("Douwe Vincent", bf12));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        cell.setBorderColor(BaseColor.WHITE);
        table2.addCell(cell);
        table2.setSpacingBefore(2f);
        doc.add(table2);

    }

    private StatistiquesNote produceBody(Document doc, Cours c, Niveau n, Option o, AnneeAcademique a, Session s, boolean avecNoms) throws Exception {
        StatistiquesNote result = new StatistiquesNote();
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        int sup15 = 0;
        int inf10 = 0;
        int entre1014 = 0;
        Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        List<Evaluation> evals = evaluationService.getAllEvaluationByCours(c.getId());
        int taille = evals.size();
        float relativeWidths[];
        if (avecNoms) {
            relativeWidths = new float[5 + taille];
            relativeWidths[0] = 1;
            relativeWidths[1] = 3;
            relativeWidths[2] = 10;
            for (int i = 0; i < taille + 2; i++) {
                relativeWidths[3 + i] = 2;
            }
        }else{
            relativeWidths = new float[4 + taille];
            relativeWidths[0] = 1;
            relativeWidths[1] = 3;
            for (int i = 0; i < taille + 2; i++) {
                relativeWidths[2 + i] = 2;
            }
        }
        PdfPTable table = new PdfPTable(relativeWidths);

        table.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table.addCell(createDefaultHeaderCell("No", bf));
        table.addCell(createDefaultHeaderCell("Matricule", bf));
        if(avecNoms)
            table.addCell(createDefaultHeaderCell("NOM(s) et PRENOM(s)", bf));

        for (Evaluation eval : evals) {
            table.addCell(createDefaultHeaderCell(String.format("%s / 20 ", eval.getCode()), bf));
        }
        table.addCell(createDefaultHeaderCell("Moy / 20", bf));
        table.addCell(createDefaultHeaderCell("Grade", bf));
        table.setSpacingBefore(10f);
        if(avecNoms)
            table.setWidthPercentage(98);
        else
            table.setWidthPercentage(60);
        List<EtudiantNotes> notes = noteService.getAllNotesEtudiants(n, o, c, null, a, s);
        int i = 0;
        for (EtudiantNotes note : notes) {
            table.addCell(createDefaultBodyCell(String.valueOf(++i), bf12));
            table.addCell(createDefaultBodyCell(note.getMatricule(), bf12));
            if(avecNoms){
                PdfPCell cell = createDefaultBodyCell(note.getNom().toUpperCase(), bf12);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }
            for (Evaluation eval : evals) {
                Double value = note.getNote().get(eval.getCode());
                table.addCell(createDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf12));

            }
            double moyenne = note.getMoyenne();
            table.addCell(createDefaultBodyCell(String.format("%.2f", moyenne), bf12));
            table.addCell(createDefaultBodyCell(transformNoteGrade(moyenne), bf12));
            if(moyenne > max)
                max = moyenne;
            if(moyenne < min)
                min = moyenne;
            if(moyenne >= 15)
                sup15++;
            else if(moyenne >= 10)
                entre1014++;
            else
                inf10++;
        }

//            System.out.println("");
//            if (n.getMoyenne() >= 15) {
//                moySup15++;
//            }
//
//            if ((n.getMoyenne()) < 15 && (n.getMoyenne() >= 10)) {
//                moySup10Inf15++;
//            }
//
//            if (n.getMoyenne() < 10) {
//                moyInf10++;
//            }
//
//            if (n.getMoyenne() >= maxMoyenne) {
//                maxMoyenne = n.getMoyenne();
//            }
//
//            if (n.getMoyenne() < minMoyenne) {
//                minMoyenne = n.getMoyenne();
//            }
//            cellule = new PdfPCell(new Phrase(String.format("%.2f", n.getMoyenne()), bf12));
//            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
//            table3.addCell(cellule);
//
//            cellule = new PdfPCell(new Phrase(transformNoteGrade(n.getMoyenne()), bf12));
//            cellule.setVerticalAlignment(Element.ALIGN_CENTER);
//            table3.addCell(cellule);
//        }
//        doc.add(table3);
//        doc.add(new Phrase("\n"));
        doc.add(table);
        table = new PdfPTable(3);
        table.setWidthPercentage(95);
        PdfPCell cell = createDefaultHeaderCell("Président", bf);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.WHITE);
        table.addCell(cell);
        cell = createDefaultHeaderCell("Vice-Président", bf);
        cell.setBorder(0);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        cell = createDefaultHeaderCell("Membre(s)", bf);
        cell.setBorder(0);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        table.setSpacingBefore(15f);
        table.setSpacingAfter(25f);

        doc.add(table);
        result.setEffectif(notes.size());
        result.setNombreMoyenneEntre10et15(entre1014);
        result.setNombreMoyenneInferieurA10(inf10);
        result.setNombreMoyenneSuperieureQuinze(sup15);
        result.setPlusGrandeMoyenne(max);
        result.setPlusPetiteMoyenne(min);
        return result;
    }

    private PdfPCell createDefaultHeaderCell(String message, Font bf) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    private PdfPCell createDefaultBodyCell(String message, Font bf) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    private void produceFooter(Document doc, StatistiquesNote stats) throws Exception {
        Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        float widths[] = {5, 3, 3};
        PdfPTable pourcentage = new PdfPTable(widths);
        pourcentage.getDefaultCell().setBorder(0);
        pourcentage.getDefaultCell().setPaddingBottom(5f);
        pourcentage.getDefaultCell().setPaddingTop(5f);
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase("Pourcentage(%)",bf));
        pourcentage.addCell(new Phrase("Moyenne Comprise entre 15 et 20", bf));
        pourcentage.addCell(new Phrase(String.valueOf(stats.getNombreMoyenneSuperieureQuinze()), bf));
        pourcentage.addCell(new Phrase(String.format("%.2f",100 * ((stats.getNombreMoyenneSuperieureQuinze() * 1.0) / stats.getEffectif())), bf));
        pourcentage.addCell(new Phrase("Moyenne Comprise entre 14,99 et 10", bf));
        pourcentage.addCell(new Phrase(String.valueOf(stats.getNombreMoyenneEntre10et15()), bf));
        pourcentage.addCell(new Phrase(String.format("%.2f", 100 * ((stats.getNombreMoyenneEntre10et15() * 1.0) / stats.getEffectif())), bf));
        pourcentage.addCell(new Phrase("Moyenne < 10", bf));
        pourcentage.addCell(new Phrase(String.valueOf(stats.getNombreMoyenneInferieurA10()), bf));
        pourcentage.addCell(new Phrase(String.format("%.2f", 100 * ((stats.getNombreMoyenneInferieurA10() * 1.0) / stats.getEffectif())), bf));
        pourcentage.addCell(new Phrase("Effectif Total des Etudiants", bf));
        pourcentage.addCell(new Phrase(String.format(String.valueOf(stats.getEffectif())), bf));
        pourcentage.addCell("");
        pourcentage.addCell(new Phrase("Plus Grande MOY (Max)", bf));
        pourcentage.addCell(new Phrase(String.format("%.2f",stats.getPlusGrandeMoyenne()), bf));
        pourcentage.addCell("");
        pourcentage.addCell(new Phrase("Plus Petite MOY (Min)", bf));
        pourcentage.addCell(new Phrase(String.format("%.2f",stats.getPlusPetiteMoyenne()), bf));
        pourcentage.addCell("");
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase("Taux de Réussite >=10", bf));
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase(String.format("%.2f",100 * ((stats.getNombreMoyenneEntre10et15() + stats.getNombreMoyenneSuperieureQuinze()) * 1.0 / stats.getEffectif())), bf));
        pourcentage.setSpacingBefore(15f);
        doc.add(pourcentage);
    }

}
