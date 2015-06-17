package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Session;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.projection.MoyenneUniteEnseignement;
import com.douwe.notes.projection.StatistiquesNote;
import com.douwe.notes.projection.UEnseignementCredit;
import com.douwe.notes.service.IDocumentService;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    private IEnseignantDao enseignantDao;

    @Inject
    private IEtudiantDao etudiantDao;

    @Inject
    private IOptionDao optionDao;

    @Inject
    private ICoursDao coursDao;

    @Inject
    private IAnneeAcademiqueDao academiqueDao;

    @Inject
    private IDepartementDao departementDao;

    // TODO Royken I faut eviter les dependances vers les services
    @Inject
    private IEvaluationDao EvaluationDao;

    @Inject
    private IProgrammeDao programmeDao;

    @Inject
    private IParcoursDao parcoursDao;

    @Inject
    private ISemestreDao semestreDao;

    @Inject
    private IUniteEnseignementDao uniteEnsDao;

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }

//    @Inject
//    private IUniteEnseignementDao uniteDao;
//    
//    @Inject
//    private ISemestreDao semestreDao;
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

    public IEnseignantDao getEnseignantDao() {
        return enseignantDao;
    }

    public void setEnseignantDao(IEnseignantDao enseignantDao) {
        this.enseignantDao = enseignantDao;
    }

    public IProgrammeDao getProgrammeDao() {
        return programmeDao;
    }

    public void setProgrammeDao(IProgrammeDao programmeDao) {
        this.programmeDao = programmeDao;
    }

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
    }

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }

    public IEvaluationDao getEvaluationDao() {
        return EvaluationDao;
    }

    public void setEvaluationDao(IEvaluationDao EvaluationDao) {
        this.EvaluationDao = EvaluationDao;
    }

    public IUniteEnseignementDao getUniteEnsDao() {
        return uniteEnsDao;
    }

    public void setUniteEnsDao(IUniteEnseignementDao uniteEnsDao) {
        this.uniteEnsDao = uniteEnsDao;
    }

    @Override
    public String produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, int session, OutputStream stream) throws ServiceException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4);
            doc.open();

            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            Cours cours = coursDao.findById(coursId);
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            Session s = Session.values()[session];
            Programme prog = programmeDao.findByCours(cours, niveau, option, anne);
            produceHeader(doc, cours, niveau, option, anne, s, prog, false);
            StatistiquesNote stats = produceBody(doc, cours, niveau, option, anne, s, true);
            produceFooter(doc, stats);
            doc.newPage();
            produceHeader(doc, cours, niveau, option, anne, s, prog, false);
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

    private void produceHeader(Document doc, Cours c, Niveau n, Option o, AnneeAcademique a, Session s, Programme prog, boolean departement) throws Exception {
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
        if (departement) {
            builder.append("\n****\n");
            builder.append(o.getDepartement().getFrenchDescription());
        }
        Paragraph frecnch = new Paragraph(new Phrase(builder.toString(), bf12));
        frecnch.setAlignment(Element.ALIGN_CENTER);
        builder = new StringBuilder();
        builder.append("Republic of Cameroon\n");
        builder.append("****\n");
        builder.append("Peace -- Work -- Fatherland\n");
        builder.append("****\n");
        builder.append("The Ministry of Higher Education\n");
        builder.append("****\n");
        builder.append("The University of Maroua\n");
        builder.append("****\n");
        builder.append("The Higher Institute of the Sahel");
        if (departement) {
            builder.append("\n****\n");
            builder.append(o.getDepartement().getEnglishDescription());
        }
        Paragraph eng = new Paragraph(new Phrase(builder.toString(), bf12));
        eng.setAlignment(Element.ALIGN_CENTER);
        builder = new StringBuilder();
        builder.append("B.P. / P.O. Box: 46 Maroua\n");
        if (departement) {
            builder.append("Tel : (+237) 22 62 03 76/ (+237) 22 62 08 90\n");
            builder.append("Fax : (+237) 22 29 31 12 / (+237) 22 29 15 41\n");
        }
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
        URL url = new ClassPathResource("logo4.png").getURL();
        //java.awt.Image img = ImageIO.read(new File("logo4.png"));
        //Image logo = Image.getInstance(img, null);
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
        /////////////////////////////
        if (!departement) {
            PdfPTable table2 = new PdfPTable(7);
            table2.setWidthPercentage(100);
            PdfPCell cell;
            Phrase phrase;
            phrase = new Phrase();
            phrase.add(new Chunk("Mention : ", fontEntete));
            phrase.add(new Chunk(o.getDepartement().getFrenchDescription(), bf12));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorderColor(BaseColor.WHITE);
            table2.addCell(cell);
            phrase = new Phrase();
            phrase.add(new Chunk("Titre de l'UE : ", fontEntete));
            phrase.add(new Chunk(c.getIntitule(), bf12));
            cell = new PdfPCell(phrase);
            cell.setColspan(4);
            cell.setBorderColor(BaseColor.WHITE);
            table2.addCell(cell);

            //    cell = new PdfPCell(new Phrase("Parcours : " + head.getParcours(), bf12));
            phrase = new Phrase();
            phrase.add(new Chunk("Parcours : ", fontEntete));
            phrase.add(new Chunk(o.getDepartement().getCode(), bf12));
            cell = new PdfPCell(phrase);

            cell.setColspan(3);
            cell.setBorderColor(BaseColor.WHITE);
            table2.addCell(cell);
            // Il faut retrouver le code de l'UE
            phrase = new Phrase();
            phrase.add(new Chunk("Code de l'UE : ", fontEntete));
            phrase.add(new Chunk(prog.getUniteEnseignement().getCode(), bf12));
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
            phrase.add(new Chunk("  ", bf12));
            phrase.add(new Chunk("Année Académique : ", fontEntete));
            phrase.add(new Chunk(a.toString(), bf12));
            cell = new PdfPCell(phrase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setColspan(3);
            table2.addCell(cell);
            // I faut retrouver le semestre du cours
            phrase = new Phrase();
            phrase.add(new Chunk("Semestre : ", fontEntete));
            phrase.add(new Chunk(prog.getSemestre().getIntitule(), bf12));
            phrase.add(new Chunk("  ", fontEntete));
            phrase.add(new Chunk("Session : ", fontEntete));
            phrase.add(new Chunk("" + (s.ordinal() + 1), bf12));
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            table2.addCell(cell);

            // il faut retrouver les enseignants du cours
            Parcours pa = parcoursDao.findByNiveauOption(n, o);
            List<Enseignant> enseignants = enseignantDao.findByCours(c, a, pa);
            String ens = "";
            for (Enseignant enseignant : enseignants) {
                ens += enseignant.getNom() + " / ";
            }
            if (ens.endsWith(" / ")) {
                ens = ens.substring(0, ens.length() - 3);
            }
            phrase = new Phrase();
            phrase.add(new Chunk("Enseignant(s) : ", fontEntete));
            phrase.add(new Chunk(ens, bf12));
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            table2.addCell(cell);
            table2.setSpacingBefore(2f);
            doc.add(table2);

        }

    }

    // TODO I need to change something here because this method is called twice and every times it needs to load the data from the database
    // Can we load the data only once ?

    private StatistiquesNote produceBody(Document doc, Cours c, Niveau n, Option o, AnneeAcademique a, Session s, boolean avecNoms) throws Exception {
        StatistiquesNote result = new StatistiquesNote();
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        int sup15 = 0;
        int inf10 = 0;
        int entre1014 = 0;
        Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        List<Evaluation> evals = EvaluationDao.evaluationForCourses(c);
        // List<Evaluation> evals = evaluationService.getAllEvaluationByCours(c.getId());
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
        } else {
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
        if (avecNoms) {
            table.addCell(createDefaultHeaderCell("NOM(s) et PRENOM(s)", bf));
        }

        for (Evaluation eval : evals) {
            table.addCell(createDefaultHeaderCell(String.format("%s / 20 ", eval.getCode()), bf));
        }
        table.addCell(createDefaultHeaderCell("Moy / 20", bf));
        table.addCell(createDefaultHeaderCell("Grade", bf));
        table.setSpacingBefore(10f);
        List<EtudiantNotes> notes = noteService.getAllNotesEtudiants(n, o, c, null, a, s);
        if (avecNoms) {
            table.setWidthPercentage(98);
        } else {
            table.setWidthPercentage(60);
            // Maybe I need to do this on the database
            Collections.sort(notes, new Comparator<EtudiantNotes>() {

                @Override
                public int compare(EtudiantNotes t, EtudiantNotes t1) {
                    return t.getMatricule().compareToIgnoreCase(t1.getMatricule());
                }

            });
        }

        int i = 0;
        for (EtudiantNotes note : notes) {
            table.addCell(createDefaultBodyCell(String.valueOf(++i), bf12, false));
            table.addCell(createDefaultBodyCell(note.getMatricule(), bf12, false));
            if (avecNoms) {
                PdfPCell cell = createDefaultBodyCell(note.getNom().toUpperCase(), bf12, false);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }
            for (Evaluation eval : evals) {
                Double value = note.getNote().get(eval.getCode());
                table.addCell(createDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf12, false));

            }
            double moyenne = note.getMoyenne();
            table.addCell(createDefaultBodyCell(String.format("%.2f", moyenne), bf12, false));
            table.addCell(createDefaultBodyCell(transformNoteGrade(moyenne), bf12, false));
            if (moyenne > max) {
                max = moyenne;
            }
            if (moyenne < min) {
                min = moyenne;
            }
            if (moyenne >= 15) {
                sup15++;
            } else if (moyenne >= 10) {
                entre1014++;
            } else {
                inf10++;
            }
        }
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
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    private PdfPCell createSyntheseDefaultHeaderCell(String message, Font bf, boolean color) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(new BaseColor(230, 230, 230));
        }
        // cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);

        cell.setBorderColor(BaseColor.BLACK);

        cell.setRotation(90);
        return cell;
    }

    private PdfPCell createDefaultBodyCell(String message, Font bf, boolean color) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    private PdfPCell createSyntheseDefaultBodyCell(String message, Font bf, boolean color, boolean isCentered) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(new BaseColor(230, 230, 230));
        }
        if (isCentered) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        } else {
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(4f);
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
        pourcentage.addCell(new Phrase("Pourcentage(%)", bf));
        pourcentage.addCell(new Phrase("Moyenne Comprise entre 15 et 20", bf));
        pourcentage.addCell(new Phrase(String.valueOf(stats.getNombreMoyenneSuperieureQuinze()), bf));
        pourcentage.addCell(new Phrase(String.format("%.2f", 100 * ((stats.getNombreMoyenneSuperieureQuinze() * 1.0) / stats.getEffectif())), bf));
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
        pourcentage.addCell(new Phrase(String.format("%.2f", stats.getPlusGrandeMoyenne()), bf));
        pourcentage.addCell("");
        pourcentage.addCell(new Phrase("Plus Petite MOY (Min)", bf));
        pourcentage.addCell(new Phrase(String.format("%.2f", stats.getPlusPetiteMoyenne()), bf));
        pourcentage.addCell("");
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase("Taux de Réussite >=10", bf));
        pourcentage.addCell(new Phrase());
        pourcentage.addCell(new Phrase(String.format("%.2f", 100 * ((stats.getNombreMoyenneEntre10et15() + stats.getNombreMoyenneSuperieureQuinze()) * 1.0 / stats.getEffectif())), bf));
        pourcentage.setSpacingBefore(15f);
        doc.add(pourcentage);
    }

    @Override
    public String produireSynthese(Long niveauId, Long optionId, Long academiqueId, Long semestreId, OutputStream stream) throws ServiceException {
        if (semestreId == null) {
            produireSyntheseAnnuelle(stream, niveauId, optionId, academiqueId);
        } else {
            produireSyntheseSemestrielle(niveauId, optionId, academiqueId, semestreId, stream);
        }
        return null;
    }

    private String produireSyntheseSemestrielle(Long niveauId, Long optionId, Long academiqueId, Long semestreId, OutputStream stream) {
        Document doc = null;
        try {
            doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            Semestre semestre = semestreDao.findById(semestreId);
            produceHeader(doc, null, niveau, option, anne, null, null, true);
            produceSyntheseSemestrielleBody(doc, niveau, option, semestre, anne);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (doc != null) {
            doc.close();
        }
        return null;
    }

    private String produireSyntheseAnnuelle(OutputStream stream, Long niveauId, Long optionId, Long academiqueId) throws ServiceException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();

            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            produceHeader(doc, null, niveau, option, anne, null, null, true);
            produireSyntheseAnnueleBody(doc, niveau, option, anne);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void produceSyntheseSemestrielleBody(Document doc, Niveau n, Option o, Semestre s, AnneeAcademique a) {
        try {
            doc.add(new Phrase("\n"));
            List<UEnseignementCredit> ues = uniteEnsDao.findByNiveauOptionSemestre(n, o, s, a);
            List<Etudiant> etudiants = etudiantDao.listeEtudiantAvecNotes(a, n, o, s);
            Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            int taille = ues.size();
            float relativeWidths[];
            relativeWidths = new float[6 + taille];
            relativeWidths[0] = 1;
            relativeWidths[1] = 10;
            relativeWidths[2] = 3;
            for (int i = 0; i < taille + 3; i++) {
                relativeWidths[3 + i] = 2;
            }
            PdfPTable table = new PdfPTable(relativeWidths);
            table.setWidthPercentage(100);
            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));
            // Entete de synthese
            PdfPCell cell = new PdfPCell(new Phrase(s.getIntitule(), bf));
            cell.setColspan(taille + 5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            table.getDefaultCell().setBorderColor(BaseColor.BLACK);
            table.addCell(createSyntheseDefaultHeaderCell("No", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Noms et Prénoms", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Matricule", bf, false));
            for (UEnseignementCredit ue : ues) {
                table.addCell(createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
            }
            table.addCell(createSyntheseDefaultHeaderCell("Moyenne " + s.getIntitule(), bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("Crédits S3 validés", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("% crédits s3 validés", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));
            for (UEnseignementCredit ue : ues) {
                table.addCell(createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
            }
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, true));

            int i = 1;
            for (Etudiant etudiant : etudiants) {

                int nombreCredit = 0;
                double sumMoyenne = 0.0; //sumMoyenne /30 renvoie à la moyenne trimestrielle
                int nbrCreditValide = 0; // le nombre de crédits validés
                Map<String, MoyenneUniteEnseignement> notes = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), n.getId(), o.getId(), s.getId(), a.getId());
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(i++), bf1, false, true));
                table.addCell(createSyntheseDefaultBodyCell(etudiant.getNom(), bf1, false, false));
                table.addCell(createSyntheseDefaultBodyCell(etudiant.getMatricule(), bf1, false, true));
                for (UEnseignementCredit ue : ues) {
                    MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                    Double value = mue.getMoyenne();
                    sumMoyenne += value * ue.getCredit();
                    nombreCredit += ue.getCredit();
                    if (value >= 10) {
                        nbrCreditValide += ue.getCredit();
                    }
                    table.addCell(createSyntheseDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf1, false, true));
                }
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", Math.ceil(sumMoyenne * 100 / nombreCredit) / 100), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide * 1.0 / nombreCredit)) * 100)), bf1, true, true));
            }
            try {
                doc.add(table);
            } catch (DocumentException ex) {
                Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produireSyntheseAnnueleBody(Document doc, Niveau n, Option o, AnneeAcademique a) {
        try {
            doc.add(new Phrase("\n"));

            List<Semestre> semestres = semestreDao.findByNiveau(n);

            //Liste des ues du semestre 1
            List<UEnseignementCredit> ues1 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(0), a);

            for (UEnseignementCredit ues11 : ues1) {
                System.out.println(ues11);
            }

            //Liste des ues du semestre 2
            List<UEnseignementCredit> ues2 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(1), a);

            for (UEnseignementCredit ues21 : ues2) {
                System.out.println(ues21);
            }

            // La liste des étudiants du parcours
            Parcours p = parcoursDao.findByNiveauOption(n, o);

            List<Etudiant> etudiants = etudiantDao.listeEtudiantParDepartementEtParcours(o.getDepartement(), a, p);
            Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
            int taille1 = ues1.size();
            int taille2 = ues2.size();
            float relativeWidths[];
            relativeWidths = new float[11 + taille1 + taille2];
            relativeWidths[0] = 1;
            relativeWidths[1] = 10;
            relativeWidths[2] = 3;
            for (int i = 0; i < taille1 + taille2 + 8; i++) {
                relativeWidths[3 + i] = 2;
            }
            PdfPTable table = new PdfPTable(relativeWidths);
            table.setWidthPercentage(100);

            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));
            PdfPCell cell = new PdfPCell(new Phrase("Synthese I", bf));
            cell.setColspan(taille1 + 3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            PdfPCell cell1 = new PdfPCell(new Phrase("Synthese II", bf));
            cell1.setColspan(taille2 + 3);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1);
            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("", bf, false));

            table.getDefaultCell().setBorderColor(BaseColor.BLACK);
            table.addCell(createSyntheseDefaultHeaderCell("No", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Noms et Prénoms", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Matricule", bf, false));
            for (UEnseignementCredit ue : ues1) {
                table.addCell(createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
            }
            table.addCell(createSyntheseDefaultHeaderCell("Moyenne I", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("Crédits S3 validés", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("% crédits s3 validés", bf, true));
            for (UEnseignementCredit ue : ues2) {
                table.addCell(createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
            }

            table.addCell(createSyntheseDefaultHeaderCell("Moyenne II", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("Crédits S4 validés", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("% crédits s4 validés", bf, true));
            table.addCell(createSyntheseDefaultHeaderCell("Moyenne annuelle", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("%annuel de crédit validés", bf, false));
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            for (UEnseignementCredit ue : ues1) {
                table.addCell(createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
            }
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("30", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            for (UEnseignementCredit ue : ues2) {
                table.addCell(createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
            }
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("30", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            table.addCell(createDefaultBodyCell("", bf, true));
            //   table.addCell(createDefaultBodyCell("", bf, true));

            int i = 1;
            for (Etudiant etudiant : etudiants) {

                int nombreCredit1 = 0;  // Nombre de crédit du premier semestre
                double sumMoyenne1 = 0.0; // (sumMoyenne /nombreCredit1) renvoie à la moyenne trimestrielle
                int nbrCreditValide1 = 0; // le nombre de  crédits validés
                int nbrCreditValide2 = 0;
                double sumMoyenne2 = 0;
                Map<String, MoyenneUniteEnseignement> notes = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), n.getId(), o.getId(), semestres.get(0).getId(), a.getId());
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(i++), bf1, false, true));
                table.addCell(createSyntheseDefaultBodyCell(etudiant.getNom(), bf1, false, true));
                table.addCell(createSyntheseDefaultBodyCell(etudiant.getMatricule(), bf1, false, true));
                for (UEnseignementCredit ue : ues1) {
                    // Double value = enue.getNote().get(ue.getCodeUE());
                    //Double value = notes.get(ue)
                    MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                    Double value = mue.getMoyenne();
                    sumMoyenne1 += value * ue.getCredit();
                    nombreCredit1 += ue.getCredit();
                    if (value >= 10) {
                        nbrCreditValide1 += ue.getCredit();
                    }
                    table.addCell(createSyntheseDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf1, false, true));
                }
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", Math.ceil(sumMoyenne1 * 100 / nombreCredit1) / 100), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide1), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide1 * 1.0 / nombreCredit1)) * 100)), bf1, true, true));

                // Le second semestre
                Map<String, MoyenneUniteEnseignement> notes2 = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), n.getId(), o.getId(), semestres.get(1).getId(), a.getId());
                for (UEnseignementCredit ue : ues2) {
                    MoyenneUniteEnseignement mue = notes2.get(ue.getCodeUE());
                    double value = 0;
                    if (mue != null) {
                        value = mue.getMoyenne();
                        sumMoyenne2 += value * ue.getCredit();
                        if (value >= 10) {
                            nbrCreditValide2 += ue.getCredit();
                        }
                    }
                    table.addCell(createSyntheseDefaultBodyCell((value == 0) ? "" : String.format("%.2f", value), bf1, false, true));
                }
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", (sumMoyenne2 / 30)), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide2), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide2 * 1.0 / 30)) * 100)), bf1, true, true));

                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", ((sumMoyenne1 + sumMoyenne2) / 60)), bf1, true, true));
                // table.addCell(createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide), bf1, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", ((((nbrCreditValide1 + nbrCreditValide2) * 1.0 / 60)) * 100)), bf1, true, true));
            }
            doc.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void produceRelevetForm(Document doc,Niveau n, Option o, Etudiant e){
        
    }
    
    public void produceRelevetTable(Document doc,Etudiant e, Niveau n, Option o , AnneeAcademique a){
        try {
            List<Semestre> semestres = semestreDao.findByNiveau(n);
            
            //Liste des ues du semestre 1
            List<UEnseignementCredit> ues1 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(0), a);
            List<UEnseignementCredit> ues2 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(0), a);
            Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
            int taille1 = ues1.size();
            int taille2 = ues2.size();
            float relativeWidths[];
            relativeWidths = new float[8];
            relativeWidths[0] = 3;
            relativeWidths[1] = 10;
            relativeWidths[2] = 3;
            for (int i = 0; i <  8; i++) {
                relativeWidths[3 + i] = 3;
            }
            PdfPTable table = new PdfPTable(relativeWidths);
            table.setWidthPercentage(100);
            table.addCell(createSyntheseDefaultHeaderCell("Code UE", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Intitulé de l'Unité d'Enseignement", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Crédit", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Moyenne/20", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Moy/Grade", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Grade", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Semestre", bf, false));
            table.addCell(createSyntheseDefaultHeaderCell("Session", bf, false));
         
            
            Map<String, MoyenneUniteEnseignement> notes = noteService.listeNoteUniteEnseignement(e.getMatricule(), n.getId(), o.getId(), semestres.get(0).getId(), a.getId());
           /* table.addCell(createSyntheseDefaultBodyCell(String.valueOf(i++), bf1, false, true));
            table.addCell(createSyntheseDefaultBodyCell(e.getNom(), bf1, false, false));
            table.addCell(createSyntheseDefaultBodyCell(e.getMatricule(), bf1, false, true));
           */
            for (UEnseignementCredit ue : ues1) {
                MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                Double value = mue.getMoyenne();
                table.addCell(createSyntheseDefaultBodyCell(ue.getCodeUE(), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(ue.getIntituleUE(), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(ue.getCredit()), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", value), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("3,4", bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(transformNoteGrade(value), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("I(2023)", bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("II", bf1, true, true));
                
            }
            for (UEnseignementCredit ue : ues2) {
                MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                Double value = mue.getMoyenne();
                table.addCell(createSyntheseDefaultBodyCell(ue.getCodeUE(), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(ue.getIntituleUE(), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.valueOf(ue.getCredit()), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(String.format("%.2f", value), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("3,4", bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell(transformNoteGrade(value), bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("I(2023)", bf1, true, true));
                table.addCell(createSyntheseDefaultBodyCell("II", bf1, true, true));
            }
            
            doc.add(table);
            
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void produireRelevet(Long niveauId, Long optionId, Long anneeId, OutputStream stream) {

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4);
            doc.open();
            Niveau n = niveauDao.findById(niveauId);
            Option o = optionDao.findById(optionId);
            AnneeAcademique a = academiqueDao.findById(anneeId);
            produceHeader(doc, null, n, o, a, null, null, true);
            StringBuilder str = new StringBuilder();
            str.append("RELEVE DE NOTES / ACADEMIC TRANSCRIPT             ");
            str.append("N°");
            str.append("/_______/");
            str.append("INFOTEL/");
            str.append("DAACR/");
            str.append("DISS\n");
            
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
