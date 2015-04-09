package com.douwe.notes.service;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.EtudiantNotes;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface INoteService {
    
    public Note saveOrUpdateNote(Note note) throws ServiceException;
    
    public void deleteNote(Long id) throws ServiceException;
    
    public Note findNoteById(long id) throws ServiceException;
    
    public List<Note> getAllNotes() throws ServiceException;
    
    public List<EtudiantNotes> getAllNotesEtudiants(Niveau niveau, Option option, Cours cours, UniteEnseignement ue, AnneeAcademique academique, Session session) throws ServiceException;
    
    public void importNotes(InputStream stream, Long coursId, Long evaluationId, Long anneeId,int session) throws ServiceException;
}
