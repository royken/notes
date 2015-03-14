package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Inscription;
import com.douwe.notes.resource.IInscriptionResource;
import com.douwe.notes.service.IInscriptionService;
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
        try {
            return service.saveOrUpdateInscription(inscription);
        } catch (ServiceException ex) {
            Logger.getLogger(InscriptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Inscription> getAllInscriptions() {
        try {
            return service.getAllInscriptions();
        } catch (ServiceException ex) {
            Logger.getLogger(InscriptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Inscription getInscription(long id) {
        try {
            Inscription inscription = service.findInscriptionById(id);
            if(inscription == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return inscription;
        } catch (ServiceException ex) {
            Logger.getLogger(InscriptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Inscription updateInscription(long id, Inscription inscription) {
        try {
            Inscription inscription1 = service.findInscriptionById(id);
            if(inscription1 != null){
                inscription1.setAnneeAcademique(inscription.getAnneeAcademique());
                inscription1.setEtudiant(inscription.getEtudiant());
                inscription1.setParcours(inscription.getParcours());
                return service.saveOrUpdateInscription(inscription1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(InscriptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteInscription(long id) {
        try {
            service.deleteInscription(id);
        } catch (ServiceException ex) {
            Logger.getLogger(InscriptionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
