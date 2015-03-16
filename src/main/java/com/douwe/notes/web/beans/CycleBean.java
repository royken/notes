package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.service.ICycleService;
import com.douwe.notes.service.ServiceException;
import static java.awt.SystemColor.text;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

@Named(value = "cycleBean")
@RequestScoped
public class CycleBean {

    @EJB
    private ICycleService service;
    private Cycle cycle = new Cycle();
    private List<Cycle> cycles;

    public CycleBean() {
    }

    public void saveOrUpdateCycle(ActionEvent actionEvent) throws ServiceException {
        if (cycle != null && cycle.getNom() != null) {
            service.saveOrUpdateCycle(cycle);
            if (cycle.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", cycle.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", cycle.getNom() + " a été enregistré"));
            }

            cycle = new Cycle();
        }
    }

    public void deleteCycle(ActionEvent actionEvent) throws ServiceException {
        if (cycle != null && cycle.getId() != null) {
            service.deleteCycle(cycle.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", cycle.getNom() + " a été supprimé"));
            cycle = new Cycle();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (cycle != null && cycle.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un Cycle avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (cycle != null && cycle.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un Cycle avant de supprimer "));
        }
    }

    public ICycleService getService() {
        return service;
    }

    public void setService(ICycleService service) {
        this.service = service;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public List<Cycle> getCycles() throws ServiceException {
        cycles = service.getAllCycles();
        return cycles;
    }

    public void setCycles(List<Cycle> cycles) {
        this.cycles = cycles;
    }
}
