package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Session;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEtudiantDao extends IDao<Etudiant, Long>{
    
    public void deleteActive(Etudiant etudiant) throws DataAccessException;
    
    public List<Etudiant> findAllActive()throws DataAccessException;
    
    public Etudiant findByMatricule(String matricule) throws DataAccessException;
    
    public Etudiant findByName(String name) throws DataAccessException;
    
    // List des étudiants d'un département pour une année académique
    public List<Etudiant> listeEtudiantParDepartement(Departement departement, AnneeAcademique academique) throws DataAccessException;
    
    // List des étudiants d'une option du département pour une année académique
    public List<Etudiant> listeEtudiantParDepartementEtOption(Departement departement, AnneeAcademique academique,Option option) throws DataAccessException;
    
    // List des étudiants d'un niveau d'une option du département pour une année académique
    public List<Etudiant> listeEtudiantParDepartementEtNiveau(Departement departement, AnneeAcademique academique, Niveau niveau, Option option) throws DataAccessException;
    
    // List des étudiants d'un parcours d'un département pour une année académique
    // TODO Je crois que le departement ne sert à rien parce que le parcours a deja le departement
    public List<Etudiant> listeEtudiantParDepartementEtParcours(Departement departement, AnneeAcademique academique,Parcours parcours) throws DataAccessException;
    
    public List<Etudiant> listeEtudiantInscritParcours(AnneeAcademique academique, Parcours parcours) throws DataAccessException;
    
    public List<Etudiant> listeEtudiantAvecNotes(AnneeAcademique academique, Niveau niveau, Option option,  Cours cours, Session session) throws DataAccessException;
    
    public List<Etudiant> listeEtudiantAvecNotes(AnneeAcademique academique, Niveau niveau, Option option, Semestre semestre) throws DataAccessException;
    
    public List<Etudiant> listeEtudiantAvecNotes(AnneeAcademique debutInscription,AnneeAcademique encours, Niveau niveau, Option option, Semestre semestre) throws DataAccessException;
    
}
