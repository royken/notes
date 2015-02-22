package com.douwe.notes.resource;

import com.douwe.notes.entities.Semestre;
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
@Path("/semestres")
public interface ISemestreResource {
    
    @POST
    @Produces(value = "application/json") 
    Semestre createSemestre(Semestre semestre);

    @GET
    @Produces(value = "application/json")
    List<Semestre> getAllSemestres();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Semestre getSemestre(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Semestre updateSemestre(@PathParam(value = "id")long id, Semestre semestre);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteSemestre(@PathParam(value = "id")long id);
    
}
