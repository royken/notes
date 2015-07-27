package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Credit;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ICreditDao extends IDao<Credit, Long>{

    public List<Credit> findByParcours(AnneeAcademique annee,Niveau niveau, Option option) throws DataAccessException;

    public Credit findByCours(Cours cours, Niveau niveau, Option option, AnneeAcademique anne) throws DataAccessException;
    
}
