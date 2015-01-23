package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import javax.inject.Named;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Named
public class DepartementDaoImpl extends GenericDao<Departement, Long> implements IDepartementDao{
    
}
