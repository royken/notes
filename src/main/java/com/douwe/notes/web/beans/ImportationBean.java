package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Evaluation;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;

import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.IEtudiantService;
import com.douwe.notes.service.IEvaluationService;
import com.douwe.notes.service.IInscriptionService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.ServiceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author root
 */
@Named(value = "importationBean")
@RequestScoped
public class ImportationBean {

    private UploadedFile file;

    private Etudiant etudiant;

    private List<AnneeAcademique> anneeAcademiques;

    @EJB
    private IEtudiantService etudiantService;

    @EJB
    private IInscriptionService inscriptionService;
    @EJB
    private INoteService noteService;
    @EJB
    private ICoursService coursService;
    @EJB
    private IDepartementService departementService;
    @EJB
    private IEvaluationService evaluationService;
    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
        @EJB
    private IOptionService optionService;
            @EJB
    private INiveauService niveauService;
    private List<Evaluation> evaluations;
    private List<Niveau> niveaus;
    private List<Option> options;    
    private List<Cours> courses;
    private List<Departement> departements;
    private Map<String, Long> countries = new HashMap<String, Long>();
    private Map<String, Long> cities = null;
    Long idAca = null;
    Long idC = null;
    Long idE = null;
    Long idN = null;
    Long idO = null;

    String nomFeuille = new String();
    List<String> nomFeuilles = new LinkedList<String>();
    private Long idD = null;

    public ImportationBean() {
        etudiant = new Etudiant();
    }

    public UploadedFile getFile() {
        file = null;
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String saveData() throws ServiceException, IOException {
        System.out.println("");
        if (file != null) {
            etudiantService.importEtudiants(file.getInputstream(), idAca);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "information", "importation reussie "));
            file = null;
            idAca = null;
        }
        return "etudiant";
    }

    public void saveNotes() throws ServiceException, IOException {
        if (file != null && idC != null && idC != 0L && idE != null && idE != 0L) {
            System.out.println("fichier============= " + file.getFileName() + " id cours " + idC + " id Evaluation " + idE + " id Annee " + idAca);
            noteService.importNotes(file.getInputstream(),idC,idE, idAca,0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "information","importation reussie "));
            System.out.println("fichier " + file.getFileName() + " id cours " + idC + " id Evaluation " + idE + " id Annee " + idAca);
            file = null;
            idAca = null;
            idE = null;
            idC = null;
        }

    }

    public void exportNotes() throws ServiceException, IOException {        
        if (file != null && idD != null  && idC != null && idAca != null  && idE != null && idN != null  && idO != null ) {
            //noteService.exportNotes(file.getInputstream(),idC,idE,idD,idAca,idN,idO);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "information","importation reussie "));
            System.out.println("fichier " + file.getFileName() + " id cours " + idC + " id Evaluation " + idE + " id Departement " + idD + " id Annee " + idAca + " id niveau " +idN + " id option "+idO);
            file = null;
            idAca = null;
            idE = null;
            idC = null;
            idD = null;
            idN = null;
            idO = null;
        }

    }
//    public void handleFileChange(ActionEvent actionEvent) throws IOException, InvalidFormatException {
//        if (file != null && file.getFileName().equals("")) {
//            final Workbook workbook = WorkbookFactory.create(file.getInputstream());
//            int i = 0;
//            while (workbook.getSheetName(i) != null) {
//                nomFeuilles.add(workbook.getSheetName(i));
//                i++;
//            }
//             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "chargement reussi","ok"));
//            System.out.println(""+nomFeuilles);
//        } else {
//             nomFeuilles = new LinkedList<String>();
//            file = null;
//        }
//
//    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        anneeAcademiques = anneeAcademiqueService.getAllAnnee();
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public Long getIdAca() {
        return idAca;
    }

    public void setIdAca(Long idAca) {
        this.idAca = idAca;
    }

    public String getNomFeuille() {
        return nomFeuille;
    }

    public void setNomFeuille(String nomFeuille) {
        this.nomFeuille = nomFeuille;
    }

    public IEtudiantService getEtudiantService() {
        return etudiantService;
    }

    public void setEtudiantService(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public IInscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(IInscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    public IAnneeAcademiqueService getAnneeAcademiqueService() {

        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    public List<String> getNomFeuilles() {
        return nomFeuilles;
    }

    public void setNomFeuilles(List<String> nomFeuilles) {
        this.nomFeuilles = nomFeuilles;

    }

    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    public ICoursService getCoursService() {
        return coursService;
    }

    public void setCoursService(ICoursService coursService) {
        this.coursService = coursService;
    }

    public IEvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public List<Evaluation> getEvaluations() throws ServiceException {
        evaluations = evaluationService.getAllEvaluations();
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Cours> getCourses() throws ServiceException {
        courses = coursService.getAllCours();
        return courses;
    }

    public void setCourses(List<Cours> courses) {
        this.courses = courses;
    }

    public Long getIdC() {
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    public List<Departement> getDepartements() throws ServiceException {
        departements = departementService.getAllDepartements();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }

    public void handleCountryChange() throws ServiceException {
        if (idD != null && idD != 0L) {
            Departement d = departementService.findDepartementById(idD);
            options = departementService.getAllOptions(d);
            cities = new HashMap<String, Long>();
            for (Option opt : options) {
                cities.put(opt.getCode(), opt.getId());
            }
        } else {
            cities = null;
        }
    }

    public Map<String, Long> getCountries() throws ServiceException {
        departements = departementService.getAllDepartements();
        for (Departement d : departements) {
            countries.put(d.getCode(), d.getId());
        }
        return countries;
    }

    public void setCountries(Map<String, Long> countries) {
        this.countries = countries;
    }

    public Map<String, Long> getCities() throws ServiceException {
        if (cities == null) {
            cities = new HashMap<String, Long>();
            options = optionService.getAllOptions();
            for (Option opt : options) {
                cities.put(opt.getCode(), opt.getId());
            }
        }

        return cities;
    }

    public void setCities(Map<String, Long> cities) {
        this.cities = cities;
    }

    public IOptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(IOptionService optionService) {
        this.optionService = optionService;
    }

    public INiveauService getNiveauService() {
        return niveauService;
    }

    public void setNiveauService(INiveauService niveauService) {
        this.niveauService = niveauService;
    }

    public List<Niveau> getNiveaus() throws ServiceException {
        niveaus=niveauService.getAllNiveaux();
        return niveaus;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
    
}
