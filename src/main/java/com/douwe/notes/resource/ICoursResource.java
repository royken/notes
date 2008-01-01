package com.douwe.notes.resource;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
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
public interface ICoursResource {
    @POST
    @Produces(value = "application/json") 
    Cours createCours(Cours cours);

    @GET
    @Produces(value = "application/json")
    List<Cours> getAllCours();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Cours getCours(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Cours updateCours(@PathParam(value = "id")long id, Cours cours);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteCours(@PathParam(value = "id")long id);
    
}
