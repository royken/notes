package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICycleDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cycle;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Stateless
public class InfrastructureServiceImpl implements IInsfrastructureService {

    @Inject
    private IDepartementDao departementDao;

    @Inject
    private ICycleDao cycleDao;

    @Inject
    private INiveauDao niveauDao;
    
    @Inject
    private IAnneeAcademiqueDao anneeAcademiqueDao;

    public Departement saveOrUpdateDepartement(Departement departement) {
        System.out.println("Exécution de la methode saveOrUpdate");
        try {
            if (departement.getId() == null) {
                System.out.println("Exécution de la methode save");
                return departementDao.create(departement);
            } else {
                System.out.println("Exécution de la methode update");
                return departementDao.update(departement);                              
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteDepartement(Long id) {
        try {
            Departement departement = departementDao.findById(id);
            if (departement != null) {
                departementDao.delete(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public List<Departement> getAllDepartements() {
        try {
            return departementDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        public AnneeAcademique saveOrUpdateAnneeAcademique(AnneeAcademique anneeAcademique) {        
        try {
            if (anneeAcademique.getId() == null) {                
                return anneeAcademiqueDao.create(anneeAcademique);
            } else {                
                return anneeAcademiqueDao.update(anneeAcademique);                              
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteAnneeAcademique(Long id) {
        try {
            AnneeAcademique anneeAcademique = anneeAcademiqueDao.findById(id);            
            if (anneeAcademique != null) {
                anneeAcademiqueDao.delete(anneeAcademique);                
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
        }
    }

    public List<AnneeAcademique> getAllAnneeAcademiques() {
        try {
            return anneeAcademiqueDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public AnneeAcademique findAnneeAcademiqueById(long id) {
        try {
            return anneeAcademiqueDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public ICycleDao getCycleDao() {
        return cycleDao;
    }

    public void setCycleDao(ICycleDao cycleDao) {
        this.cycleDao = cycleDao;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public Departement findDepartementById(long id) {
        try {
            return departementDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cycle saveOrUpdateCycle(Cycle cycle) {
        try {
            if (cycle.getId() == null) {
                return cycleDao.create(cycle);
            } else {
                return cycleDao.update(cycle);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteCycle(long id) {
        try {
            Cycle c = cycleDao.findById(id);
            if (c != null) {
                cycleDao.delete(c);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cycle findCycleById(long id) {
        try {
            return cycleDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Cycle> getAllCycles() {
        try {
            return cycleDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    public Niveau saveOrUpdateNiveau(Niveau niveau) {
        try {
            if (niveau.getId() == null) {
                return niveauDao.create(niveau);
            } else {
                return niveauDao.update(niveau);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteNiveau(long id) {
        try {
            Niveau n = niveauDao.findById(id);
            if (n != null)
                niveauDao.delete(n);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Niveau findNiveauById(long id) {
        try {
            return niveauDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Niveau> getAllNiveaux() {
        try {
            return niveauDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public IAnneeAcademiqueDao getAnneeAcademiqueDao() {
        return anneeAcademiqueDao;
    }

    public void setAnneeAcademiqueDao(IAnneeAcademiqueDao anneeAcademiqueDao) {
        this.anneeAcademiqueDao = anneeAcademiqueDao;
    }    
}
