package com.douwe.notes.service.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@XmlRootElement(name = "deliberationElement")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliberationItem {
    private String matricule;
    private String nom;
    private double noteAvant;
    private double moyenneAvant;
    private double noteApres;
    private double moyenneApres;

    public DeliberationItem() {
    }

    public DeliberationItem(String matricule, String nom, double noteAvant, double moyenneAvant, double noteApres, double moyenneApres) {
        this.matricule = matricule;
        this.nom = nom;
        this.noteAvant = noteAvant;
        this.moyenneAvant = moyenneAvant;
        this.noteApres = noteApres;
        this.moyenneApres = moyenneApres;
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

    public double getNoteAvant() {
        return noteAvant;
    }

    public void setNoteAvant(double noteAvant) {
        this.noteAvant = noteAvant;
    }

    public double getMoyenneAvant() {
        return moyenneAvant;
    }

    public void setMoyenneAvant(double moyenneAvant) {
        this.moyenneAvant = moyenneAvant;
    }

    public double getNoteApres() {
        return noteApres;
    }

    public void setNoteApres(double noteApres) {
        this.noteApres = noteApres;
    }

    public double getMoyenneApres() {
        return moyenneApres;
    }

    public void setMoyenneApres(double moyenneApres) {
        this.moyenneApres = moyenneApres;
    }
    
    
}
