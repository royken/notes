package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IAnneeAcademiqueDao extends IDao<AnneeAcademique, Long>{
    
    public List<AnneeAcademique> findAllActive() throws DataAccessException;
    
    AnneeAcademique findLastYearNote(Etudiant etudiant, Cours c) throws DataAccessException;
    
    //public AnneeAcademique findByString(String date) throws DataAccessException;
}
