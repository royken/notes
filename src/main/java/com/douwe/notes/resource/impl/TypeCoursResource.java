package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.resource.ITypeCoursResource;
import com.douwe.notes.service.ITypeCoursService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/typeCours")
public class TypeCoursResource implements ITypeCoursResource{
    
    @EJB
    private ITypeCoursService typeCoursService;

    public ITypeCoursService getTypeCoursService() {
        return typeCoursService;
    }

    public void setTypeCoursService(ITypeCoursService typeCoursService) {
        this.typeCoursService = typeCoursService;
    }

    public TypeCours createTypeCours(TypeCours typeCours) {
        return typeCoursService.saveOrUpdateTpyeCours(typeCours);
    }

    public TypeCours getTypeCours(long id) {
        TypeCours typeCours = typeCoursService.findTypeCoursById(id);
        if(typeCours == null){
            throw  new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return typeCours;
    }

    public TypeCours updateTypeCours(long id, TypeCours typeCours) {
        TypeCours typeCours1 = typeCoursService.findTypeCoursById(id);
        if(typeCours1 != null){
            typeCours1.setNom(typeCours.getNom());
            return typeCoursService.saveOrUpdateTpyeCours(typeCours1);
        }
        return null;
    }

    public List<TypeCours> getAllTypeCours() {
        return typeCoursService.getAllTypeCours();
    }

    public void deleteTypeCours(long id) {
        typeCoursService.deleteTypeCours(id);
    }    
}
