package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Niveau;
import com.douwe.notes.resource.INiveauResource;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/niveaux")
public class NiveauResource implements INiveauResource {

    @Inject
    private IInsfrastructureService insfrastructureService;

    @EJB
    private INiveauService service;

    public INiveauService getService() {
        return service;
    }

    public void setService(INiveauService service) {
        this.service = service;
    }

    @Override
    public Niveau createNiveau(Niveau niveau) {
        try {
            return service.saveOrUpdateNiveau(niveau);
        } catch (ServiceException ex) {
            Logger.getLogger(NiveauResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Niveau> getAllNiveaux() throws ServiceException {
        return service.getAllNiveaux();
    }

    @Override
    public Niveau getNiveau(long id) {
        try {
            return service.findNiveauById(id);
        } catch (ServiceException ex) {
            Logger.getLogger(NiveauResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Niveau updateNiveau(long id, Niveau niveau) {
        try {
            Niveau n = service.findNiveauById(id);
            if (n != null) {
                n.setCode(niveau.getCode());
                n.setCycle(niveau.getCycle());
                return service.saveOrUpdateNiveau(n);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(NiveauResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteNiveau(long id) {
        try {
            service.deleteNiveau(id);
        } catch (ServiceException ex) {
            Logger.getLogger(NiveauResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public IInsfrastructureService getInsfrastructureService() {
        return insfrastructureService;
    }

    public void setInsfrastructureService(IInsfrastructureService insfrastructureService) {
        this.insfrastructureService = insfrastructureService;
    }

}
