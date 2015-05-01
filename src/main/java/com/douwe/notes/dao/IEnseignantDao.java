package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.entities.Parcours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEnseignantDao extends IDao<Enseignant, Long>{
    
    public void deleteActive(Enseignant enseignant) throws DataAccessException;
    
    public List<Enseignant> findAllActive() throws DataAccessException;
    
    public List<Enseignant> findByCours(Cours cours, AnneeAcademique annee, Parcours parcours) throws DataAccessException;
    
}
