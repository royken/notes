package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Programme;
import com.douwe.notes.resource.IProgrammeResource;
import com.douwe.notes.service.IProgrammeService;
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
@Path("/programmes")
public class ProgrammeResource implements IProgrammeResource{
    
    @EJB
    private IProgrammeService service;

    public IProgrammeService getService() {
        return service;
    }

    public void setService(IProgrammeService service) {
        this.service = service;
    }
    
    

    public Programme createProgramme(Programme programme) {
        try {
            return service.saveOrUpdateProgramme(programme);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Programme> getAllProgrammes() {
        try {
            return service.getAllProgrammes();
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Programme getProgramme(long id) {
        try {
            Programme programme = service.findProgrammeById(id);
            if(programme == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Programme updateProgramme(long id, Programme programme) {
        try {
            Programme programme1 = service.findProgrammeById(id);
            if(programme1 != null){
                programme1.setAnneeAcademique(programme.getAnneeAcademique());
                programme1.setParcours(programme.getParcours());
                programme1.setUniteEnseignement(programme.getUniteEnseignement());
                return service.saveOrUpdateProgramme(programme1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteProgramme(long id) {
        try {
            service.deleteProgramme(id);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
