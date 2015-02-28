package com.douwe.notes.dao;

import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Note;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface INoteDao extends IDao<Note, Long>{
    //Note d'un étudiant à un cours pour une année acadéique donnée
    public List<Note> listNoteCours(Etudiant etudiant, Cours cours, AnneeAcademique academique);
    
    
}
