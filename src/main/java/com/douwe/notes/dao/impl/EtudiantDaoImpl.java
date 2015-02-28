package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEtudiantDao;
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
public class EtudiantDaoImpl extends GenericDao<Etudiant, Long> implements IEtudiantDao{

    public List<Etudiant> listDepartement(Departement departement, AnneeAcademique academique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Etudiant> listDepartementOption(Departement departement, AnneeAcademique academique, Option option) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Etudiant> listDepartementNiveau(Departement departement, AnneeAcademique academique, Niveau niveau, Option option) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Etudiant> listDepartementParcours(Departement departement, AnneeAcademique academique, Parcours parcours) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
