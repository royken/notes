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
    
    public Inscription saveOrUpdateInscription(Inscription inscription) throws ServiceException;
    
    public void deleteInscription(long id) throws ServiceException;
    
    public Inscription findInscriptionById(long id) throws ServiceException;
    
    public List<Inscription> getAllInscriptions() throws ServiceException;
    
}
