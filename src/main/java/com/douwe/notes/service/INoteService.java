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
    
    public Note saveOrUpdateNote(Note note) throws ServiceException;
    
    public void deleteNote(Long id) throws ServiceException;
    
    public Note findNoteById(long id) throws ServiceException;
    
    public List<Note> getAllNotes() throws ServiceException;
}
