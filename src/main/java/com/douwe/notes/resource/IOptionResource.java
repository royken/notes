package com.douwe.notes.resource;

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
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IOptionResource {
    @POST
    @Produces(value = "application/json") 
    Option createOption(Option option);

    @GET
    @Produces(value = "application/json")
    List<Option> getAllOptions();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Option getOption(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Option updateOption(@PathParam(value = "id")long id, Option option);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteOption(@PathParam(value = "id")long id);
    
    
    @GET
    @Path(value = "{id : \\d+}/departement")
    Option getDepartement(@PathParam(value = "id")long id);
    
    @GET
    @Path("{code}")
    @Produces(value = "application/json")
    public Option findByCode(@PathParam(value = "code") String code);
    
    @GET
    @Path("{departementId: \\d+}/{niveauId: \\d+}")
    @Produces(value = "application/json")
    public List<Option> findByDepartementNiveau(@PathParam(value = "departementId")long departementId, @PathParam(value = "niveauId")long niveauId);
}
