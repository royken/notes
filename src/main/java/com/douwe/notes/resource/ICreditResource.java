package com.douwe.notes.resource;

import com.douwe.notes.entities.Credit;
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
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ICreditResource {

    @POST
    @Produces(value = "application/json")
    Credit createCredit(Credit credit);

    @POST
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json")
    Credit createCredit(@PathParam(value = "niveauId") long niveauId, @PathParam(value = "optionId") long optionId, Credit credit);

    @PUT
    @Path(value = "{id : \\d+}*")
    @Produces(value = "application/json")
    Credit updateCredit(@PathParam(value = "id") long id, Credit credit);

    @PUT
    @Path(value = "{niveauId : \\d+}/{optionId : \\d+}/{id : \\d+}")
    @Produces(value = "application/json")
    Credit updateCredit(@PathParam(value = "niveauId") long niveauId, @PathParam(value = "optionId") long optionId, @PathParam(value = "id") long id, Credit credit);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteCredit(@PathParam(value = "id") long id);

    @GET
    @Produces(value = "application/json")
    List<Credit> getAllCredit();

    @GET
    @Path(value = "{anneeId : \\d+}/{niveauId : \\d+}/{optionId : \\d+}")
    @Produces(value = "application/json")
    List<Credit> getAllCredit(@PathParam(value = "anneeId")long anneeId,@PathParam(value = "niveauId")long niveauId, @PathParam(value = "optionId")long optionId);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Credit getCredit(long id);
}
