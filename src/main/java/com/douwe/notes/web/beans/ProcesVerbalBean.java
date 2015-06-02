package com.douwe.notes.web.beans;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Parcours;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.service.IAnneeAcademiqueService;
import com.douwe.notes.service.ICoursService;
import com.douwe.notes.service.IDocumentService;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.INiveauService;
import com.douwe.notes.service.IParcoursService;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.IOptionService;
import com.douwe.notes.service.ServiceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author root
 */
@Named(value = "procesVerbalBean")
@RequestScoped
public class ProcesVerbalBean {

    private Session session;
    private List<AnneeAcademique> anneeAcademiques;

    @EJB
    private INoteService noteService;
    @EJB
    private ICoursService coursService;
    @EJB
    private IDepartementService departementService;
    @EJB
    private IAnneeAcademiqueService anneeAcademiqueService;
    @EJB
    private IParcoursService parcoursService;
    @EJB
    private IOptionService optionService;
    @EJB
    private INiveauService niveauService;
    @EJB
    private IDocumentService documentService;

    private StreamedContent fichier;

    private List<Niveau> niveaus;
    private List<Option> options;
    //private Map<String, Long> courses;
    private List<Cours> courses;
    private List<Departement> departements;
    private List<Session> sessions;
    //private Map<String, Long> countries = new HashMap<String, Long>();
    //private Map<String, Long> cities = null;
    Long idAca = null;
    Long idC = null;

    Long idN = null;
    Long idO = null;
    Long idD = null;

    public void procesVerbal() throws ServiceException, FileNotFoundException {
        if (idN != null && idO != null && idC != null && session != null) {
            FacesContext context = FacesContext.getCurrentInstance();

            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                try {
                    HttpServletResponse hsr = (HttpServletResponse) response;
                    hsr.setContentType("application/pdf");
                    hsr.setHeader("Content-Disposition", "attachment; filename=pv.pdf");
                    documentService.produirePv(idN, idO, idC, idAca, session.ordinal(), ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                    context.responseComplete();
                } catch (IOException ex) {
                    Logger.getLogger(ProcesVerbalBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
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

    public IAnneeAcademiqueService getAnneeAcademiqueService() {

        return anneeAcademiqueService;
    }

    public void setAnneeAcademiqueService(IAnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
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

    public List<Cours> getCourses() {
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

    public StreamedContent getFichier() {
        return fichier;
    }

    public void setFichier(StreamedContent fichier) {
        this.fichier = fichier;
    }

    public void handleCountryChange() throws ServiceException {
        if (idD != null && idD != 0L) {
            Departement d = departementService.findDepartementById(idD);
            options = departementService.getAllOptions(d);

        } else {

            options = null;
        }
    }

    public void handleNiveauOptionChange() throws ServiceException {
        System.out.println("Les valeurs sont "+idD + " et alors "+ idN + " ou alors "+idO);
        if (idN != null && idN != 0L) {
            // Le niveau n'est pas null
            if (idO != null && idO != 0L) {
                // l'option n'est pas null alors il faut charger les cours du parcours
                Niveau n = niveauService.findNiveauById(idN);
                Option o = optionService.findOptionById(idO);
                Parcours p = parcoursService.findByNiveauOption(n, o);
                courses = coursService.findByParcoursAnnee(p.getId(), idAca);   
                System.out.println("La liste des cours");
                for (Cours c : courses) {
                    System.out.println(c.getIntitule());
                }
            } else {
                // Il faut charger les options 
                options = optionService.findByDepartementNiveau(idD, idN);
            }
        }
//        if (idN != null && idN != 0L && idO != null && idO != 0L) {
//        Niveau n = niveauService.findNiveauById(idN);
//        Option o = optionService.findOptionById(idO);            
//            Parcours p = parcoursService.findByNiveauOption(n, o);
//            List<Cours> cs = coursService.findByParcoursAnnee(p.getId(), idAca);
//            courses = new HashMap<String, Long>();
//            for (Cours c : cs) {
//                courses.put(c.getIntitule(), c.getId());
//            }
//        } else {
//            courses = null;
//        }
    }

//    public Map<String, Long> getCountries() throws ServiceException {
//        departements = departementService.getAllDepartements();
//        for (Departement d : departements) {
//            countries.put(d.getCode(), d.getId());
//        }
//        return countries;
//    }
//
//    public void setCountries(Map<String, Long> countries) {
//        this.countries = countries;
//    }
    //public Map<String, Long> getCities() throws ServiceException {
//        if (cities == null) {
//            cities = new HashMap<String, Long>();
//            options = optionService.getAllOptions();
//            for (Option opt : options) {
//                cities.put(opt.getCode(), opt.getId());
//            }
//        }
//        return cities;
//    }
//    public void setCities(Map<String, Long> cities) {
//        this.cities = cities;
//    }
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
        niveaus = niveauService.getAllNiveaux();
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

    public IDocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(IDocumentService documentService) {
        this.documentService = documentService;
    }

    public List<Session> getSessions() {
        Session[] ss;
        ss = Session.values();
        sessions = new ArrayList<Session>();
        for (int i = 0; i < 2; i++) {
            sessions.add(ss[i]);
        }
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
