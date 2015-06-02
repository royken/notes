package com.douwe.notes.projection;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement
public class UEnseignementCredit {
    
    private String codeUE;
    
    private String intituleUE;
    
    private int credit;

    public UEnseignementCredit() {
    }

    public UEnseignementCredit(String codeUE, String intituleUE, int credit) {
        this.codeUE = codeUE;
        this.intituleUE = intituleUE;
        this.credit = credit;
    }

    public String getCodeUE() {
        return codeUE;
    }

    public void setCodeUE(String codeUE) {
        this.codeUE = codeUE;
    }

    public String getIntituleUE() {
        return intituleUE;
    }

    public void setIntituleUE(String intituleUE) {
        this.intituleUE = intituleUE;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "UEnseignementCredit{" + "codeUE=" + codeUE + ", intituleUE=" + intituleUE + ", credit=" + credit + '}';
    }

   

   
}
