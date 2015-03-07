package com.douwe.notes.resource;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
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
public interface IDepartementResource {

    @POST
    @Produces(value = "application/json")
    Departement createDepartement(Departement dep);

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Departement getDepartement(@PathParam(value = "id") long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Departement updateDepartement(@PathParam(value = "id") long id, Departement depart);

    @GET
    @Produces(value = "application/json")
    List<Departement> getAllDepartement();
    
    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteDepartement(@PathParam(value = "id")long id);
    
    @GET
    @Path(value = "{id : \\d+}/options")
    @Produces(value = "application/json")
    List<Option> getAllOptions(@PathParam(value = "id") long id);
    
    @GET
    @Path(value = "{code}")
    @Produces(value = "application/json")
    public Departement findByCode(@PathParam(value = "code") String code);

}
