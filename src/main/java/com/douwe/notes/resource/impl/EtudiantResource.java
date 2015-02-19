package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.resource.IEtudiantResource;
import com.douwe.notes.service.IEtudiantService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/etudiants")
public class EtudiantResource implements IEtudiantResource{
    
    @EJB
    private IEtudiantService etudiantService;

    public IEtudiantService getEtudiantService() {
        return etudiantService;
    }

    public void setEtudiantService(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantService.saveOrUpdateEtudiant(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiant();
    }

    public Etudiant getEtudiant(long id) {
        Etudiant etudiant = etudiantService.findEtudiantById(id);
        if(etudiant == null){
            throw  new  WebApplicationException(Response.Status.NOT_FOUND);
        }
        return etudiant;
    }

    public Etudiant updateEtudiant(long id, Etudiant etudiant) {
        Etudiant etud = etudiantService.findEtudiantById(etudiant.getId());
        if(etud != null){
            etud.setDateDeNaissance(etudiant.getDateDeNaissance());
            etud.setEmail(etudiant.getEmail());
            etud.setGenre(etudiant.getGenre());
            etud.setLieuDeNaissance(etudiant.getLieuDeNaissance());
            etud.setMatricule(etudiant.getMatricule());
            etud.setNom(etudiant.getNom());
            etud.setNumeroTelephone(etudiant.getNumeroTelephone());
            return etudiantService.saveOrUpdateEtudiant(etud);
        }
        return null;
    }

    public void deleteEtudiant(long id) {
            etudiantService.deleteEtudiant(id);
    }
}