package com.douwe.notes.resource;

import com.douwe.notes.entities.Enseignant;
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
public interface IEnseignantResource {
 
    @POST
    @Produces(value = "application/json") 
    Enseignant createEnseignant(Enseignant enseignant);

    @GET
    @Produces(value = "application/json")
    List<Enseignant> getAllEnseignants();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Enseignant getEnseignant(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    
    @Produces(value = "application/json")
    Enseignant updateEnseignant(@PathParam(value = "id")long id, Enseignant enseignant);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEnseignant(@PathParam(value = "id")long id);
}
