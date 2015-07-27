package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.resource.IUniteEnseignementResource;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.IUniteEnseignementService;
import com.douwe.notes.service.ServiceException;
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
@Path("/uniteEns")
public class UniteEnseignementResource implements IUniteEnseignementResource {

    @EJB
    private IUniteEnseignementService service;

    @EJB
    private ICoursService coursService;

    public IUniteEnseignementService getService() {
        return service;
    }

    public void setService(IUniteEnseignementService service) {
        this.service = service;
    }

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }

    @Override
    public UniteEnseignement createUniteEnseignement(UniteEnseignement ue) {
        try {
            return service.saveOrUpdateUe(ue);
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<UniteEnseignement> getAllUniteEnseignements() {
        try {
            return service.getAllUniteEnseignements();
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public UniteEnseignement getUniteEnseignement(long id) {
        try {
            UniteEnseignement enseignement = service.findUniteEnseignementById(id);
            if (enseignement == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return enseignement;
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public UniteEnseignement updateSemestre(long id, UniteEnseignement ue) {
        try {
            UniteEnseignement enseignement = service.findUniteEnseignementById(id);
            if (enseignement != null) {
                enseignement.setCode(ue.getCode());
                enseignement.setIntitule(ue.getIntitule());
                return service.saveOrUpdateUe(enseignement);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteUniteEns(long id) {
        try {
            service.deleteUniteEnseignement(id);
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cours> findAllCoursByUe(long id) {
        try {
            return coursService.findAllByUe(id);
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public List<UniteEnseignement> findByParcours(long niveauId, long optionId) {
        try {
            return service.findByParcours(niveauId, optionId);
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public UniteEnseignement addUniteEnseignement(Long niveauId, Long optionId, UniteEnseignement ue) {
        try {
            return service.addUniteEnseignement(niveauId, optionId, ue);
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public UniteEnseignement miseAJourUniteEnseignement(Long niveauId, Long optionId, Long id, UniteEnseignement ue) {
        try {
            if (id != null) {

                UniteEnseignement nue = service.findUniteEnseignementById(id);
                if (nue != null) {
                    nue.setCode(ue.getCode());
                    nue.setHasOptionalChoices(ue.isHasOptionalChoices());
                    nue.setIntitule(ue.getIntitule());
                    nue.setActive(1);
                    nue.setCours(ue.getCours());
                    return service.addUniteEnseignement(niveauId, optionId, nue);
                }
            }
        } catch (ServiceException ex) {
            Logger.getLogger(UniteEnseignementResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

}
