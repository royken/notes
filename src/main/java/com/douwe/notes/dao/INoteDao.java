package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;
import javax.persistence.Tuple;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface INoteDao extends IDao<Note, Long>{
    //Note d'un étudiant à un cours pour une année acadéique donnée
    public List<Note> listeNoteCours(Etudiant etudiant, Cours cours, AnneeAcademique academique, com.douwe.notes.entities.Session session) throws DataAccessException;
    
    public Note getNoteCours(Etudiant etudiant, Evaluation evaluation, Cours cours, AnneeAcademique academique, com.douwe.notes.entities.Session session)throws DataAccessException;
    
    
    List<Tuple> getAllNotes(Niveau niveau, Option option, Cours cours, UniteEnseignement ue, AnneeAcademique academique,Session session) throws DataAccessException;
    
    
    
    
}
