package com.douwe.notes.service.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Genre;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.ServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class EtudiantServiceImpl implements IEtudiantService {

    @Inject
    private IEtudiantDao etudiantDao;
    
    @Inject
    private IDepartementDao departementDao;
    
    @Inject
    private IOptionDao optionDao;
    
    @Inject
    private IAnneeAcademiqueDao anneeAcademiqueDao;
    
    @Inject
    private INiveauDao niveauDao;

    @EJB
    private IInscriptionService inscriptionService;

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    public IAnneeAcademiqueDao getAnneeAcademiqueDao() {
        return anneeAcademiqueDao;
    }

    public void setAnneeAcademiqueDao(IAnneeAcademiqueDao anneeAcademiqueDao) {
        this.anneeAcademiqueDao = anneeAcademiqueDao;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }
    

    @Override
    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant) throws ServiceException {
        try {
            if (etudiant.getId() == null) {
                etudiant.setActive(1);
                return etudiantDao.create(etudiant);
            } else {
                return etudiantDao.update(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void deleteEtudiant(Long id) throws ServiceException {
        try {
            Etudiant etudiant = etudiantDao.findById(id);
            if (etudiant != null) {
                etudiant.setActive(0);
                etudiantDao.update(etudiant);
            }
        } catch (DataAccessException dae) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, dae);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public Etudiant findEtudiantById(long id) throws ServiceException {
        try {
            return etudiantDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Etudiant> getAllEtudiant() throws ServiceException {
        try {
            return etudiantDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public List<Etudiant> findByCritiria(long departementId, long anneeId, long niveauId, long optionId) {
        try {
            Departement departement = null;
            AnneeAcademique annee = null;
            Niveau niveau = null;
            Option option = null;
            if(departementId > 0)
                departement = departementDao.findById(departementId);
            if (anneeId > 0)
                annee = anneeAcademiqueDao.findById(anneeId);
            if(niveauId > 0)
                niveau = niveauDao.findById(niveauId);
            if (optionId > 0)
                option = optionDao.findById(optionId);
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
            throw new ServiceException("La ressource demandée est introuvable");
        }
    }

    @Override
    public void importEtudiants(InputStream stream, Long idAnneeAcademique) throws ServiceException {
        try {
            Workbook workbook = WorkbookFactory.create(stream);
            final Sheet sheet = workbook.getSheetAt(0);
            int index = 1;
            Row row = sheet.getRow(index++);
            while (row != null) {
                Etudiant etudiant = new Etudiant();
                etudiant.setMatricule(row.getCell(1).getStringCellValue());
                etudiant.setNom(row.getCell(2).getStringCellValue());
                etudiant.setDateDeNaissance(row.getCell(3).getDateCellValue());
                etudiant.setLieuDeNaissance(row.getCell(4).getStringCellValue());
                if (row.getCell(5) != null) {
                    etudiant.setEmail(row.getCell(5).getStringCellValue());
                }
                if (row.getCell(6) != null) {
                    etudiant.setNumeroTelephone(row.getCell(6).getNumericCellValue()+"");
                }
                etudiant.setGenre(Genre.valueOf(row.getCell(7).getStringCellValue().toLowerCase()));
                String niveau = row.getCell(10).getStringCellValue();
                String option = row.getCell(11).getStringCellValue();
                etudiant.setActive(1);
                try{
                    inscriptionService.inscrireEtudiant(etudiant, niveau, option, idAnneeAcademique);
                }catch(Exception ex){
                
                    System.out.println("L'étudiant " + index + "n'a pas été enregistré");
                }
                row = sheet.getRow(index++);
            }
        } catch (IOException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(EtudiantServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public IInscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(IInscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

}
