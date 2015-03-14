package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Semestre;
import com.douwe.notes.resource.ISemestreResource;
import com.douwe.notes.service.ISemestreService;
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
@Path("/semestres")
public class SemestreResource implements ISemestreResource{
    
    @EJB
    private ISemestreService semestreService;

    public ISemestreService getSemestreService() {
        return semestreService;
    }

    public void setSemestreService(ISemestreService semestreService) {
        this.semestreService = semestreService;
    }
    
    

    public Semestre createSemestre(Semestre semestre) {
        try {
            return semestreService.saveOrUpdateSemestre(semestre);
        } catch (ServiceException ex) {
            Logger.getLogger(SemestreResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Semestre> getAllSemestres() {
        try {
            return semestreService.getAllSemestre();
        } catch (ServiceException ex) {
            Logger.getLogger(SemestreResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Semestre getSemestre(long id) {
        try {
            Semestre semestre = semestreService.findSemestreById(id);
            if(semestre == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return semestre;
        } catch (ServiceException ex) {
            Logger.getLogger(SemestreResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Semestre updateSemestre(long id, Semestre semestre) {
        try {
            Semestre semestre1 = semestreService.findSemestreById(id);
            if(semestre1 != null){
                semestre1.setIntitule(semestre.getIntitule());
                semestre1.setNiveau(semestre1.getNiveau());
                return semestreService.saveOrUpdateSemestre(semestre1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(SemestreResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteSemestre(long id) {
        try {
            semestreService.deleteSemestre(id);
        } catch (ServiceException ex) {
            Logger.getLogger(SemestreResource.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
}
