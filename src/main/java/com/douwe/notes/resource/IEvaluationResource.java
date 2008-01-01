package com.douwe.notes.resource;

import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Niveau;
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
@Path("/evaluations")
public interface IEvaluationResource {
    
    @POST
    @Produces(value = "application/json")
    Evaluation createEvaluation(Evaluation evaluation);

    @GET
    @Produces(value = "application/json")
    List<Evaluation> getAllEvaluations();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Evaluation getEvaluation(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Evaluation updateEvaluation(@PathParam(value = "id")long id, Evaluation evaluation);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEvaluation(@PathParam(value = "id")long id);
    
}
