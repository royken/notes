package com.douwe.notes.projection;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MoyenneUniteEnseignement {

    private List<AnneeAcademique> annees;

    private List<Session> sessions;

    private Map<String, Integer> credits;

    private Map<String, Double> notes;
    
    private final boolean avecComposantesOptionnelles;
    
    public MoyenneUniteEnseignement(){
        this(false);
    }

    public MoyenneUniteEnseignement(boolean type) {
        annees = new ArrayList<AnneeAcademique>();
        sessions = new ArrayList<Session>();
        credits = new HashMap<String, Integer>();
        notes = new HashMap<String, Double>();
        avecComposantesOptionnelles = type;
    }

    public Session getSession() {
        Session result = null;
        if (!sessions.isEmpty()) {
            result = sessions.get(0);
            for (Session sess : sessions) {
                if (result.compareTo(sess) < 0) {
                    result = sess;
                }
            }
        }
        return result;
    }

    public AnneeAcademique getAnneeAcademique() {
        AnneeAcademique result = null;
        if(!annees.isEmpty()){
            result = annees.get(0);
            for (AnneeAcademique annee : annees) {
                if(result.getDebut().before(annee.getDebut()))
                    result = annee;
            }
        }
        return result;
    }

    public double getMoyenne() {
        double result = 0.0;
        int creditTotal = getNombreCredit();
        for (Map.Entry<String, Double> entrySet : notes.entrySet()) {
            String key = entrySet.getKey();
            Double value = entrySet.getValue();
            int credit = credits.get(key);
            if(avecComposantesOptionnelles){
                if(result < value * credit)
                    result = value * credit;
            }else{
                result += value  * credit;
            }
        }
        return (creditTotal != 0)? result / creditTotal: result;
    }
    
    private int getNombreCredit(){
        int result = 0;
        for (Map.Entry<String, Integer> entrySet : credits.entrySet()) {
            Integer value = entrySet.getValue();
            if(avecComposantesOptionnelles){
                result = value;
                break;
            }
            result += value;
        }
        return result;
    }
    
    public int getCredit(){
        int result = 0;
        if(!credits.isEmpty()){
            for (Map.Entry<String, Integer> entrySet : credits.entrySet()) {
                Integer value = entrySet.getValue();
                result += value;
                if(avecComposantesOptionnelles)
                    break;
            }
        }
        return result;
    }

    public void setAnnees(List<AnneeAcademique> annees) {
        this.annees = annees;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void setCredits(Map<String, Integer> credits) {
        this.credits = credits;
    }

    public void setNotes(Map<String, Double> notes) {
        this.notes = notes;
    }

    public List<AnneeAcademique> getAnnees() {
        return annees;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public Map<String, Integer> getCredits() {
        return credits;
    }

    public Map<String, Double> getNotes() {
        return notes;
    }
    
}
