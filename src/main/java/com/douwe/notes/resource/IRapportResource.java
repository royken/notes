package com.douwe.notes.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/rapport")

public interface IRapportResource {

    @GET
    @Produces("text/pdf")
    Response test() throws Exception;

    @GET
    @Path(value = "pv/{niveauid : \\d+}/{optionid : \\d+}/{coursid : \\d+}/{anneeid : \\d+}/{session : \\d+}")
    @Produces("text/pdf")
    Response produirePv(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "coursid") long coursid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "session") int session);

    @GET
    @Path(value = "synthese/semestre/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}/{semestre : \\d+}")
    @Produces("text/pdf")
    Response produireSyntheseSemestrielle(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "semestre") long semestreid);

    @GET
    @Path(value = "synthese/annuelle/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}")
    @Produces("text/pdf")
    Response produireSyntheseAnnuelle(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid);

    @GET
    @Path(value = "relevet/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}")
    @Produces("text/pdf")
    Response produireRelevet(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid);

    @GET
    @Path(value = "synthese/annual")
    @Produces("text/pdf")
    Response produire();
}
