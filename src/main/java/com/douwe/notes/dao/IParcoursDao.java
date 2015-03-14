package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IParcoursDao extends IDao<Parcours, Long>{
    
    public void deleteActive(Parcours parcours) throws DataAccessException;
    
    public List<Parcours> findAllActive()throws DataAccessException;
    
    public Parcours findByNiveauOption(Niveau niveau, Option option) throws DataAccessException;
    
}
