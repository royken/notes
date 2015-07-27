package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.resource.IProgrammeResource;
import com.douwe.notes.service.IParcoursService;
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
    
    @EJB
    private IParcoursService parcoursService;

    public IProgrammeService getService() {
        return service;
    }

    public void setService(IProgrammeService service) {
        this.service = service;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }
    
    @Override
    public Programme createProgramme(Programme programme) {
        try {
            Niveau n = programme.getParcours().getNiveau();
            Option o = programme.getParcours().getOption();
            Parcours p = parcoursService.findByNiveauOption(n.getId(), o.getId());
            programme.setParcours(p);
            return service.saveOrUpdateProgramme(programme);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Programme> getAllProgrammes() {
        try {
            return service.getAllProgrammes();
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
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

    @Override
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

    @Override
    public void deleteProgramme(long id) {
        try {
            service.deleteProgramme(id);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Programme createProgramme(long niveauId, long optionId, Programme programme) {
        try {
            Parcours p = parcoursService.findByNiveauOption(niveauId, optionId);
            programme.setParcours(p);
            return service.saveOrUpdateProgramme(programme);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }        
    }

    @Override
    public List<Programme> getAllProgrammes(long anneeId, long niveauId, long optionId, long semestreId) {
        try {
            return service.findByOptionAnnee(anneeId, niveauId, optionId, semestreId);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public Programme updateProgramme(long niveauId, long optionId, long id, Programme programme) {
        try {
            Parcours parcours = parcoursService.findByNiveauOption(niveauId, optionId);
            Programme prog = service.findProgrammeById(id);
            prog.setParcours(parcours);
            prog.setAnneeAcademique(programme.getAnneeAcademique());
            prog.setSemestre(programme.getSemestre());
            prog.setUniteEnseignement(programme.getUniteEnseignement());
            return service.saveOrUpdateProgramme(prog);
        } catch (ServiceException ex) {
            Logger.getLogger(ProgrammeResource.class.getName()).log(Level.SEVERE, null, ex);
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
