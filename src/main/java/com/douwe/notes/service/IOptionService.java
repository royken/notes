package com.douwe.notes.service;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IOptionService {
    
    public Option saveOrUpdateOption(Option option);
    
    public void deleteOption(Long id);
    
    public Option findOptionById(long id);
    
    public List<Option> getAllOptions();
    
    public Departement getDepartement(Option option);
    
}
