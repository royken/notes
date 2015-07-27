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
    
    @POST
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json") 
    Programme createProgramme(@PathParam(value = "niveauId")long niveauId,@PathParam(value = "optionId")long optionId, Programme programme);

    @GET
    @Produces(value = "application/json")
    List<Programme> getAllProgrammes();
    
    @GET
    @Path(value = "{anneeId : \\d+}/{niveauId : \\d+}/{optionId : \\d+}/{semestreId : \\d+}")
    @Produces(value = "application/json")
    List<Programme> getAllProgrammes(@PathParam(value = "anneeId")long anneeId,@PathParam(value = "niveauId")long niveauId,@PathParam(value = "optionId")long optionId,@PathParam(value = "semestreId")long semestreId);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Programme getProgramme(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Programme updateProgramme(@PathParam(value = "id")long id, Programme programme);
    
    @PUT
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}/{id : \\d+}")
    @Produces(value = "application/json")
    Programme updateProgramme(@PathParam(value = "niveauId")long niveauId,@PathParam(value = "optionId")long optionId,@PathParam(value = "id")long id, Programme programme);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteProgramme(@PathParam(value = "id")long id);
    
}
