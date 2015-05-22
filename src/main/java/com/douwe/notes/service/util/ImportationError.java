package com.douwe.notes.service.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@XmlRootElement(name = "erreur")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportationError {

    private int ligne;
    private String raison;

    public ImportationError() {
    }

    public ImportationError(int ligne, String raison) {
        this.ligne = ligne;
        this.raison = raison;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

}
