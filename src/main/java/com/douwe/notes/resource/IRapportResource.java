/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
