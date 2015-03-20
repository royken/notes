
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Named(value = "etudiantBean")
@RequestScoped
public class EtudiantBean {
    
    private Departement departement;
    
    private AnneeAcademique annee;
    
    private Niveau niveau;
    
    private Option option;
    
    @EJB
    private IEtudiantService etudiantService;
    
    @EJB
    private INiveauService niveauService;
    
    private List<Etudiant> etudiants;

    /**
     * Creates a new instance of EtudiantBean
     */
    public EtudiantBean() {
        etudiants = new ArrayList<Etudiant>();
    }
    
    public void update(){
        
    }
    
    public String filtrer() throws ServiceException{
        etudiants = etudiantService.findByCritiria((departement != null)? departement.getId() : -1, 
                (annee != null) ? annee.getId() : -1, 
                (niveau != null) ? niveau.getId(): -1, 
                (option != null) ? option.getId(): -1);       
        //etudiants = etudiantService.findByCritiria(null, null, niveau, null);
        return null;
    } 

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public AnneeAcademique getAnnee() {
        return annee;
    }

    public void setAnnee(AnneeAcademique annee) {
        this.annee = annee;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public IEtudiantService getEtudiantService() {
        return etudiantService;
    }

    public void setEtudiantService(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public List<Etudiant> getEtudiants() throws ServiceException {
        //niveau = niveauService.findNiveauById(851);
        //etudiants = etudiantService.findByCritiria(null, null, niveau, null);
        //System.err.println("Le nombre d'etudiants "+ etudiants.size());
        etudiants = etudiantService.getAllEtudiant();
        return etudiants;
    }
    
}
