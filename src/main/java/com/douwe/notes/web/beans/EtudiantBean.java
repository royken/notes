package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Genre;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.ServiceException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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
    private IParcoursService parcoursService;

    @EJB
    private IDepartementService departementService;

    @EJB
    private IInscriptionService inscriptionService;

    private List<Etudiant> etudiants = new LinkedList<Etudiant>();

    private List<Niveau> niveaus;
    private List<Option> options;
    private List<AnneeAcademique> anneeAcademiques;
    private List<Departement> departements;
    private Etudiant etudiant = new Etudiant();
    private Inscription inscription;
    private AnneeAcademique anneeAcademique;
    private Genre genre;
    private List<String> genres = new LinkedList<String>();
    private Parcours parcours;
    private List<Parcours> parcourses = new LinkedList<Parcours>();
    Long idD = 0L, idN = 0L, idO = 0L, idA = 0L, idP = 0L;
    int taille = 30;

    /**
     * Creates a new instance of EtudiantBean
     */
    public EtudiantBean() {

        departement = new Departement();
        anneeAcademique = new AnneeAcademique();
        niveau = new Niveau();
        option = new Option();        
        parcours = new Parcours();
        inscription = new Inscription();
    }

    public void update() {

    }

    public void filtrer() throws ServiceException {
        if (idA != null) {
            anneeAcademique = anneeAcademiqueService.findAnneeById(idA);
        }
        if (idD != null) {
            departement = departementService.findDepartementById(idD);
        }
        if (idN != null) {
            niveau = niveauService.findNiveauById(idN);
        }
        if (idO != null) {
            option = optionService.findOptionById(idO);
        }
        etudiants = etudiantService.findByCritiria(departement, annee, niveau, option);
        initTaille();        
        departement = new Departement();
        anneeAcademique = new AnneeAcademique();
        niveau = new Niveau();
        option = new Option();        
        idD = null;
        idN = null;
        idO = null;
    }

    public void saveOrUpdateEtudiant(ActionEvent actionEvent) throws ServiceException {
        if (etudiant != null && etudiant != null) {
            etudiantService.saveOrUpdateEtudiant(etudiant);
            parcours = parcoursService.findParcoursById(idP);
            System.out.println("" + parcours);
            inscription.setParcours(parcours);
            inscription.setEtudiant(etudiant);
            AnneeAcademique anneeAcademique1 = anneeAcademiqueService.findAnneeById(idA);
            inscription.setAnneeAcademique(anneeAcademique1);
            inscriptionService.saveOrUpdateInscription(inscription);
            if (etudiant.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "l'étudiant " + etudiant.getNom() + " a été enregistré en " + inscription.getParcours().getNiveau().getCode() + " option " + inscription.getParcours().getOption().getCode()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "l'étudiant " + etudiant.getNom() + " a été enregistré en " + inscription.getParcours().getNiveau().getCode() + " option " + inscription.getParcours().getOption().getCode()));
            }
            idA = inscription.getAnneeAcademique().getId();
            idD = inscription.getParcours().getOption().getDepartement().getId();
            idN = inscription.getParcours().getNiveau().getId();
            idO = inscription.getParcours().getOption().getId();
            filtrer();                        
            inscription = new Inscription();
            parcours = new Parcours();
            etudiant = new Etudiant();

        }
    }

    public void initTaille() {
        if (etudiants.isEmpty()) {
            taille = 30;
        } else {
            taille = 300;
        }
    }

    public void deleteEtudiant(ActionEvent actionEvent) throws ServiceException {
        if (etudiant != null && etudiant.getId() != null) {
            AnneeAcademique a = anneeAcademiqueService.findAnneeById(idA);
            //find inscriptionbyEtudiant
            // inscription = findIncriptionByEtudiant(etudiant,a);
            etudiantService.deleteEtudiant(etudiant.getId());
            //inscriptionService.deleteInscription(inscription.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", "l'étudiant " + etudiant.getNom() + " a été enregistré en " + inscription.getParcours().getNiveau().getCode() + "/" + inscription.getParcours().getOption().getCode()));
            etudiant = new Etudiant();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (etudiant != null && etudiant.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un etudiant avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        System.out.println("verifierEtSupprimer-------"+etudiant);
        if (etudiant != null && etudiant.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un etudiant avant de supprimer "));
        }
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
        if (etudiant != null && inscription != null && inscription.getAnneeAcademique() != null) {
            idP = inscription.getAnneeAcademique().getId();
        }
        
        return idA;
    }

    public void setIdA(Long idA) {        
        this.idA = idA;
    }

    public Etudiant getEtudiant() throws ServiceException {
        AnneeAcademique a = anneeAcademiqueService.findAnneeById(idA);
        // inscription = findIncriptionByEtudiant(etudiant,a);
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Inscription getInscription() {        
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public List<String> getGenres() {
        genres.add("feminin");
        genres.add("masculin");
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Long getIdP() {
        if (etudiant != null && inscription != null && inscription.getParcours() != null) {
            idP = inscription.getParcours().getId();
        }
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public IParcoursService getParcoursService() {
        return parcoursService;
    }

    public void setParcoursService(IParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public List<Parcours> getParcourses() throws ServiceException {
        parcourses = parcoursService.getAllParcours();
        return parcourses;
    }

    public void setParcourses(List<Parcours> parcourses) {
        this.parcourses = parcourses;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public IInscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(IInscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

}
