package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.AnneeAcademique_;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Note;
import com.douwe.notes.entities.Note_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class AnneeAcademiqueDaoImpl extends GenericDao<AnneeAcademique, Long> implements IAnneeAcademiqueDao{

    @Override
    public List<AnneeAcademique> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Annee.findAllActive").getResultList();
    }

//    @Override
//    public AnneeAcademique findByString(String date) throws DataAccessException {
//        return (AnneeAcademique)(getManager().createNamedQuery("Annee.findByString").setParameter("param", date).getSingleResult());
//    }

    @Override
    public AnneeAcademique findLastYearNote(Etudiant etudiant, Cours c) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<AnneeAcademique> cq = cb.createQuery(AnneeAcademique.class);
        Root<Note> noteRoot = cq.from(Note.class);
        Path<AnneeAcademique> anneePath = noteRoot.get(Note_.anneeAcademique);
        Path<Etudiant> etudiantPath = noteRoot.get(Note_.etudiant);
        Path<Cours> coursPath = noteRoot.get(Note_.cours);
        cq.where(cb.and(cb.equal(etudiantPath, etudiant), cb.equal(coursPath, c)));
        cq.select(anneePath);
        cq.orderBy(cb.desc(anneePath.get(AnneeAcademique_.debut)));
        return getManager().createQuery(cq).setMaxResults(1).getSingleResult();
    }
}
