package com.douwe.notes.resource;

import com.douwe.notes.entities.Etudiant;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IEtudiantResource {
    
    @POST
    @Produces(value = "application/json") 
    Etudiant createEtudiant(Etudiant etudiant);

    @GET
    @Produces(value = "application/json")
    List<Etudiant> getAllEtudiants();

    @GET
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Etudiant getEtudiant(@PathParam(value = "id")long id);

    @PUT
    @Path(value = "{id : \\d+}")
    @Produces(value = "application/json")
    Etudiant updateEtudiant(@PathParam(value = "id")long id, Etudiant etudiant);

    @DELETE
    @Path(value = "{id : \\d+}")
    void deleteEtudiant(@PathParam(value = "id")long id);
    
    @GET
    @Path(value = "{matricule}")
    @Produces(value = "application/json")
    public Etudiant findByMatricule(@PathParam(value = "id") String matricule);
    

    
    @GET
    @Path("inscrits")
    @Produces(value = "application/json")
    List<Etudiant> listeInscrit(@DefaultValue("-1") @QueryParam("departementId") long departementId, 
            @DefaultValue("-1") @QueryParam("anneeId") long anneeId, 
            @DefaultValue("-1") @QueryParam("niveauId") long niveauId, 
            @DefaultValue("-1") @QueryParam("optionId") long optionId);
    
//    @GET
//    @Path("import/{file}-{annee:\\d+}")
//    public void importEtudiant(@PathParam(value = "file") InputStream stream, @PathParam(value = "annee") Long idAnne);

    
}
