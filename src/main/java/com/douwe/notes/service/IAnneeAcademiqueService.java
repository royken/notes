package com.douwe.notes.service;

import com.douwe.notes.entities.AnneeAcademique;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IAnneeAcademiqueService {
    public AnneeAcademique saveOrUpdateAnnee(AnneeAcademique anneeAcademique);
    
    public void deleteAnnee(Long id);
    
    public AnneeAcademique findAnneeById(long id);
    
    public List<AnneeAcademique> getAllAnnee();
}
