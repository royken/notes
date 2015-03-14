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
            FacesContext context = FacesContext.getCurrentInstance();
            if (cycle.getId() == null) {                
                context.addMessage(null, new FacesMessage("Operation reussie", cycle.getNom() + "a été mis à jour "));                                
            } else {
                context.addMessage(null, new FacesMessage("Operation reussie", cycle.getNom() + "a été enregistré"));
            }

            cycle = new Cycle();
        }
    }

    public void deleteCycle(ActionEvent actionEvent) throws ServiceException {
        if (cycle != null) {
            System.out.println("delete --- " + cycle);
            String message = cycle.getNom();
            service.deleteCycle(cycle.getId());
            
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, new FacesMessage("Operation reussie", message + " a été supprimé"));
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Operation reussie", "a été supprimé"));
            cycle = new Cycle();
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
