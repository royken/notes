package com.douwe.notes.projection;

import java.util.Map;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class EtudiantNotes {
    
    private String matricule;
    
    private String nom;
    
    private Map<String,Double> note;
    
    private Map<String, Integer> details;
    
    public double getMoyenne(){
        double res = 0;
        for (Map.Entry<String, Integer> col : details.entrySet()) {
            if(note.containsKey(col.getKey())){
                res += note.get(col.getKey()) * col.getValue();
            }
        }
        return res / 100;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Map<String, Double> getNote() {
        return note;
    }

    public void setNote(Map<String, Double> note) {
        this.note = note;
    }

    public Map<String, Integer> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Integer> details) {
        this.details = details;
    }
   
}
