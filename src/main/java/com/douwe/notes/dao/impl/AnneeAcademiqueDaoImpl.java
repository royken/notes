package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.entities.AnneeAcademique;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class AnneeAcademiqueDaoImpl extends GenericDao<AnneeAcademique, Long> implements IAnneeAcademiqueDao{

    @Override
    public List<AnneeAcademique> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Annee.findAllActive").getResultList();
    }

//    @Override
//    public AnneeAcademique findByString(String date) throws DataAccessException {
//        return (AnneeAcademique)(getManager().createNamedQuery("Annee.findByString").setParameter("param", date).getSingleResult());
//    }
    
    
    
}
