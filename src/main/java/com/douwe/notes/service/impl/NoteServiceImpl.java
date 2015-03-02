package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.entities.Note;
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
public class NoteServiceImpl implements INoteService{
    
    @Inject
    private INoteDao noteDao;

    public INoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(INoteDao noteDao) {
        this.noteDao = noteDao;
    }
    
    

    public Note saveOrUpdateNote(Note note) throws ServiceException{
        try {
        if(note.getId() == null){
                return noteDao.create(note);            
        }
        else{
            return noteDao.update(note);
        }
        } catch (DataAccessException ex) {
                Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw  new ServiceException("La ressource demandée est introuvable");
            }
            
    }

    public void deleteNote(Long id) throws ServiceException{
        try {
            Note note = noteDao.findById(id);
            if(note != null){
                noteDao.delete(note);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public Note findNoteById(long id) throws ServiceException{
        try {
            return noteDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    public List<Note> getAllNotes() throws ServiceException{
        try {
            return noteDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(NoteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }
    
}
