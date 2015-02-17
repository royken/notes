package com.douwe.notes.resource;

import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Option;
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
@Path("/etudiants")
public interface IEtudiantResource {
    
    @POST
    @Consumes(value = "application/json")
    @Produces(value = "application/json") 
    Etudiant createEtudiant(Etudiant etudiant);

    @GET
    @Produces(value = "application/json")
    List<Etudiant> getAllEtudiants();

    @GET
    @Path(value = "{id : \\d+}")
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    Etudiant getEtudiant(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    Etudiant updateEtudiant(@PathParam(value = "id")long id, Etudiant etudiant);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEtudiant(@PathParam(value = "id")long id);
    
}
