package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Inscription;
import com.douwe.notes.resource.IInscriptionResource;
import com.douwe.notes.service.IInscriptionService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/inscriptions")
public class InscriptionResource implements IInscriptionResource{
    
    @EJB
    private IInscriptionService service;

    public IInscriptionService getService() {
        return service;
    }

    public void setService(IInscriptionService service) {
        this.service = service;
    }
    
    

    public Inscription createSemestre(Inscription inscription) {
        return service.saveOrUpdateInscription(inscription);
    }

    public List<Inscription> getAllInscriptions() {
        return service.getAllInscriptions();
    }

    public Inscription getInscription(long id) {
        Inscription inscription = service.findInscriptionById(id);
        if(inscription == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return inscription;
    }

    public Inscription updateInscription(long id, Inscription inscription) {
        Inscription inscription1 = service.findInscriptionById(id);
        if(inscription1 != null){
            inscription1.setAnneeAcademique(inscription.getAnneeAcademique());
            inscription1.setEtudiant(inscription.getEtudiant());
            inscription1.setParcours(inscription.getParcours());
            return service.saveOrUpdateInscription(inscription1);
        }
        return null;
    }

    public void deleteInscription(long id) {
        service.deleteInscription(id);
    }
    
}
