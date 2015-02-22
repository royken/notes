package com.douwe.notes.resource.impl;


import com.douwe.notes.entities.Parcours;
import com.douwe.notes.resource.IParcoursResource;
import com.douwe.notes.service.IParcoursService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/parcours")
public class ParcoursResource implements IParcoursResource{
    
    @EJB
    private IParcoursService parcoursService;

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }
    
    

    public Parcours createParcours(Parcours parcours) {
        return parcoursService.saveOrUpdateParcours(parcours);
    }

    public List<Parcours> getAllParcours() {
        return parcoursService.getAllParcours();
    }

    public Parcours getParcours(long id) {
        Parcours parcours = parcoursService.findParcoursById(id);
        if (parcours == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return parcours;
    }

    public Parcours updateParcours(long id, Parcours parcours) {
        Parcours parcours1 = parcoursService.findParcoursById(id);
        if(parcours1 != null){
            parcours1.setNiveau(parcours.getNiveau());
            parcours1.setOption(parcours.getOption());
            parcours1.setUniteEnseignements(parcours.getUniteEnseignements());
            return parcoursService.saveOrUpdateParcours(parcours1);
        }
        return null;
    }

    public void deleteParcours(long id) {
        parcoursService.deleteParcours(id);
    }
    
}
