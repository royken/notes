package com.douwe.notes.resource;

import com.douwe.notes.entities.AnneeAcademique;
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
@Path("/annees")
public interface IAnneeResource {
    
    @POST
    @Produces(value = "application/json") 
    AnneeAcademique createAnnee(AnneeAcademique anneeAcademique);

    @GET
    @Produces(value = "application/json")
    List<AnneeAcademique> getAllAnnees();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    AnneeAcademique getAnnee(@PathParam(value = "id")long id);
    
    @GET
    @Path(value = "{annee}")
    @Produces(value = "application/json")
    AnneeAcademique getAnnee(@PathParam(value = "id")String annee);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    AnneeAcademique updateAnnee(@PathParam(value = "id")long id, AnneeAcademique anneeAcademique);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteAnnee(@PathParam(value = "id")long id);
    
}
