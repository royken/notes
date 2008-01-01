package com.douwe.notes.service;

import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.TypeCours;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ITypeCoursService {
    
    public TypeCours saveOrUpdateTpyeCours(TypeCours typeCours);
    
    public void deleteTypeCours(Long id);
    
    public TypeCours findTypeCoursById(long id);
    
    public List<TypeCours> getAllTypeCours();
    
}
