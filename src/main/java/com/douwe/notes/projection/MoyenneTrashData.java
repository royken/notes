package com.douwe.notes.projection;

import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.Session;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class MoyenneTrashData {
    
    private double moyenne;
    
    private Session session;
    
    private Semestre semestre;

    public MoyenneTrashData(double moyenne, Session session, Semestre semestre) {
        this.moyenne = moyenne;
        this.session = session;
        this.semestre = semestre;
    }

    public MoyenneTrashData() {
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
    
    
    
    
    
}
