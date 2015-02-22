package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.resource.IAnneeResource;
import com.douwe.notes.service.IAnneeAcademiqueService;
import java.util.List;
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
    
    

    public AnneeAcademique createAnnee(AnneeAcademique anneeAcademique) {
        return academiqueService.saveOrUpdateAnnee(anneeAcademique);
    }

    public List<AnneeAcademique> getAllAnnees() {
        return academiqueService.getAllAnnee();
    }

    public AnneeAcademique getAnnee(long id) {
        AnneeAcademique anneeAcademique = academiqueService.findAnneeById(id);
        if(anneeAcademique == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return anneeAcademique;
    }

    public AnneeAcademique updateAnnee(long id, AnneeAcademique anneeAcademique) {
        AnneeAcademique academique = academiqueService.findAnneeById(id);
        if(academique != null){
            academique.setDebut(anneeAcademique.getDebut());
            academique.setFin(anneeAcademique.getFin());
            return academiqueService.saveOrUpdateAnnee(academique);
        }
        return null;
    }

    public void deleteAnnee(long id) {
        academiqueService.deleteAnnee(id);
    }
    
}
