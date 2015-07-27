package com.douwe.notes.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.UEnseignementCredit;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IUniteEnseignementDao extends IDao<UniteEnseignement, Long>{
    
    public void deleteActive(UniteEnseignement ue) throws DataAccessException;
    
    public List<UniteEnseignement> findAllActive() throws DataAccessException;
    
    UniteEnseignement findByCours(Cours c, Niveau n, Option o, AnneeAcademique a) throws DataAccessException;
    
    // Retourne la liste des UEnseignementCredit d'un parcours pour un semestre
    public List<UEnseignementCredit> findByNiveauOptionSemestre(Niveau niveau, Option option, Semestre semestre, AnneeAcademique annee) throws DataAccessException;
    
    public List<UniteEnseignement> findByUniteNiveauOptionSemestre(Niveau niveau, Option option, Semestre semestre, AnneeAcademique annee) throws DataAccessException;
    
    public List<UniteEnseignement> findUniteByNiveauOption(Niveau niveau, Option option) throws DataAccessException;
    
}
