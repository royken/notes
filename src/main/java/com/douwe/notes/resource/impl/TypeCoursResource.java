package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.resource.ITypeCoursResource;
import com.douwe.notes.service.ITypeCoursService;
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

    @Override
    public TypeCours createTypeCours(TypeCours typeCours) {
        try {
            return typeCoursService.saveOrUpdateTpyeCours(typeCours);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public TypeCours getTypeCours(long id) {
        try {
            TypeCours typeCours = typeCoursService.findTypeCoursById(id);
            if(typeCours == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return typeCours;
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public TypeCours updateTypeCours(long id, TypeCours typeCours) {
        try {
            TypeCours typeCours1 = typeCoursService.findTypeCoursById(id);
            if(typeCours1 != null){
                typeCours1.setNom(typeCours.getNom());
                return typeCoursService.saveOrUpdateTpyeCours(typeCours1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<TypeCours> getAllTypeCours() {
        try {
            return typeCoursService.getAllTypeCours();
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteTypeCours(long id) {
        try {
            typeCoursService.deleteTypeCours(id);
        } catch (ServiceException ex) {
            Logger.getLogger(TypeCoursResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
