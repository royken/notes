package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Note;
import com.douwe.notes.resource.INoteResource;
import com.douwe.notes.service.INoteService;
import java.util.List;
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
        return service.saveOrUpdateNote(note);
    }

    public List<Note> getAllNotes() {
        return service.getAllNotes();
    }

    public Note getNote(long id) {
        Note note = service.findNoteById(id);
        if(note == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return note;
    }

    public Note updateNote(long id, Note note) {
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
    }

    public void deleteNote(long id) {
        service.deleteNote(id);
    }
    
    
}
