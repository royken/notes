package com.douwe.notes.resource;

import com.douwe.notes.entities.Enseignement;
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
public interface IEnseignementResource {
    
    @POST
    @Produces(value = "application/json") 
    Enseignement createEnseignement(Enseignement enseignement);
    
    @POST
    @Path(value = "{anneeId : \\d+}/{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json") 
    Enseignement createEnseignementOption(@PathParam(value = "anneeId")Long anneeId, @PathParam(value = "niveauId")Long niveauId, @PathParam(value = "optionId")Long optionId, Enseignement enseignement);

    @GET
    @Produces(value = "application/json")
    List<Enseignement> getAllEnseignements();
    
    @GET
    @Path(value = "{anneeId : \\d+}/{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json")
    List<Enseignement> getEnseignementsOptions(@PathParam(value = "anneeId")Long anneeId, @PathParam(value = "niveauId")Long niveauId, @PathParam(value = "optionId")Long optionId);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Enseignement getEnseignement(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Enseignement updateEnseignement(@PathParam(value = "id")long id, Enseignement enseignement);
    
    @PUT
    @Path(value = "{anneeId : \\d+}/{niveauId : \\d+}/{optionId : \\d+}/{id : \\d+}")
    @Produces(value = "application/json")
    Enseignement updateEnseignementOption(@PathParam(value = "anneeId")Long anneeId, @PathParam(value = "niveauId")Long niveauId, @PathParam(value = "optionId")Long optionId,@PathParam(value = "id")long id, Enseignement enseignement);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEnseignement(@PathParam(value = "id")long id);
    
}
