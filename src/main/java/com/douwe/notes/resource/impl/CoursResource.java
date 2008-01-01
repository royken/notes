package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.resource.ICoursResource;
import com.douwe.notes.service.ICoursService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/cours")
public class CoursResource implements ICoursResource{
    
    @EJB
    private ICoursService coursService;

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }

    public Cours createCours(Cours cours) {
        return coursService.saveOrUpdateCours(cours);
    }

    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }

    public Cours getCours(long id) {
        Cours cours = coursService.findCoursById(id);
        if(cours == null){
            throw  new  WebApplicationException(Response.Status.NOT_FOUND);
        }
        return cours;
    }

    public Cours updateCours(long id, Cours cours) {
        Cours cours1 = coursService.findCoursById(id);
        if(cours1 != null){
            cours1.setCredit(cours.getCredit());
            cours1.setIntitule(cours.getIntitule());
            cours1.setTypeCours(cours.getTypeCours());
            return coursService.saveOrUpdateCours(cours1);
        }
        return null;
    }

    public void deleteCours(long id) {
        coursService.deleteCours(id);
    }
    
    
    
    
}
