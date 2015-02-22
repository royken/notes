package com.douwe.notes.resource;

import com.douwe.notes.entities.Programme;
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
@Path("/programmes")
public interface IProgrammeResource {
    
    @POST
    @Produces(value = "application/json") 
    Programme createProgramme(Programme programme);

    @GET
    @Produces(value = "application/json")
    List<Programme> getAllProgrammes();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Programme getProgramme(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Programme updateProgramme(@PathParam(value = "id")long id, Programme programme);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteProgramme(@PathParam(value = "id")long id);
    
}
