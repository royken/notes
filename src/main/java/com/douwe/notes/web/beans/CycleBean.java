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
    private String message;

    /**
     * Creates a new instance of CycleBean
     */
    public CycleBean() {        
        message="";
    }


    public String saveOrUpdateCycle() throws ServiceException {
        if (cycle != null) {            
            service.saveOrUpdateCycle(cycle);
            cycle = new Cycle();                      
        }
        return "saveOrUpdateCycle";
    }

    public String deleteCycle() throws ServiceException {
        if (cycle != null) {          
            message = "Suppression reussi de "+cycle.getNom();
            service.deleteCycle(cycle.getId());
            cycle = new Cycle();            
        }
        return "deleteCycle";
    }

    public String choix(int n) {
        if (n == 1) {
            cycle=new Cycle();
            message="Enregistrement reussi ";
            return "saveCycle";
        }

        else if (n == 2 && cycle!=null &&cycle.getVersion() >= 1) {
            message="Mise à jour reussi ";
            return "updateCycle";
        }
        cycle = new Cycle();        
        return "cycle";
    }
    public void notification(ActionEvent actionEvent) {  
        FacesContext context = FacesContext.getCurrentInstance();            
        context.addMessage(null, new FacesMessage("Succes", message));          
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
