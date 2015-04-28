package com.douwe.notes.resource;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;
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
@Path("/uniteEns")
public interface IUniteEnseignementResource {
    
    @POST
    @Produces(value = "application/json") 
    UniteEnseignement createUniteEnseignement(UniteEnseignement ue);

    @GET
    @Produces(value = "application/json")
    List<UniteEnseignement> getAllUniteEnseignements();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    UniteEnseignement getUniteEnseignement(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    UniteEnseignement updateSemestre(@PathParam(value = "id")long id, UniteEnseignement ue);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteUniteEns(@PathParam(value = "id")long id);
    
    @GET
    @Path(value = "{id : \\d+}/cours")
    @Produces(value = "application/json")
    List<Cours> findAllCoursByUe(@PathParam(value = "id")long id);
    
}
