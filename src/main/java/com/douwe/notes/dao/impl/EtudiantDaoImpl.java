package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.CoursUEAnnee;
import com.douwe.notes.entities.CoursUEAnnee_;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Departement_;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Etudiant_;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Evaluation_;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Inscription_;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Niveau_;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Note_;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Option_;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Programme_;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.entities.UniteEnseignement_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EtudiantDaoImpl extends GenericDao<Etudiant, Long> implements IEtudiantDao {

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

    private List<Etudiant> etudiantsByCriteria(Departement departement, AnneeAcademique annee, Parcours parcours, Niveau niveau, Option option) throws DataAccessException {
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
        if (departement != null) {
            predicates.add(cb.equal(departPath, departement));
            predicates.add(cb.equal(departPath.get(Departement_.active), 1));
        }
        if (annee != null) {
            predicates.add(cb.equal(anneePath, annee));
            predicates.add(cb.equal(anneePath.get(AnneeAcademique_.active), 1));
        }
        if (parcours != null) {
            predicates.add(cb.equal(parcoursPath, parcours));
            predicates.add(cb.equal(parcoursPath.get(Parcours_.active), 1));
        }
        if (niveau != null) {
            predicates.add(cb.equal(niveauPath, niveau));
            predicates.add(cb.equal(niveauPath.get(Niveau_.active), 1));
        }
        if (option != null) {
            predicates.add(cb.equal(optionPath, option));
            predicates.add(cb.equal(optionPath.get(Option_.active), 1));
        }
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(etudiantPath);
        cq.orderBy(cb.asc(etudiantPath.get(Etudiant_.nom)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public void deleteActive(Etudiant etudiant) throws DataAccessException {
        getManager().createNamedQuery("Etudiant.deleteActive").setParameter("idParam", etudiant.getId());
    }

    @Override
    public List<Etudiant> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findAllActive").getResultList();
    }

    @Override
    public Etudiant findByMatricule(String matricule) throws DataAccessException {
        try {
            return (Etudiant) (getManager().createNamedQuery("Etudiant.findByMatricule").setParameter("param", matricule).getSingleResult());
        } catch (NoResultException nre) {

        }
        return null;
    }

    @Override
    public Etudiant findByName(String name) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Etudiant> cq = cb.createQuery(Etudiant.class);
        Root<Etudiant> etudiantRoot = cq.from(Etudiant.class);
        cq.where(cb.and(cb.like(etudiantRoot.get(Etudiant_.nom), name),cb.equal(etudiantRoot.get(Etudiant_.active), 1)));
        cq.select(etudiantRoot);
        return getManager().createQuery(cq).getSingleResult();

    }

    @Override
    public List<Etudiant> listeEtudiantInscritParcours(AnneeAcademique academique, Parcours parcours) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Etudiant> cq = cb.createQuery(Etudiant.class);
        Root<Inscription> inscriptionRoot = cq.from(Inscription.class);
        Root<Inscription> inscriptionRoot2 = cq.from(Inscription.class);
        Path<Etudiant> etudiantPath = inscriptionRoot.get(Inscription_.etudiant);
        Path<AnneeAcademique> anneePath = inscriptionRoot.get(Inscription_.anneeAcademique);  
        Path<Parcours> parcoursPath = inscriptionRoot2.get(Inscription_.parcours);
        // L'etudiant est inscrit en ce moment
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.lessThanOrEqualTo(anneePath.get(AnneeAcademique_.debut), academique.getDebut()));
        predicates.add(cb.equal(parcoursPath, parcours));
        predicates.add(cb.equal(etudiantPath, inscriptionRoot2.get(Inscription_.etudiant)));
        predicates.add(cb.equal(inscriptionRoot2.get(Inscription_.anneeAcademique),academique));        
        cq.select(etudiantPath);
        cq.distinct(true);
        cq.orderBy(cb.asc(etudiantPath.get(Etudiant_.nom)));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
               
    }

    @Override
    public List<Etudiant> listeEtudiantAvecNotes(AnneeAcademique academique, Niveau niveau, Option option, Cours cours, Session session) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Etudiant> cq = cb.createQuery(Etudiant.class);
        Root<Note> noteRoot = cq.from(Note.class);
        Path<Evaluation> evaluationPath = noteRoot.get(Note_.evaluation);
        Root<Inscription> inscriptionRoot = cq.from(Inscription.class);
        Root<Inscription> inscriptionRoot2 = cq.from(Inscription.class);
        Path<Etudiant> etudiantPath = noteRoot.get(Note_.etudiant);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(etudiantPath, inscriptionRoot.get(Inscription_.etudiant)));
        predicates.add(cb.equal(etudiantPath, inscriptionRoot2.get(Inscription_.etudiant)));
        predicates.add(cb.equal(inscriptionRoot2.get(Inscription_.parcours).get(Parcours_.niveau), niveau));
        predicates.add(cb.equal(inscriptionRoot2.get(Inscription_.parcours).get(Parcours_.option), option));
        predicates.add(cb.lessThanOrEqualTo(inscriptionRoot2.get(Inscription_.anneeAcademique).get(AnneeAcademique_.debut),academique.getDebut()));
        predicates.add(cb.equal(inscriptionRoot.get(Inscription_.anneeAcademique),academique));
        predicates.add(cb.equal(noteRoot.get(Note_.cours),cours));
        predicates.add(cb.equal(noteRoot.get(Note_.anneeAcademique),academique));
        if(session == Session.rattrapage){
            predicates.add(cb.isTrue(evaluationPath.get(Evaluation_.isExam)));
            predicates.add(cb.equal(noteRoot.get(Note_.session), session));
        }
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.distinct(true);
        cq.orderBy(cb.asc(etudiantPath.get(Etudiant_.nom)));
        cq.select(etudiantPath);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Etudiant> listeEtudiantAvecNotes(AnneeAcademique academique, Niveau niveau, Option option, Semestre semestre) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Etudiant> cq = cb.createQuery(Etudiant.class);
        Root<Note> noteRoot = cq.from(Note.class);
        Root<Cours> coursRoot = cq.from(Cours.class);
        //Root<CoursUEAnnee> coursUeRoot = cq.from(CoursUEAnnee.class);        
        Root<Inscription> inscriptionRoot = cq.from(Inscription.class);
        Root<Inscription> inscriptionRoot2 = cq.from(Inscription.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Path<Evaluation> evaluationPath = noteRoot.get(Note_.evaluation);
        Path<UniteEnseignement> unitePath = programmeRoot.get(Programme_.uniteEnseignement);
        Expression<List<Cours>> coursPath = unitePath.get(UniteEnseignement_.cours);
        //Expression<List<Cours>> totoPath = programmeRoot.get(Programme_.uniteEnseignement).get(UniteEnseignement_.courses);
        Path<Etudiant> etudiantPath = noteRoot.get(Note_.etudiant);
        List<Predicate> predicates = new ArrayList<Predicate>();
        // Les étudiants inscrits dans le parcours ou inscript par le passe et inscrit l'annee en cours et ainsi obtenu une note 
        // a un cours du parcours et semestre l'annee en cours
        //predicates.add(cb.equal(coursUeRoot.get(CoursUEAnnee_.anneeAcademique), academique));
        //predicates.add(cb.equal(coursUeRoot.get(CoursUEAnnee_.uniteEnseignement), programmeRoot.get(Programme_.uniteEnseignement)));
        predicates.add(cb.equal(etudiantPath, inscriptionRoot.get(Inscription_.etudiant)));
        predicates.add(cb.equal(etudiantPath, inscriptionRoot2.get(Inscription_.etudiant)));
        predicates.add(cb.equal(inscriptionRoot2.get(Inscription_.parcours).get(Parcours_.niveau), niveau));
        predicates.add(cb.equal(inscriptionRoot2.get(Inscription_.parcours).get(Parcours_.option), option));
        predicates.add(cb.lessThanOrEqualTo(inscriptionRoot2.get(Inscription_.anneeAcademique).get(AnneeAcademique_.debut),academique.getDebut()));
        predicates.add(cb.equal(inscriptionRoot.get(Inscription_.anneeAcademique),academique));
        predicates.add(cb.equal(programmeRoot.get(Programme_.anneeAcademique),academique));
        predicates.add(cb.equal(programmeRoot.get(Programme_.parcours).get(Parcours_.niveau),niveau));
        predicates.add(cb.equal(programmeRoot.get(Programme_.parcours).get(Parcours_.option),option));
        predicates.add(cb.isMember(coursRoot, coursPath));
        //predicates.add(cb.equal(coursRoot, coursUeRoot.get(CoursUEAnnee_.cours)));
        //predicates.add(cb.equal(noteRoot.get(Note_.cours), coursRoot));
        predicates.add(cb.equal(noteRoot.get(Note_.evaluation), evaluationPath));
        if(semestre != null){
            predicates.add(cb.equal(programmeRoot.get(Programme_.semestre), semestre));
        }
        predicates.add(cb.equal(noteRoot.get(Note_.anneeAcademique),academique));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.distinct(true);
        cq.orderBy(cb.asc(etudiantPath.get(Etudiant_.nom)));
        cq.select(etudiantPath);
        return getManager().createQuery(cq).getResultList();
    }
    
    

}
