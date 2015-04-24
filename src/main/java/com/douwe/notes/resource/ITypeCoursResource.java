package com.douwe.notes.resource;

import com.douwe.notes.entities.EvaluationDetails;
import com.douwe.notes.entities.TypeCours;
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
public interface ITypeCoursResource {
    @POST
    TypeCours createTypeCours(TypeCours typeCours);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    TypeCours getTypeCours(@PathParam(value = "id") long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    TypeCours updateTypeCours(@PathParam(value = "id") long id, TypeCours typeCours);

    @GET
    @Produces(value = "application/json")
    List<TypeCours> getAllTypeCours();
    
    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteTypeCours(@PathParam(value = "id")long id);
    
    @GET
    @Path(value = "{id : \\d+}/evaluations")
    @Produces(value = "application/json")
    List<EvaluationDetails> getEvaluationByTypeCours(@PathParam(value = "id")long id);
    
    @POST
    @Path(value = "{id : \\d+}/evaluations")
    EvaluationDetails saveEvaluationByTypeCours(@PathParam(value = "id")long id, EvaluationDetails details);
    
    @PUT
    @Path(value = "{idTCours : \\d+}/evaluations/{idEvaluation : \\d+}")
    @Produces(value = "application/json")
    EvaluationDetails updateEvalDetailByTypeCours(@PathParam(value = "id")long idTCours,@PathParam(value = "id")long idEvaluation ,EvaluationDetails evaluationDetails);

    @DELETE
    @Path(value = "{idTCours : \\d+}/evaluations/{idEvaluation : \\d+}")
    void deleteEvalDetailByTypeCours(@PathParam(value = "id")long idTCours,@PathParam(value = "id")long idEvaluation);
    
}
