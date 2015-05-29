package com.douwe.notes.resource;

import com.douwe.notes.entities.Note;
import com.douwe.notes.projection.EtudiantNotes;
import com.douwe.notes.projection.MoyenneUniteEnseignement;
import com.douwe.notes.service.util.ImportationResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
    Note getNote(@PathParam(value = "id") long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Note updateNote(@PathParam(value = "id") long id, Note note);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteNote(@PathParam(value = "id") long id);

    @POST
    @Path("import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ImportationResult importNotes(@FormDataParam("fichier") InputStream fichier, @FormDataParam("fichier") FormDataContentDisposition fileDisposition, @FormDataParam("courId") Long coursId, @FormDataParam("evaluationId") Long evaluationId, @FormDataParam("anneeId") Long anneeId, @DefaultValue("0") @FormDataParam("session") int session);
    // Debut mes tests. A supprimer a la fin bien sur
    @GET
    @Path(value = "{niveauid : \\d+}/{optionid : \\d+}/{coursid : \\d+}/{anneeid : \\d+}/{session : \\d+}")
    @Produces(value = "application/json")
    String afficher(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "coursid") long coursid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "session") int session);
    
    @GET
    @Path(value = "/toto")
    @Produces(value = "application/json")
    OutputStream produirePv();
    
    @GET
    @Path(value = "/salut/{matricule}/{coursId: \\d+}")
    @Produces(value = "application/json")
    EtudiantNotes noteEtudiant(@PathParam(value = "matricule")String matricule, @PathParam(value = "coursId")long coursId);
    
    @GET
    @Path(value = "/bonsoir/{matricule}/{ueId: \\d+}/{annee: \\d+}")
    @Produces(value = "application/json")
    MoyenneUniteEnseignement moyenneEtudiant(@PathParam(value = "matricule")String matricule, @PathParam(value = "ueId")long ueId, @PathParam(value = "annee")long annee);
    
    @GET
    @Path(value = "/cafe/{matricule}/{niveauId: \\d+}/{optionId: \\d+}/{semestreId: \\d+}/{anneeId: \\d+}")
    @Produces(value = "application/json")
    Map<String, MoyenneUniteEnseignement> listeNoteUniteEnseignement(@PathParam(value = "matricule")String matricule, @PathParam(value = "niveauId")long niveauId, @PathParam(value = "optionId")long optionId, @PathParam(value = "semestreId")long semestreId, @PathParam(value = "anneeId")long anneeId);
    // fin mes tests. 
}
