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
    
    @POST
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json")
    UniteEnseignement addUniteEnseignement(@PathParam(value ="niveauId")Long niveauId, @PathParam(value ="optionId")Long optionId, UniteEnseignement ue);
    
    @PUT
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}/{id : \\d+}")
    @Produces(value = "application/json")
    UniteEnseignement miseAJourUniteEnseignement(@PathParam(value ="niveauId")Long niveauId, @PathParam(value ="optionId")Long optionId, @PathParam(value ="id")Long id, UniteEnseignement ue);

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
    
    @GET
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json")
    List<UniteEnseignement> findByParcours(@PathParam(value = "niveauId")long niveauId, @PathParam(value = "optionId")long optionId);
    
}
