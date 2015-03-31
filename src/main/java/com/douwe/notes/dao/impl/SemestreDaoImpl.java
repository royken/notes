package com.douwe.notes.dao.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Niveau_;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Semestre_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class SemestreDaoImpl extends GenericDao<Semestre, Long> implements ISemestreDao{

    @Override
    public List<Semestre> findByNiveau(Niveau n) throws DataAccessException {
        System.out.println("Toto est de retour");
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Semestre> cq = cb.createQuery(Semestre.class);
        Root<Semestre> semestreRoot = cq.from(Semestre.class);
        Path<Niveau> niveauPath =  semestreRoot.get(Semestre_.niveau);
        cq.where(cb.and(cb.equal(semestreRoot.get(Semestre_.active), 1), cb.equal(niveauPath,n), cb.equal(niveauPath.get(Niveau_.active), 1)));
        cq.select(semestreRoot);
        return getManager().createQuery(cq).getResultList();
    }
    
}
