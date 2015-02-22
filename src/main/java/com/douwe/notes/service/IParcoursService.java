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

    public Parcours saveOrUpdateParcours(Parcours parcours);

    public void deleteParcours(Long id);

    public Parcours findParcoursById(long id);

    public List<Parcours> getAllParcours();

}
