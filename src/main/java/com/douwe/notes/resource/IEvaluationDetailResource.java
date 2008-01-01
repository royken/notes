package com.douwe.notes.resource;

import com.douwe.notes.entities.EvaluationDetails;
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
@Path("/evalDetail")
public interface IEvaluationDetailResource {
    
    @POST
    @Produces(value = "application/json")
    EvaluationDetails createEvalDetail(EvaluationDetails evaluationDetails);

    @GET
    @Produces(value = "application/json")
    List<EvaluationDetails> getAllEvalDetails();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    EvaluationDetails getEvalDetail(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    EvaluationDetails updateEvalDetail(@PathParam(value = "id")long id, EvaluationDetails evaluationDetails);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEvalDetail(@PathParam(value = "id")long id);
    
}
