package com.douwe.notes.resource;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.TypeCours;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/typeCours")
public interface ITypeCoursResource {
    @POST
    @Consumes(value = "application/json")
    TypeCours createTypeCours(TypeCours typeCours);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    TypeCours getTypeCours(@PathParam(value = "id") long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Consumes(value = "application/json")
    TypeCours updateTypeCours(@PathParam(value = "id") long id, TypeCours typeCours);

    @GET
    @Produces(value = "application/json")
    List<TypeCours> getAllTypeCours();
    
    @DELETE
    @Path(value = "{id : \\d+}")
    @Consumes(value = "application/json")
    void deleteTypeCours(@PathParam(value = "id")long id);
    
}
