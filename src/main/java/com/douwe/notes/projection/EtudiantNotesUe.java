package com.douwe.notes.projection;

import java.util.Map;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class EtudiantNotesUe {
    
    private String matricule;
    
    private String nom;
    
    
    /**
    * La note de l'étudiant à une unité d'enseignement
    */
    private Map<String,Double> note;

    public EtudiantNotesUe(String matricule, String nom, Map<String, Double> note) {
        this.matricule = matricule;
        this.nom = nom;
        this.note = note;
    }

    public EtudiantNotesUe(String matricule, String nom) {
        this.matricule = matricule;
        this.nom = nom;
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
    
    
    
}
