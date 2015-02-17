package com.douwe.notes.resource.impl;

import com.douwe.notes.entities.Option;
import com.douwe.notes.resource.IOptionResource;
import com.douwe.notes.service.IOptionService;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    public Option createOption(Option option) {
        return optionService.saveOrUpdateOption(option);
    }

    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    public Option getOption(long id) {
        Option option = optionService.findOptionById(id);
        if (option == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return option;
    }

    public Option updateOption(long id, Option option) {
       Option option1 = optionService.findOptionById(id);
        if(option1 != null){
            option1.setCode(option.getCode());
            option1.setDescription(option.getDescription());
            return optionService.saveOrUpdateOption(option1);
        }
        return null;
    }

    public void deleteOption(long id) {
        optionService.deleteOption(id);
    }

    public Option getDepartement(long id) {
        Option option = optionService.findOptionById(id);
        if(option != null){
            optionService.getDepartement(option);
        }
        return null;
    }   
}
