package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.resource.IUniteEnseignementResource;
import com.douwe.notes.service.IUniteEnseignementService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/uniteEns")
public class UniteEnseignementResource implements IUniteEnseignementResource{
    
    @EJB
    private IUniteEnseignementService service;

    public IUniteEnseignementService getService() {
        return service;
    }

    public void setService(IUniteEnseignementService service) {
        this.service = service;
    }
    
    

    public UniteEnseignement createUniteEnseignement(UniteEnseignement ue) {
        return service.saveOrUpdateCours(ue);
    }

    public List<UniteEnseignement> getAllUniteEnseignements() {
        return service.getAllUniteEnseignements();
    }

    public UniteEnseignement getUniteEnseignement(long id) {
        UniteEnseignement enseignement = service.findUniteEnseignementById(id);
        if(enseignement == null){
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return enseignement;
    }

    public UniteEnseignement updateSemestre(long id, UniteEnseignement ue) {
        UniteEnseignement enseignement = service.findUniteEnseignementById(id);
        if(enseignement != null){
            enseignement.setCode(ue.getCode());
            enseignement.setIntitule(ue.getIntitule());
            enseignement.setParcours(ue.getParcours());
            return service.saveOrUpdateCours(enseignement);
        }
        return null;
    }

    public void deleteUniteEns(long id) {
        service.deleteUniteEnseignement(id);
    }
    
}
