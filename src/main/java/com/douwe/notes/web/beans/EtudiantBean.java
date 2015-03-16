package com.douwe.notes.web.beans;

import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.apache.poi.ss.formula.atp.AnalysisToolPak;

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

    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;

    @EJB
    private IOptionService optionService;
    @EJB
    private IDepartementService departementService;

    private List<Etudiant> etudiants;
    private List<Niveau> niveaus;
    private List<Option> options;
    private List<AnneeAcademique> anneeAcademiques;
    private List<Departement> departements;
    private Etudiant etudiant;
    private AnneeAcademique anneeAcademique;   
    Long idD = 0L, idN = 0L, idO = 0L, idA = 0L;

    /**
     * Creates a new instance of EtudiantBean
     */
    public EtudiantBean() {
        etudiants = new ArrayList<Etudiant>();
        departement = new Departement();
        anneeAcademique = new AnneeAcademique();
        niveau =  new Niveau();
        option = new Option();
    }

    public void update() {

    }

    public void filtrer() throws ServiceException {
        if (idA != 0) {
            anneeAcademique = anneeAcademiqueService.findAnneeById(idA);
        }
        if(idD!=0)
            departement = departementService.findDepartementById(idD);
        if (idN!=0) {
            niveau = niveauService.findNiveauById(idN);            
        }
        if (idO!=0) {
            option = optionService.findOptionById(idO);
        }
        etudiants = etudiantService.findByCritiria(departement, annee, niveau, option);
        //etudiants = etudiantService.findByCritiria(null, null, niveau, null);
        System.err.println("Le nombre d'etudiants " + etudiants.size());        
                etudiants = new ArrayList<Etudiant>();
        departement = new Departement();
        anneeAcademique = new AnneeAcademique();
        niveau =  new Niveau();
        option = new Option();
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
        // etudiants = etudiantService.findByCritiria(null, null, niveau, null);
        //System.err.println("Le nombre d'etudiants "+ etudiants.size());
        //etudiants = etudiantService.getAllEtudiant();
        return etudiants;
    }

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {
        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    public List<Niveau> getNiveaus() throws ServiceException {
        niveaus = niveauService.getAllNiveaux();
        return niveaus;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public List<Option> getOptions() throws ServiceException {
        options = optionService.getAllOptions();
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public List<Departement> getDepartements() throws ServiceException {
        departements = departementService.getAllDepartements();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }

    public Long getIdN() {
        return idN;
    }

    public void setIdN(Long idN) {
        this.idN = idN;
    }

    public Long getIdO() {
        return idO;
    }

    public void setIdO(Long idO) {
        this.idO = idO;
    }

    public Long getIdA() {
        return idA;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
    }

}
