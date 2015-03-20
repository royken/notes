package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.resource.IEtudiantResource;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Override
    public Etudiant createEtudiant(Etudiant etudiant) {
        try {
            return etudiantService.saveOrUpdateEtudiant(etudiant);
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        try {
            return etudiantService.getAllEtudiant();
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Etudiant getEtudiant(long id) {
        try {
            Etudiant etudiant = etudiantService.findEtudiantById(id);
            if(etudiant == null){
                throw  new  WebApplicationException(Response.Status.NOT_FOUND);
            }
            return etudiant;
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
            return  null;
        }
    }

    @Override
    public Etudiant updateEtudiant(long id, Etudiant etudiant) {
        try {
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
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteEtudiant(long id) {
        try {
            etudiantService.deleteEtudiant(id);
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Etudiant findByMatricule(String matricule) {
        try {
            Etudiant etudiant = etudiantService.findByMatricule(matricule);
            
            if(etudiant == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return etudiant;
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

//    @Override
//    public void importEtudiant(InputStream stream, Long idAnne) {
//        try {
//            etudiantService.importEtudiants(stream, idAnne);
//        } catch (ServiceException ex) {
//            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }
//    }


    @Override
    public List<Etudiant> listeInscrit(long departementId, long anneeId, long niveauId, long optionId) {
        try {
            return etudiantService.findByCritiria(departementId, anneeId, niveauId, optionId);
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
}