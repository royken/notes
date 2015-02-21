package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.ITypeCoursDao;
import com.douwe.notes.entities.TypeCours;
import com.douwe.notes.service.ITypeCoursService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class TypeCourServiceImpl implements ITypeCoursService{
    
    @Inject
    private ITypeCoursDao typeCoursDao;

    public ITypeCoursDao getTypeCoursDao() {
        return typeCoursDao;
    }

    public void setTypeCoursDao(ITypeCoursDao typeCoursDao) {
        this.typeCoursDao = typeCoursDao;
    }
    
    

    public TypeCours saveOrUpdateTpyeCours(TypeCours typeCours) {
        try {
            if (typeCours.getId() == null) {
                return typeCoursDao.create(typeCours);
            } else {
                return typeCoursDao.update(typeCours);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(DepartementServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            return null;
        }
    }

    public void deleteTypeCours(Long id) {
        try {
            TypeCours typeCours = typeCoursDao.findById(id);
            typeCoursDao.delete(typeCours);
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TypeCours findTypeCoursById(long id) {
        try {
            return typeCoursDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<TypeCours> getAllTypeCours() {
        try {
            return typeCoursDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(TypeCourServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
