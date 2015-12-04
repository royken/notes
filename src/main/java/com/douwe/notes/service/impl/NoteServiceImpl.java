package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.CoursCredit;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.projection.MoyenneUniteEnseignement;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import com.douwe.notes.service.util.DeliberationItem;
import com.douwe.notes.service.util.ImportationError;
import com.douwe.notes.service.util.ImportationResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class NoteServiceImpl implements INoteService {

    @Inject
    private INoteDao noteDao;

    @Inject
    private IEvaluationDao evaluationDao;

    @Inject
    private IEtudiantDao etudiantDao;

    @Inject
    private IEvaluationDetailsDao evaluationDetailsDao;

    @Inject
    private ICoursDao coursDao;

    @Inject
    private IAnneeAcademiqueDao academiqueDao;

    @Inject
    private IUniteEnseignementDao uniteEnseignementDao;

    @Inject
    private INiveauDao niveauDao;

    @Inject
    private ISemestreDao semestreDao;

    @Inject
    private IOptionDao optionDao;

    public INoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(INoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public IEvaluationDao getEvaluationDao() {
        return evaluationDao;
    }

    public void setEvaluationDao(IEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }

    public IEvaluationDetailsDao getEvaluationDetailsDao() {
        return evaluationDetailsDao;
    }

    public void setEvaluationDetailsDao(IEvaluationDetailsDao evaluationDetailsDao) {
        this.evaluationDetailsDao = evaluationDetailsDao;
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

    public IUniteEnseignementDao getUniteEnseignementDao() {
        return uniteEnseignementDao;
    }

    public void setUniteEnseignementDao(IUniteEnseignementDao uniteEnseignementDao) {
        this.uniteEnseignementDao = uniteEnseignementDao;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    public Note saveOrUpdateNote(Note note) throws ServiceException {
        try {
            if (note.getId() == null) {
                note.setActive(1);
                return noteDao.create(note);
            } else {
                return noteDao.update(note);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }

    }

    @Override
    public void deleteNote(Long id) throws ServiceException {
        try {
            Note note = noteDao.findById(id);
            if (note != null) {
                noteDao.delete(note);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Note findNoteById(long id) throws ServiceException {
        try {
            return noteDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Note> getAllNotes() throws ServiceException {
        try {
            return noteDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<EtudiantNotes> getAllNotesEtudiants(Niveau niveau, Option option, Cours cours, UniteEnseignement ue, AnneeAcademique academique, Session session) throws ServiceException {

        //boolean state = false;
        try {
            return listeNoteEtudiant(cours, academique, niveau, option, session);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(400);
        }

    }

    private List<EtudiantNotes> listeNoteEtudiant(Cours cours, AnneeAcademique academique, Niveau niveau, Option option, Session session) throws DataAccessException {
        List<EtudiantNotes> result = new ArrayList<EtudiantNotes>();
        Map<String, Integer> calc = getEvaluationDetails(cours);

        // recuperer les listes des  étudiants du parcours
        //List<Etudiant> etudiants = etudiantDao.listeEtudiantParDepartementEtNiveau(null, academique, niveau, option);
        List<Etudiant> etudiants = etudiantDao.listeEtudiantAvecNotes(academique, niveau, option, cours, session);
        System.out.println("Bravo j'ai trouve " + etudiants.size() + " etudiants");
        for (Etudiant etudiant : etudiants) {
            EtudiantNotes et = new EtudiantNotes();
            et.setMatricule(etudiant.getMatricule());
            et.setNom(etudiant.getNom());
            Map<String, Double> notes = new HashMap<String, Double>();
            List<Note> nn = noteDao.listeNoteCours(etudiant, cours, academique, session);
            for (Note nn1 : nn) {
                notes.put(nn1.getEvaluation().getCode(), nn1.getValeur());
            }
            et.setNote(notes);
            et.setDetails(calc);
            result.add(et);
        }
        return result;
    }

    private Map<String, Integer> getEvaluationDetails(Cours cours) throws DataAccessException {
        Map<String, Integer> calc = new HashMap<String, Integer>();
        List<EvaluationDetails> details = evaluationDetailsDao.findByTypeCours(cours.getTypeCours());
        for (EvaluationDetails detail : details) {
            calc.put(detail.getEvaluation().getCode(), detail.getPourcentage());
        }
        return calc;
    }

    @Override
    public ImportationResult importNotes(InputStream stream, Long coursId, Long evaluationId, Long anneeId, int session) throws ServiceException {
        ImportationResult result = new ImportationResult();
        List<ImportationError> erreurs = new ArrayList<ImportationError>();
        int count = 0;
        try {
            Cours cours = coursDao.findById(coursId);
            Evaluation evaluation = evaluationDao.findById(evaluationId);
            AnneeAcademique academique = academiqueDao.findById(anneeId);
            Workbook workbook = WorkbookFactory.create(stream);
            final Sheet sheet = workbook.getSheetAt(0);
            int index = 1;
            Row row = sheet.getRow(index++);
            String matricule;
            String nom;
            while (row != null) {
                Etudiant etudiant;
                System.out.println("Index +++++++ " + index);
                if (row.getCell(1) != null) {
                    matricule = row.getCell(1).getStringCellValue();
                    etudiant = etudiantDao.findByMatricule(matricule);
                    /*} else {
                     nom = row.getCell(2).getStringCellValue();
                     etudiant = etudiantDao.findByName(nom);
                     }*/
                    if (row.getCell(3) != null) {
                        if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            Note note = new Note();
                            note.setValeur(row.getCell(3).getNumericCellValue());
                            note.setActive(1);
                            note.setAnneeAcademique(academique);
                            note.setCours(cours);
                            note.setEtudiant(etudiant);
                            note.setEvaluation(evaluation);
                            if (evaluation.isIsExam()) {
                                Session s = Session.values()[session];
                                note.setSession(s);
                            }
                            try {
                                noteDao.create(note);
                                count++;
                            } catch (Exception ex) {
                                ImportationError err = new ImportationError(index, ex.getMessage());
                                erreurs.add(err);
                            }
                        } else {
                            ImportationError err = new ImportationError(index, "Note invalide");
                            erreurs.add(err);
                        }
                    } else {
                        ImportationError err = new ImportationError(index, "Note indisponible");
                        erreurs.add(err);
                    }
                } else {
                    ImportationError err = new ImportationError(index, "Matricule indisponible");
                    erreurs.add(err);
                }
                row = sheet.getRow(index++);
            }

        } catch (IOException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        result.setNombreImporte(count);
        result.setErreurs(erreurs);
        return result;
    }

    private Note insertNote(String etudiantMatricule, String nomEtudiant, String codeEvaluation, String coursIntitule, Long anneeId, double valeur, int session) {

        try {
            Etudiant etudiant;
            if (etudiantMatricule != null) {
                etudiant = etudiantDao.findByMatricule(etudiantMatricule);
            } else {
                etudiant = etudiantDao.findByName(nomEtudiant);
            }

            Evaluation eval = evaluationDao.findByCode(codeEvaluation);

            Cours cours = coursDao.findByIntitule(coursIntitule);

            AnneeAcademique academique = academiqueDao.findById(anneeId);

            Note note = new Note();
            note.setActive(1);
            note.setAnneeAcademique(academique);
            note.setCours(cours);
            note.setEtudiant(etudiant);
            note.setEvaluation(eval);
            Session s = Session.values()[session];
            note.setSession(s);
            note.setValeur(valeur);
            return noteDao.create(note);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

//    @Override
//    public List<EtudiantNotes> getAllNotesEtudiants(Niveau niveau, Option option, Cours cours) throws ServiceException {
//        List<EtudiantNotes> result = new ArrayList<EtudiantNotes>();
//        boolean state = false;
//        try {
//
//            Map<String, Integer> calc = new HashMap<String, Integer>();
//            List<EvaluationDetails> details = evaluationDetailsDao.findByTypeCours(cours.getTypeCours());
//            for (EvaluationDetails detail : details) {
//                calc.put(detail.getEvaluation().getCode(), detail.getPourcentage());
//            }
//
//            // recuperer les listes des  étudiants du parcours
//            List<Etudiant> etudiants = etudiantDao.listeEtudiantParDepartementEtNiveau(null, academique, niveau, option);
//            for (Etudiant etudiant : etudiants) {
//                EtudiantNotes et = new EtudiantNotes();
//                et.setMatricule(etudiant.getMatricule());
//                et.setNom(etudiant.getNom());
//                Map<String, Double> notes = new HashMap<String, Double>();
//                List<Note> nn = noteDao.listeNoteCours(etudiant, cours, academique, session);
//                for (Note nn1 : nn) {
//                    notes.put(nn1.getEvaluation().getCode(), nn1.getValeur());
//                    state = state || (nn1.getEvaluation().isIsExam() && nn1.getValeur() >= 0);
//                }
//                et.setNote(notes);
//                et.setDetails(calc);
//                if (state || session == Session.normale) {
//                    result.add(et);
//                }
//                state = false;
//            }
//        } catch (DataAccessException ex) {
//            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
    @Override
    public EtudiantNotes getNoteEtudiant(String matricule, long coursId, long anneeId) throws ServiceException {
        EtudiantNotes result = null;
        try {
            Etudiant etudiant = etudiantDao.findByMatricule(matricule);
            Cours c = coursDao.findById(coursId);
            AnneeAcademique annee = academiqueDao.findById(anneeId);
            try {
                annee = academiqueDao.findLastYearNote(etudiant, c, annee);
            } catch (NoResultException nre) {
                annee = null;
            }
            if (annee != null) {
                result = new EtudiantNotes();
                result.setAnnee(annee);
                List<Note> notes = noteDao.getNoteCours(etudiant, c, annee);
                result.setDetails(getEvaluationDetails(c));
                result.setNom(etudiant.getNom());
                result.setMatricule(etudiant.getMatricule());
                Session s = Session.normale;
                boolean status = false;
                Map<String, Double> map = new HashMap<String, Double>();
                for (Note note : notes) {
                    if (note.getEvaluation().isIsExam()) {
                        if (!status) {
                            status = (note.getSession() == Session.rattrapage);
                            map.put(note.getEvaluation().getCode(), note.getValeur());
                            s = note.getSession();
                        }
                    } else {
                        map.put(note.getEvaluation().getCode(), note.getValeur());
                    }
                }
                result.setNote(map);
                result.setSession(s);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public MoyenneUniteEnseignement getMoyenneUEEtudiant(String matricule, long ueId, long anneeId) throws ServiceException {
        MoyenneUniteEnseignement result = null;
        try {
            UniteEnseignement ue = uniteEnseignementDao.findById(ueId);
            // TODO I need to come back here and figure out something
            AnneeAcademique annee = null;
            if (anneeId > 0) {
                annee = academiqueDao.findById(anneeId);
            }
            result = new MoyenneUniteEnseignement(ue.isHasOptionalChoices());
            // TODO I need to find out a way note to issue this query for every studend
            List<CoursCredit> liste = coursDao.findCoursCreditByUe(ue, annee);
            for (CoursCredit cours : liste) {
                EtudiantNotes n = getNoteEtudiant(matricule, cours.getCours().getId(), anneeId);
                if (n != null) {
                    result.getCredits().put(cours.getCours().getIntitule(), cours.getCredit());
                    result.getSessions().add(n.getSession());
                    result.getNotes().put(cours.getCours().getIntitule(), n.getMoyenne());
                    result.getAnnees().add(n.getAnnee());
                } else {
                    result.getCredits().put(cours.getCours().getIntitule(), cours.getCredit());
                    result.getSessions().add(Session.normale);
                    result.getNotes().put(cours.getCours().getIntitule(), 0.0);
                    if (annee != null) {
                        result.getAnnees().add(annee);
                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

//    private MoyenneUniteEnseignement getMoyenneUEEtudiant(String matricule, long anneeId, List<CoursCredit> courss, boolean isOptional) throws ServiceException {
//        MoyenneUniteEnseignement result = null;
//        try {
//            
//            AnneeAcademique annee = null;
//            if (anneeId > 0) {
//                annee = academiqueDao.findById(anneeId);
//            }
//            result = new MoyenneUniteEnseignement(isOptional);
//            for (CoursCredit cours : courss) {
//                EtudiantNotes n = getNoteEtudiant(matricule, cours.getCours().getId(), anneeId);
//                if (n != null) {
//                    result.getCredits().put(cours.getCours().getIntitule(), cours.getCredit());
//                    result.getSessions().add(n.getSession());
//                    result.getNotes().put(cours.getCours().getIntitule(), n.getMoyenne());
//                    result.getAnnees().add(n.getAnnee());
//                } else {
//                    result.getCredits().put(cours.getCours().getIntitule(), cours.getCredit());
//                    result.getSessions().add(Session.normale);
//                    result.getNotes().put(cours.getCours().getIntitule(), 0.0);
//                    if (annee != null) {
//                        result.getAnnees().add(annee);
//                    }
//                }
//            }
//        } catch (DataAccessException ex) {
//            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
    @Override
    public Map<String, MoyenneUniteEnseignement> listeNoteUniteEnseignement(String matricule, long niveauId, long optionId, long semestreId, long anneeId) throws ServiceException {
        try {
            Map<String, MoyenneUniteEnseignement> result = new HashMap<String, MoyenneUniteEnseignement>();
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            Semestre semestre = semestreDao.findById(semestreId);
            AnneeAcademique annee = academiqueDao.findById(anneeId);
            List<UniteEnseignement> liste = uniteEnseignementDao.findByUniteNiveauOptionSemestre(niveau, option, semestre, annee);
            for (UniteEnseignement liste1 : liste) {
                MoyenneUniteEnseignement res = getMoyenneUEEtudiant(matricule, liste1.getId(), anneeId);
                result.put(liste1.getCode(), res);
            }
            return result;
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Map<String, MoyenneUniteEnseignement> listeNoteUniteEnseignement(String matricule, long anneeId, List<UniteEnseignement> ues) throws ServiceException {
        Map<String, MoyenneUniteEnseignement> result = new HashMap<String, MoyenneUniteEnseignement>();

        for (UniteEnseignement liste1 : ues) {
            MoyenneUniteEnseignement res = getMoyenneUEEtudiant(matricule, liste1.getId(), anneeId);
            result.put(liste1.getCode(), res);
        }
        return result;
    }

    @Override
    public List<DeliberationItem> listeDeliberation(long niveauId, long optionId, long coursId, long anneeId, int session, double borneInf, boolean infInclusive, double borneSup, boolean supInclusive, double finale) throws ServiceException {

        try {
            Cours c = coursDao.findById(coursId);
            Niveau n = niveauDao.findById(niveauId);
            Option p = optionDao.findById(optionId);
            AnneeAcademique a = academiqueDao.findById(anneeId);
            Session s = Session.values()[session];
            return lesDeliberation(c, n, p, a, s, infInclusive, borneInf, supInclusive, borneSup, finale);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(400);
        }

    }

    private List<DeliberationItem> lesDeliberation(Cours c, Niveau n, Option o, AnneeAcademique a, Session s, boolean infInclusive, double borneInf, boolean supInclusive, double borneSup, double finale) throws DataAccessException {
        List<DeliberationItem> result = new ArrayList<DeliberationItem>();

        
        List<EtudiantNotes> toto = listeNoteEtudiant(c, a, n, o, s);
        for (EtudiantNotes toto1 : toto) {
            // I need to keep only those with values that belongs to the right interval
            double moyenne = toto1.getMoyenne();
            boolean test1 = infInclusive ? moyenne >= borneInf : moyenne > borneInf;
            boolean test2 = supInclusive ? moyenne <= borneSup : moyenne < borneSup;
            if (test1 && test2) {
                // I add the corresponding student to the list
                DeliberationItem item = new DeliberationItem();
                item.setMatricule(toto1.getMatricule());
                item.setNom(toto1.getNom());
                item.setMoyenneAvant(moyenne);
                // TODO Il fqut chercher un moyen pour recuperer le code de l'examen de la base de données
                double noteAvant = toto1.getNote().get("EE");
                double noteApres = ((finale - moyenne) * 100 / toto1.getDetails().get("EE")) + noteAvant;
                noteApres = Math.ceil(noteApres * 4) / 4.0;
                // TODO je dois surement eviter de faire ce genre de chose
                toto1.getNote().put("EE", noteApres);
                item.setMoyenneApres(toto1.getMoyenne());
                item.setNoteAvant(noteAvant);
                item.setNoteApres(noteApres);
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public int delibererCours(long niveauId, long optionId, long coursId, long anneeId, int session, double borneInf, boolean infInclusive, double borneSup, boolean supInclusive, double finale) throws ServiceException {
        int res = 0;
        try {
            Cours c = coursDao.findById(coursId);
            Niveau n = niveauDao.findById(niveauId);
            Option p = optionDao.findById(optionId);
            AnneeAcademique a = academiqueDao.findById(anneeId);
            Session s = Session.values()[session];
            List<DeliberationItem> result = lesDeliberation(c, n, p, a, s, infInclusive, borneInf, supInclusive, borneSup, finale);
            Evaluation evaluation = evaluationDao.findExamen();
            for (DeliberationItem result1 : result) {
                Etudiant e = etudiantDao.findByMatricule(result1.getMatricule());
                Note note = noteDao.getNoteCours(e, evaluation, c, a, s);
                note.setValeur(result1.getNoteApres());
                noteDao.update(note);
                res++;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;

    }

    @Override
    public List<Note> listeNoteEtudiant(String matricule, long coursId, long anneeId) throws ServiceException {
        try {
            Etudiant etudiant = etudiantDao.findByMatricule(matricule);
            Cours cours = coursDao.findById(coursId);
            AnneeAcademique annee = academiqueDao.findById(anneeId);
            return noteDao.getNoteCours(etudiant, cours, annee);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex);
        }
    }

}
