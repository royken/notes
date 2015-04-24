package com.douwe.notes.resource;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
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
@Path("/parcours")
public interface IParcoursResource {
    
    @POST
    @Produces(value = "application/json")
    Parcours createParcours(Parcours parcours);

    @GET
    @Produces(value = "application/json")
    List<Parcours> getAllParcours();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Parcours getParcours(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Parcours updateParcours(@PathParam(value = "id")long id, Parcours parcours);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteParcours(@PathParam(value = "id")long id);
    
    
    @GET
    @Path("{idNiveau : \\d+}/{idOption : \\d+}")
    @Produces(value = "application/json")
    public Parcours findByNiveauOption(@PathParam(value = "idNiveau") long idNveau, @PathParam(value = "idOption")long idOption);
    
    @GET
    @Path(value = "{idParcours : \\d+}/cours/{idAnnee : \\d+}")
    @Produces(value = "application/json")
    List<Cours> getCoursByParcoursAnnee(@PathParam(value = "idParcours")long idParcours,@PathParam(value = "idAnnee")long idAnnee);
    
}
