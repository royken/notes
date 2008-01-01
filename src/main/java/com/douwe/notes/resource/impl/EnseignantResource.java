package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Enseignant;
import com.douwe.notes.resource.IEnseignantResource;
import com.douwe.notes.service.IEnseignantService;
import java.util.List;
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
        return enseignantService.saveOrUpdateEnseignant(enseignant);
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }

    public Enseignant getEnseignant(long id) {
        Enseignant enseignant = enseignantService.findEnseignantById(id);
        if(enseignant == null){
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return enseignant;
    }

    public Enseignant updateEnseignant(long id, Enseignant enseignant) {
        Enseignant enseignant1 = enseignantService.findEnseignantById(id);
        if(enseignant1 != null){
            enseignant1.setNom(enseignant.getNom());
            return enseignantService.saveOrUpdateEnseignant(enseignant1);
        }
        return null;
    }

    public void deleteEnseignant(long id) {
        enseignantService.deleteEnseignant(id);
    }
}
