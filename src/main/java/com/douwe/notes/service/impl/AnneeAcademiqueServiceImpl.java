package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ServiceException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
@Named
public class AnneeAcademiqueServiceImpl implements IAnneeAcademiqueService{
    
    @Inject
    private IAnneeAcademiqueDao academiqueDao;
    
    private final DateFormat df;

    public AnneeAcademiqueServiceImpl() {
       df = new SimpleDateFormat("yyyy");
    }
    
    

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }
    
    


    @Override
    public AnneeAcademique saveOrUpdateAnnee(AnneeAcademique anneeAcademique) throws ServiceException{
        try {
            anneeAcademique.setNumeroDebut(Integer.valueOf(df.format(anneeAcademique.getDebut())));
            if (anneeAcademique.getId() == null) {
                anneeAcademique.setActive(1);
                
                //anneeAcademique.setDateString(df.format(anneeAcademique.getDebut()) + "-" + df.format(anneeAcademique.getFin()));
                return academiqueDao.create(anneeAcademique);
            } else {
                return academiqueDao.update(anneeAcademique);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public void deleteAnnee(Long id) throws ServiceException {
        try {
            AnneeAcademique anneeAcademique = academiqueDao.findById(id);
            if(anneeAcademique != null){
                anneeAcademique.setActive(0);
                academiqueDao.update(anneeAcademique);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public AnneeAcademique findAnneeById(long id) throws ServiceException{
        try {
            return academiqueDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public List<AnneeAcademique> getAllAnnee() throws ServiceException {
        try {
            return academiqueDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(AnneeAcademiqueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    
}
