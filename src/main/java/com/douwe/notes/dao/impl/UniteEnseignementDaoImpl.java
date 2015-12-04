package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.CoursUEAnnee;
import com.douwe.notes.entities.CoursUEAnnee_;
import com.douwe.notes.entities.Cours_;
import com.douwe.notes.entities.Credit;
import com.douwe.notes.entities.Credit_;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import com.douwe.notes.entities.Programme;
import com.douwe.notes.entities.Programme_;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.entities.UniteEnseignement_;
import com.douwe.notes.projection.UEnseignementCredit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class UniteEnseignementDaoImpl extends GenericDao<UniteEnseignement, Long> implements IUniteEnseignementDao {

    @Override
    public void deleteActive(UniteEnseignement ue) throws DataAccessException {
        getManager().createNamedQuery("UE.deleteActive").setParameter("idParam", ue.getId());
    }

    @Override
    public List<UniteEnseignement> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("UE.findAllActive").getResultList();
    }

    @Override
    public List<UEnseignementCredit> findByNiveauOptionSemestre(Niveau niveau, Option option, Semestre semestre, AnneeAcademique annee) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<UEnseignementCredit> cq = cb.createQuery(UEnseignementCredit.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        //Root<Cours> coursRoot = cq.from(Cours.class);
        Root<Credit> creditRoot = cq.from(Credit.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Semestre> semestrePath = programmeRoot.get(Programme_.semestre);
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        //Path<UniteEnseignement> unitePath = programmeRoot.get(Programme_.uniteEnseignement);
        //ListJoin<Cours, UniteEnseignement> ab = coursRoot.join(Cours_.uniteEnseignements);
        Path<UniteEnseignement> unitePath = programmeRoot.get(Programme_.uniteEnseignement);
        Expression<List<Cours>> ab = unitePath.get(UniteEnseignement_.cours);
        Join<Credit, Cours> toto = creditRoot.join(Credit_.cours);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(anneePath, annee));
        // A modifier
        predicates.add(cb.equal(creditRoot.get(Credit_.academique), annee));
        predicates.add(cb.equal(creditRoot.get(Credit_.parcours), parcoursPath));
        predicates.add(cb.isMember(toto, ab));
        //predicates.add(cb.equal(ab, unitePath));
        predicates.add(cb.equal(semestrePath, semestre));
        predicates.add(cb.equal(parcoursPath.get(Parcours_.niveau), niveau));
        predicates.add(cb.equal(parcoursPath.get(Parcours_.option), option));
        //predicates.add(cb.equal(programmeRoot.get(Programme_.uniteEnseignement), ab));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
       // cq.groupBy(ab.get(UniteEnseignement_.code));
        //cq.orderBy(cb.asc(ab.get(UniteEnseignement_.code)));
        // I need to figure out one day why it is not working
//        cq.multiselect(ab.get(UniteEnseignement_.code),
//                ab.get(UniteEnseignement_.intitule),
//                cb.selectCase()
//                        .when(cb.equal(ab.get(UniteEnseignement_.hasOptionalChoices), true), cb.min(coursRoot.get(Cours_.credit)).as(Integer.class))
//                        .otherwise(cb.sum(coursRoot.get(Cours_.credit)).as(Integer.class)));
        cq.groupBy(unitePath.get(UniteEnseignement_.code));
        cq.multiselect(
                unitePath.get(UniteEnseignement_.code),
                unitePath.get(UniteEnseignement_.intitule),
                cb.sum(creditRoot.get(Credit_.valeur)),
                cb.min(creditRoot.get(Credit_.valeur))
                ,
                unitePath.get(UniteEnseignement_.hasOptionalChoices)
        );
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public UniteEnseignement findByCours(Cours c, Niveau n, Option o, AnneeAcademique a) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<UniteEnseignement> cq = cb.createQuery(UniteEnseignement.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Root<CoursUEAnnee> ueRoot = cq.from(CoursUEAnnee.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Niveau> niveauPath = parcoursPath.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursPath.get(Parcours_.option);
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        Path<UniteEnseignement> unitePath = programmeRoot.get(Programme_.uniteEnseignement);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(ueRoot.get(CoursUEAnnee_.anneeAcademique), a));
        predicates.add(cb.equal(ueRoot.get(CoursUEAnnee_.cours), c));
        predicates.add(cb.equal(ueRoot.get(CoursUEAnnee_.uniteEnseignement), unitePath));
        predicates.add(cb.equal(niveauPath, n));
        predicates.add(cb.equal(optionPath, o));
        predicates.add(cb.equal(anneePath, a));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.select(unitePath);
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<UniteEnseignement> findByUniteNiveauOptionSemestre(Niveau niveau, Option option, Semestre semestre, AnneeAcademique annee) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<UniteEnseignement> cq = cb.createQuery(UniteEnseignement.class);
        Root<Programme> programmeRoot = cq.from(Programme.class);
        Path<Parcours> parcoursPath = programmeRoot.get(Programme_.parcours);
        Path<Semestre> semestrePath = programmeRoot.get(Programme_.semestre);
        Path<AnneeAcademique> anneePath = programmeRoot.get(Programme_.anneeAcademique);
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(anneePath, annee));
        //predicates.add(cb.equal(ab, unitePath));
        predicates.add(cb.equal(semestrePath, semestre));
        predicates.add(cb.equal(parcoursPath.get(Parcours_.niveau), niveau));
        predicates.add(cb.equal(parcoursPath.get(Parcours_.option), option));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq.orderBy(cb.asc(programmeRoot.get(Programme_.uniteEnseignement).get(UniteEnseignement_.code)));
        cq.select(programmeRoot.get(Programme_.uniteEnseignement));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<UniteEnseignement> findUniteByNiveauOption(Niveau niveau, Option option) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<UniteEnseignement> cq = cb.createQuery(UniteEnseignement.class);
        Root<UniteEnseignement> uniteRoot = cq.from(UniteEnseignement.class);
        Path<Parcours> parcoursPath = uniteRoot.get(UniteEnseignement_.parcours);
        Path<Niveau> niveauPath = parcoursPath.get(Parcours_.niveau);
        Path<Option> optionPath = parcoursPath.get(Parcours_.option);
        cq.where(cb.and(cb.equal(optionPath, option), cb.equal(niveauPath, niveau)));
        cq.orderBy(cb.asc(uniteRoot.get(UniteEnseignement_.code)));
        return getManager().createQuery(cq).getResultList();
    }

}
