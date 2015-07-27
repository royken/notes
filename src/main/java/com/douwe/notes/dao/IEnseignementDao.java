package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEnseignementDao extends IDao<Enseignement, Long>{
    
    public void deleteActive(Enseignement enseignement) throws DataAccessException;
    
    public List<Enseignement> findAllActive() throws  DataAccessException;

    public List<Enseignement> findByOption(AnneeAcademique annee, Niveau niveau, Option option) throws DataAccessException;
    
}
