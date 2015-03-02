package com.douwe.notes.service;

import com.douwe.notes.entities.Parcours;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IParcoursService {

    public Parcours saveOrUpdateParcours(Parcours parcours) throws ServiceException;

    public void deleteParcours(Long id) throws ServiceException;

    public Parcours findParcoursById(long id) throws ServiceException;

    public List<Parcours> getAllParcours() throws ServiceException;

}
