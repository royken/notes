package com.douwe.notes.resource;

import com.douwe.notes.entities.Note;
import java.io.OutputStream;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/notes")
public interface INoteResource {
    
    @POST
    @Produces(value = "application/json") 
    Note createNote(Note note);

    @GET
    @Produces(value = "application/json")
    List<Note> getAllNotes();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Note getNote(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Note updateNote(@PathParam(value = "id")long id, Note note);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteNote(@PathParam(value = "id")long id);
    
    /*@GET
    @Path(value = "{niveauid : \\d+}/{optionid : \\d+}/{coursid : \\d+}/{anneeid : \\d+}/{session : \\d+}")
            @Produces(value = "application/json")
    String afficher(@PathParam(value = "niveauid")long niveauid, @PathParam(value = "optionid")long optionid, @PathParam(value = "coursid")long coursid, @PathParam(value = "anneeid")long anneeid, @PathParam(value = "session")int session);
    */
    @GET
    @Path(value = "/toto")
            @Produces(value = "application/json")
    OutputStream produirePv();
    
    
}
