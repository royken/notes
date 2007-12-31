/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.Cycle;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author root
 */
@Named(value = "cycleBean")
@RequestScoped
public class CycleBean {

    @EJB
    private IInsfrastructureService service;
    private Cycle cycle = new Cycle();
    private List<Cycle> cycles;

    public CycleBean() {
    }

    public List<Cycle> findAll() {
        return service.getAllCycles();
    }
    public void saveOrUpdateCycles() {
        if (cycle != null) {
            service.saveOrUpdateCycle(cycle);
            cycle = new Cycle();
        }
    }

    public void deleteCycle() {
        if (cycle != null && cycle.getId() > 0) {
            service.deleteCycle(cycle.getId());
            cycle = new Cycle();
        }
    }
    public IInsfrastructureService getService() {
        return service;
    }

    public void setService(IInsfrastructureService service) {
        this.service = service;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public List<Cycle> getCycles() {
        cycles=findAll();
        return cycles;
    }

    public void setCycles(List<Cycle> cycles) {
        this.cycles = cycles;
    }
    

}
