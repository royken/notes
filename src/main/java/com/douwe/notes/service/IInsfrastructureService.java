package com.douwe.notes.service;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.service.impl.InfrastructureServiceImpl;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Local
public interface IInsfrastructureService {

    public Departement saveOrUpdateDepartement(Departement departement);

    public void deleteDepartement(Long id);

    public Departement findDepartementById(long id);

    public List<Departement> getAllDepartements();

    // Les operations en relation avec les cycles
    public Cycle saveOrUpdateCycle(Cycle cycle);

    public void deleteCycle(long id);

    public Cycle findCycleById(long id);

    public List<Cycle> getAllCycles();

    // Les opérations en relation avec les niveaux
    public Niveau saveOrUpdateNiveau(Niveau niveau);

    public void deleteNiveau(long id);

    public Niveau findNiveauById(long id);

    public List<Niveau> getAllNiveaux();

    // Les opérations en relation avec les annees academique
    public AnneeAcademique saveOrUpdateAnneeAcademique(AnneeAcademique anneeAcademique);

    public void deleteAnneeAcademique(Long id);

    public List<AnneeAcademique> getAllAnneeAcademiques();

    public AnneeAcademique findAnneeAcademiqueById(long id);
}
