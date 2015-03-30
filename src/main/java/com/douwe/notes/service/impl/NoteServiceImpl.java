package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class NoteServiceImpl implements INoteService {
    
    @Inject
    private INoteDao noteDao;
    
    public INoteDao getNoteDao() {
        return noteDao;
    }
    
    public void setNoteDao(INoteDao noteDao) {
        this.noteDao = noteDao;
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
    public List<EtudiantNotes> getAllNotesEtudiants(Niveau niveau, Option option, Cours cours, UniteEnseignement ue, AnneeAcademique academique,Session session) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
