package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.resource.IAnneeResource;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@Path("/annees")
public class AnneeResource implements IAnneeResource{
    
    @EJB
    private IAnneeAcademiqueService academiqueService;

    public IAnneeAcademiqueService getAcademiqueService() {
        return academiqueService;
    }

    public void setAcademiqueService(IAnneeAcademiqueService academiqueService) {
        this.academiqueService = academiqueService;
    }
    
    

    @Override
    public AnneeAcademique createAnnee(AnneeAcademique anneeAcademique) {
        try {
            return academiqueService.saveOrUpdateAnnee(anneeAcademique);
        } catch (ServiceException ex) {
            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<AnneeAcademique> getAllAnnees() {
        try {
            return academiqueService.getAllAnnee();
        } catch (ServiceException ex) {
            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public AnneeAcademique getAnnee(long id) {
        try {
            AnneeAcademique anneeAcademique = academiqueService.findAnneeById(id);
            if(anneeAcademique == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return anneeAcademique;
        } catch (ServiceException ex) {
            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public AnneeAcademique updateAnnee(long id, AnneeAcademique anneeAcademique) {
        try {
            AnneeAcademique academique = academiqueService.findAnneeById(id);
            if(academique != null){
                academique.setDebut(anneeAcademique.getDebut());
                academique.setFin(anneeAcademique.getFin());
                return academiqueService.saveOrUpdateAnnee(academique);
            }
            
        } catch (ServiceException ex) {
            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    @Override
    public void deleteAnnee(long id) {
        try {
            academiqueService.deleteAnnee(id);
        } catch (ServiceException ex) {
            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//    @Override
//    public AnneeAcademique getAnnee(String annee) {
//        try {
//            AnneeAcademique date = academiqueService.findAnneeByString(annee);
//            
//            if(date == null){
//                throw  new WebApplicationException(Response.Status.NOT_FOUND);
//            }
//            return date;
//        } catch (ServiceException ex) {
//            Logger.getLogger(AnneeResource.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//                
//    }

    
}
