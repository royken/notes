package com.douwe.notes.resource.impl;


import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.resource.IParcoursResource;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
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
@Path("/parcours")
public class ParcoursResource implements IParcoursResource{
    
    @EJB
    private IParcoursService parcoursService;
    
    @EJB
    private INiveauService niveauService;
    
    @EJB
    private IOptionService optionService;
    
    @EJB
    private ICoursService coursService;

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }
    
    
    
    

    @Override
    public Parcours createParcours(Parcours parcours) {
        try {
            return parcoursService.saveOrUpdateParcours(parcours);
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Parcours> getAllParcours() {
        try {
            return parcoursService.getAllParcours();
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Parcours getParcours(long id) {
        try {
            Parcours parcours = parcoursService.findParcoursById(id);
            if (parcours == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return parcours;
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Parcours updateParcours(long id, Parcours parcours) {
        try {
            Parcours parcours1 = parcoursService.findParcoursById(id);
            if(parcours1 != null){
                parcours1.setNiveau(parcours.getNiveau());
                parcours1.setOption(parcours.getOption());
                return parcoursService.saveOrUpdateParcours(parcours1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteParcours(long id) {
        try {
            parcoursService.deleteParcours(id);
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Parcours findByNiveauOption(long idNveau, long idOption) {
        try {       
            
            Parcours parcours = parcoursService.findByNiveauOption(idNveau, idOption);
            if(parcours == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return parcours;
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Cours> getCoursByParcoursAnnee(long idParcours, long idAnnee) {
        try {
            return coursService.findByParcoursAnnee(idParcours, idAnnee);
        } catch (ServiceException ex) {
            Logger.getLogger(ParcoursResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
}
