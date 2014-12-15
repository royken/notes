package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.service.IInsfrastructureService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Stateless
public class InfrastructureServiceImpl implements IInsfrastructureService {

    @Resource
    private IDepartementDao departementDao;

    public Departement saveOrUpdateDepartement(Departement departement) {
        try {
            if (departement.getId() == null) {
                return departementDao.create(departement);
            } else {
                return departementDao.update(departement);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(InfrastructureServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteDepartement(Long id) {
        try{
        Departement departement = departementDao.findById(id);
        if(departement != null)
            departementDao.delete(departement);
        }catch(DataAccessException dae){
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

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }
}
