package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.dao.IEvaluationDetailsDao;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
        List<EtudiantNotes> result = new ArrayList<EtudiantNotes>();
        try {

            Map<String, Integer> calc = new HashMap<String, Integer>();
            List<EvaluationDetails> details = evaluationDetailsDao.findByTypeCours(cours.getTypeCours());
            for (EvaluationDetails detail : details) {
                calc.put(detail.getEvaluation().getCode(), detail.getPourcentage());
            }
            // Recuperer toutes les evaluations du cours concerné
            List<Evaluation> evaluations = evaluationDao.evaluationForCourses(cours);

            // recuperer les listes des  étudiants du parcours
            List<Etudiant> etudiants = etudiantDao.listeEtudiantParDepartementEtNiveau(null, academique, niveau, option);
            for (Etudiant etudiant : etudiants) {
                EtudiantNotes et = new EtudiantNotes();
                et.setMatricule(etudiant.getMatricule());
                et.setNom(etudiant.getNom());
                Map<String, Double> notes = new HashMap<String, Double>();
                for (Evaluation eval : evaluations) {
                    try {
                        Note n = noteDao.getNoteCours(etudiant, eval, cours, academique, session);
                        notes.put(eval.getCode(), n.getValeur());
                    } catch (NoResultException nre) {

                    }
                }
                et.setNote(notes);
                et.setDetails(calc);
                result.add(et);
            }
            // pour chaque etudiant recuperer les différentes notes
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Renvoyer une liste de tuples et transformer ces tuples en EtudiantNotes
//            List<Tuple> tmp = noteDao.getAllNotes(niveau, option, cours, ue, academique, session);
//            for (Tuple tmp1 : tmp) {
//                EtudiantNotes e = new EtudiantNotes();
//                e.setMatricule(tmp1.get("matricule", String.class));
//                e.setNom(tmp1.get("nom", String.class));
//                Map<String, Double> notes = new HashMap<String, Double>();
//                notes.put("CC", tmp1.get("CC", Double.class));
//                notes.put("TPE", tmp1.get("CC", Double.class));
//                notes.put("EE", tmp1.get("CC", Double.class));
//                e.setNote(notes);
        //}
        return result;
    }

    @Override
    public void importNotes(InputStream stream, Long coursId, Long evaluationId, Long anneeId, int session) throws ServiceException {
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
                if (row.getCell(1) != null) {
                    matricule = row.getCell(1).getStringCellValue();
                    etudiant = etudiantDao.findByMatricule(matricule);
                } else {
                    nom = row.getCell(2).getStringCellValue();
                    etudiant = etudiantDao.findByName(nom);
                }
                if (row.getCell(3) != null) {
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
                    noteDao.create(note);
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

    }

    private Note insertNote(String etudiantMatricule, String nomEtudiant, String codeEvaluation, String coursIntitule, Long anneeId, double valeur, int session) {

        try {
            Etudiant etudiant = new Etudiant();
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

}
