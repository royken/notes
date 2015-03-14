package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Note;
import com.douwe.notes.resource.INoteResource;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/notes")
public class NoteResource implements INoteResource{
    
    @EJB
    private INoteService service;

    public INoteService getService() {
        return service;
    }

    public void setService(INoteService service) {
        this.service = service;
    }
    
    

    public Note createNote(Note note) {
        try {
            return service.saveOrUpdateNote(note);
        } catch (ServiceException ex) {
            Logger.getLogger(NoteResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Note> getAllNotes() {
        try {
            return service.getAllNotes();
        } catch (ServiceException ex) {
            Logger.getLogger(NoteResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Note getNote(long id) {
        try {
            Note note = service.findNoteById(id);
            if(note == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return note;
        } catch (ServiceException ex) {
            Logger.getLogger(NoteResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Note updateNote(long id, Note note) {
        try {
            Note note1 = service.findNoteById(id);
            if(note1 != null){
                note1.setAnneeAcademique(note.getAnneeAcademique());
                note1.setCours(note.getCours());
                note1.setEtudiant(note.getEtudiant());
                note1.setEvaluation(note.getEvaluation());
                note1.setSession(note.getSession());
                note1.setValeur(note.getValeur());
                return service.saveOrUpdateNote(note1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(NoteResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteNote(long id) {
        try {
            service.deleteNote(id);
        } catch (ServiceException ex) {
            Logger.getLogger(NoteResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
