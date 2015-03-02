package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.resource.IEnseignantResource;
import com.douwe.notes.service.IEnseignantService;
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
@Path("/enseignants")
public class EnseignantResource implements IEnseignantResource{
    
    @EJB
    private IEnseignantService enseignantService;

    public IEnseignantService getEnseignantService() {
        return enseignantService;
    }

    public void setEnseignantService(IEnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public Enseignant createEnseignant(Enseignant enseignant) {
        try {
            return enseignantService.saveOrUpdateEnseignant(enseignant);
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Enseignant> getAllEnseignants() {
        try {
            return enseignantService.getAllEnseignants();
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Enseignant getEnseignant(long id) {
        try {
            Enseignant enseignant = enseignantService.findEnseignantById(id);
            if(enseignant == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return enseignant;
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Enseignant updateEnseignant(long id, Enseignant enseignant) {
        try {
            Enseignant enseignant1 = enseignantService.findEnseignantById(id);
            if(enseignant1 != null){
                enseignant1.setNom(enseignant.getNom());
                return enseignantService.saveOrUpdateEnseignant(enseignant1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteEnseignant(long id) {
        try {
            enseignantService.deleteEnseignant(id);
        } catch (ServiceException ex) {
            Logger.getLogger(EnseignantResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
