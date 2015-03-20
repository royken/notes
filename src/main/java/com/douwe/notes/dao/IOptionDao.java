package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IOptionDao extends IDao<Option, Long>{
    
    public Departement findDepartement(Option option) throws DataAccessException;
    
    public Option findByCode(String code) throws DataAccessException;

    public List<Option> findByDepartementNiveau(Departement dep, Niveau niv) throws DataAccessException;
}
