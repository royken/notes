package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.IAnneeAcademiqueService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class AnneeAcademiqueServiceImpl implements IAnneeAcademiqueService{
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }
    
    

    public AnneeAcademique saveOrUpdateAnnee(AnneeAcademique anneeAcademique) {
        try {
            if (anneeAcademique.getId() == null) {
                return academiqueDao.create(anneeAcademique);
            } else {
                return academiqueDao.update(anneeAcademique);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteAnnee(Long id) {
        try {
            AnneeAcademique anneeAcademique = academiqueDao.findById(id);
            if(anneeAcademique != null){
                academiqueDao.delete(anneeAcademique);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnneeAcademique findAnneeById(long id) {
        try {
            return academiqueDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AnneeAcademique> getAllAnnee() {
        try {
            return academiqueDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
