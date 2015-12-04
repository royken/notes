package com.douwe.notes.projection;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@XmlRootElement(name = "noteTransfer")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteTransfer implements Serializable{
    
    private long id;
    
    private String evaluation;
    
    private String session;
    
    private double valeur;

    public NoteTransfer() {
    }
    
    public NoteTransfer(long id, String evaluation, String session, double valeur) {
        this.id = id;
        this.evaluation = evaluation;
        this.session = session;
        this.valeur = valeur;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    
    
}
