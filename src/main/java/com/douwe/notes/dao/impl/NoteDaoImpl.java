package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.INoteDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Note;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class NoteDaoImpl extends GenericDao<Note, Long> implements INoteDao{

    public List<Note> listNoteCours(Etudiant etudiant, Cours cours, AnneeAcademique academique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
