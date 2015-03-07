package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Option;
import com.douwe.notes.resource.IOptionResource;
import com.douwe.notes.service.IOptionService;
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
@Path("/options")
public class OptionResource implements IOptionResource{
    
    @EJB
    private IOptionService optionService;

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public Option createOption(Option option) {
        try {
            return optionService.saveOrUpdateOption(option);
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Option> getAllOptions() {
        try {
            return optionService.getAllOptions();
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Option getOption(long id) {
        try {
            Option option = optionService.findOptionById(id);
            if (option == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return option;
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Option updateOption(long id, Option option) {
        try {
            Option option1 = optionService.findOptionById(id);
            if(option1 != null){
                option1.setCode(option.getCode());
                option1.setDescription(option.getDescription());
                return optionService.saveOrUpdateOption(option1);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteOption(long id) {
        try {
            optionService.deleteOption(id);
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Option getDepartement(long id) {
        try {
            Option option = optionService.findOptionById(id);
            if(option != null){
                optionService.getDepartement(option);
            }
            return null;
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }   

    @Override
    public Option findByCode(String code) {
        try {
            Option option = optionService.findByCode(code);
            if(option == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return option;
        } catch (ServiceException ex) {
            Logger.getLogger(OptionResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
