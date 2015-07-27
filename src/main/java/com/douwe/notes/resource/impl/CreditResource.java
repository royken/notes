package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Credit;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.resource.ICreditResource;
import com.douwe.notes.service.ICreditService;
import com.douwe.notes.service.IParcoursService;
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
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/credits")
public class CreditResource implements ICreditResource {

    @EJB
    private ICreditService creditService;

    @EJB
    private IParcoursService parcoursService;

    public ICreditService getCreditService() {
        return creditService;
    }

    public void setCreditService(ICreditService creditService) {
        this.creditService = creditService;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    @Override
    public Credit createCredit(Credit credit) {
        try {
            return creditService.saveOrUpdateCredit(credit);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public Credit createCredit(long niveauId, long optionId, Credit credit) {
        try {
            Parcours parcours = parcoursService.findByNiveauOption(niveauId, optionId);
            credit.setParcours(parcours);
            return creditService.saveOrUpdateCredit(credit);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public Credit updateCredit(long id, Credit credit) {
        try {
            Credit cred = creditService.findById(id);
            cred.setAcademique(credit.getAcademique());
            cred.setCours(credit.getCours());
            cred.setParcours(credit.getParcours());
            cred.setValeur(credit.getValeur());
            return creditService.saveOrUpdateCredit(credit);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public Credit updateCredit(long niveauId, long optionId, long id, Credit credit) {
        try {
            Parcours parcours = parcoursService.findByNiveauOption(niveauId, optionId);
            Credit cred = creditService.findById(id);
            cred.setAcademique(credit.getAcademique());
            cred.setCours(credit.getCours());
            cred.setParcours(parcours);
            cred.setValeur(credit.getValeur());
            return creditService.saveOrUpdateCredit(credit);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

    }

    @Override
    public void deleteCredit(long id) {
        try {
            creditService.deleteCredit(id);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public List<Credit> getAllCredit() {
        try {
            return creditService.getAll();
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public List<Credit> getAllCredit(long anneeId, long niveauId, long optionId) {
        try {
            return creditService.getAll(anneeId, niveauId, optionId);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public Credit getCredit(long id) {
        try {
            return creditService.findById(id);
        } catch (ServiceException ex) {
            Logger.getLogger(CreditResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

}
