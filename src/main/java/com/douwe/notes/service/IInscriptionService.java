package com.douwe.notes.service;

import com.douwe.notes.entities.Inscription;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IInscriptionService {
    
    public Inscription saveOrUpdateInscription(Inscription inscription);
    
    public void deleteInscription(long id);
    
    public Inscription findInscriptionById(long id);
    
    public List<Inscription> getAllInscriptions();
    
}
