package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.ServiceException;
import java.io.InputStream;
import java.util.Collections;
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
public class EtudiantServiceImpl implements IEtudiantService{
    
    @Inject
    private IEtudiantDao etudiantDao;

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }
    
    


    @Override
    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant) throws ServiceException{
        try {
            if (etudiant.getId() == null) {
                etudiant.setActive(1);
                return etudiantDao.create(etudiant);
            } else {
                return etudiantDao.update(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public void deleteEtudiant(Long id) throws ServiceException{
        try {
            Etudiant etudiant = etudiantDao.findById(id);
            if (etudiant != null) {
                etudiant.setActive(0);
                etudiantDao.update(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public Etudiant findEtudiantById(long id) throws ServiceException{
        try {
            return etudiantDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }


    @Override
    public List<Etudiant> getAllEtudiant() throws ServiceException{
        try {
            return etudiantDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
           throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Etudiant> findByCritiria(Departement departement, AnneeAcademique annee, Niveau niveau, Option option) {
        try {
            return etudiantDao.listeEtudiantParDepartementEtNiveau(departement, annee, niveau, option);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Etudiant findByMatricule(String matricule) throws ServiceException {
        try {
            return etudiantDao.findByMatricule(matricule);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void importEtudiants(InputStream stream, AnneeAcademique academique) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
