package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.resource.IEnseignementResource;
import com.douwe.notes.service.IEnseignementService;
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
@Path("/enseignements")
public class EnseignementResource implements IEnseignementResource{
    
    @EJB
    private IEnseignementService service;

    public IEnseignementService getService() {
        return service;
    }

    public void setService(IEnseignementService service) {
        this.service = service;
    }
    
    public Enseignement createEnseignement(Enseignement enseignement) {
        try {
            return service.saveOrUpdateEnseignement(enseignement);
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Enseignement> getAllEnseignements() {
        try {
            return service.getAllEnseignements();
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Enseignement getEnseignement(long id) {
        try {
            Enseignement enseignement = service.findEnseignementById(id);
            if(enseignement == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return enseignement;
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Enseignement updateEnseignement(long id, Enseignement enseignement) {
        try {
            Enseignement enseignement1 = service.findEnseignementById(id);
            if(enseignement1 != null){
                enseignement1.setAnneeAcademique(enseignement.getAnneeAcademique());
                enseignement1.setCours(enseignement.getCours());
                enseignement1.setEnseignants(enseignement.getEnseignants());
                return service.saveOrUpdateEnseignement(enseignement1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteEnseignement(long id) {
        try {
            service.deleteEnseignement(id);
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
