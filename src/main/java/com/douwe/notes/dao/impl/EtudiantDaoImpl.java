package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Departement_;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Etudiant_;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Inscription_;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Niveau_;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Option_;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EtudiantDaoImpl extends GenericDao<Etudiant, Long> implements IEtudiantDao{

    @Override
    public List<Etudiant> listeEtudiantParDepartement(Departement departement, AnneeAcademique academique) throws DataAccessException {
        return etudiantsByCriteria(departement, academique, null, null, null);
    }

    @Override
    public List<Etudiant> listeEtudiantParDepartementEtOption(Departement departement, AnneeAcademique academique, Option option) throws DataAccessException {
        return etudiantsByCriteria(departement, academique, null, null, option);
    }

    @Override
    public List<Etudiant> listeEtudiantParDepartementEtNiveau(Departement departement, AnneeAcademique academique, Niveau niveau, Option option) throws DataAccessException {
        return etudiantsByCriteria(departement, academique, null, niveau, option);
    }

    @Override
    public List<Etudiant> listeEtudiantParDepartementEtParcours(Departement departement, AnneeAcademique academique, Parcours parcours) throws DataAccessException {
       return etudiantsByCriteria(departement, academique, parcours, null, null);
    }
    
    private List<Etudiant> etudiantsByCriteria(Departement departement, AnneeAcademique annee, Parcours parcours, Niveau niveau, Option option) throws DataAccessException{
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Etudiant> cq = cb.createQuery(Etudiant.class);
        Root<Inscription> inscriptionRoot = cq.from(Inscription.class);
        Path<AnneeAcademique> anneePath = inscriptionRoot.get(Inscription_.anneeAcademique);
        Path<Parcours> parcoursPath = inscriptionRoot.get(Inscription_.parcours);
        Path<Niveau> niveauPath = parcoursPath.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursPath.get(Parcours_.option);
        Path<Departement> departPath = optionPath.get(Option_.departement);
        Path<Etudiant> etudiantPath = inscriptionRoot.get(Inscription_.etudiant);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(etudiantPath.get(Etudiant_.active), 1));
        if(departement != null){
            predicates.add(cb.equal(departPath, departement));
            predicates.add(cb.equal(departPath.get(Departement_.active), 1));
        }
        if(annee != null){
            predicates.add(cb.equal(anneePath, annee));
            predicates.add(cb.equal(anneePath.get(AnneeAcademique_.active), 1));
        }
        if(parcours != null){
            predicates.add(cb.equal(parcoursPath, parcours));
            predicates.add(cb.equal(parcoursPath.get(Parcours_.active), 1));
        }
        if(niveau != null){
            predicates.add(cb.equal(niveauPath, niveau));
            predicates.add(cb.equal(niveauPath.get(Niveau_.active), 1));
        }
        if(option != null){
            predicates.add(cb.equal(optionPath, option));
            predicates.add(cb.equal(optionPath.get(Option_.active), 1));
        }
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(etudiantPath);
        return getManager().createQuery(cq).getResultList();
    }

    public void deleteActive(Etudiant etudiant) throws DataAccessException {
        getManager().createNamedQuery("Etudiant.deleteActive").setParameter("idParam", etudiant.getId());
    }

    public List<Etudiant> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findAllActive").getResultList();
    }
    
}
