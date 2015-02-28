package com.douwe.notes.dao;

import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IEtudiantDao extends IDao<Etudiant, Long>{
    
    // List des étudiants d'un département pour une année académique
    public List<Etudiant> listDepartement(Departement departement, AnneeAcademique academique);
    
    // List des étudiants d'une option du département pour une année académique
    public List<Etudiant> listDepartementOption(Departement departement, AnneeAcademique academique,Option option);
    
    // List des étudiants d'un niveau d'une option du département pour une année académique
    public List<Etudiant> listDepartementNiveau(Departement departement, AnneeAcademique academique, Niveau niveau, Option option);
    
    // List des étudiants d'un parcours d'un département pour une année académique
    public List<Etudiant> listDepartementParcours(Departement departement, AnneeAcademique academique,Parcours parcours);
    
}
