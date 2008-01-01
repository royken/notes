package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Semestre;
import com.douwe.notes.resource.ISemestreResource;
import com.douwe.notes.service.ISemestreService;
import java.util.List;
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
        return semestreService.saveOrUpdateSemestre(semestre);
    }

    public List<Semestre> getAllSemestres() {
        return semestreService.getAllSemestre();
    }

    public Semestre getSemestre(long id) {
        Semestre semestre = semestreService.findSemestreById(id);
        if(semestre == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return semestre;
    }

    public Semestre updateSemestre(long id, Semestre semestre) {
        Semestre semestre1 = semestreService.findSemestreById(id);
        if(semestre1 != null){
            semestre1.setIntitule(semestre.getIntitule());
            semestre1.setNiveau(semestre1.getNiveau());
            return semestreService.saveOrUpdateSemestre(semestre1);
        }
        return null;
    }

    public void deleteSemestre(long id) {
        semestreService.deleteSemestre(id);
    }
    
}
