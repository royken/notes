package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.entities.Note;
import com.douwe.notes.service.INoteService;
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
public class NoteServiceImpl implements INoteService{
    
    @Inject
    private INoteDao noteDao;

    public INoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(INoteDao noteDao) {
        this.noteDao = noteDao;
    }
    
    

    public Note saveOrUpdateNote(Note note) {
        try {
        if(note.getId() == null){
            
                return noteDao.create(note);
            
        }
        else{
            return noteDao.update(note);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
    }

    public void deleteNote(Long id) {
        try {
            Note note = noteDao.findById(id);
            if(note != null){
                noteDao.delete(note);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Note findNoteById(long id) {
        try {
            return noteDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Note> getAllNotes() {
        try {
            return noteDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
