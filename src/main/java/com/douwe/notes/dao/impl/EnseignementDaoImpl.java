package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.IEnseignementDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours_;
import com.douwe.notes.entities.Enseignement;
import com.douwe.notes.entities.Enseignement_;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Parcours_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class EnseignementDaoImpl extends GenericDao<Enseignement, Long> implements IEnseignementDao{

    @Override
    public void deleteActive(Enseignement enseignement) throws DataAccessException {
        getManager().createNamedQuery("Enseignement.deleteActive").setParameter("idParam", enseignement.getId());
    }

    @Override
    public List<Enseignement> findAllActive() throws DataAccessException {
        return getManager().createNamedQuery("Enseignement.findAllActive").getResultList();
    }

    @Override
    public List<Enseignement> findByOption(AnneeAcademique annee, Niveau niveau, Option option) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Enseignement> cq = cb.createQuery(Enseignement.class);
        Root<Enseignement> root = cq.from(Enseignement.class);
        Path<AnneeAcademique> annePath = root.get(Enseignement_.anneeAcademique);
        Path<Parcours> parcoursPath = root.get(Enseignement_.parcours);
        cq.where(cb.and(cb.equal(annePath, annee), cb.equal(parcoursPath.get(Parcours_.niveau), niveau),
                cb.equal(parcoursPath.get(Parcours_.option), option),
                cb.ge(root.get(Enseignement_.active), 1)));
        cq.orderBy(cb.asc(root.get(Enseignement_.cours).get(Cours_.intitule)));
        return getManager().createQuery(cq).getResultList();
    }
    
}
