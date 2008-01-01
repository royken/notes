package com.douwe.notes.service;

import com.douwe.notes.entities.Note;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface INoteService {
    
    public Note saveOrUpdateNote(Note note);
    
    public void deleteNote(Long id);
    
    public Note findNoteById(long id);
    
    public List<Note> getAllNotes();
}
