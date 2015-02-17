package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Named
public class DepartementDaoImpl extends GenericDao<Departement, Long> implements IDepartementDao{

    public List<Option> getAllOptions(Departement departement) throws DataAccessException {
      return  getManager().createNamedQuery("Departement.getAllOptions").setParameter("idParam", departement.getId()).getResultList();
    }
    
}
