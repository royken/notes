package com.douwe.notes.projection;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class StatistiquesNote {
    
    private int effectif;
    private double plusGrandeMoyenne;
    private double plusPetiteMoyenne;
    private int nombreMoyenneSuperieureQuinze;
    private int nombreMoyenneEntre10et15;
    private int nombreMoyenneInferieurA10;

    public StatistiquesNote() {
    }
    
    public StatistiquesNote(int effectif, double plusGrandeMoyenne, double plusPetiteMoyenne, int nombreMoyenneSuperieureQuinze, int nombreMoyenneEntre10et15, int nombreMoyenneInferieurA10) {
        this.effectif = effectif;
        this.plusGrandeMoyenne = plusGrandeMoyenne;
        this.plusPetiteMoyenne = plusPetiteMoyenne;
        this.nombreMoyenneSuperieureQuinze = nombreMoyenneSuperieureQuinze;
        this.nombreMoyenneEntre10et15 = nombreMoyenneEntre10et15;
        this.nombreMoyenneInferieurA10 = nombreMoyenneInferieurA10;
    }
    

    public int getEffectif() {
        return effectif;
    }

    public void setEffectif(int effectif) {
        this.effectif = effectif;
    }

    public double getPlusGrandeMoyenne() {
        return plusGrandeMoyenne;
    }

    public void setPlusGrandeMoyenne(double plusGrandeMoyenne) {
        this.plusGrandeMoyenne = plusGrandeMoyenne;
    }

    public double getPlusPetiteMoyenne() {
        return plusPetiteMoyenne;
    }

    public void setPlusPetiteMoyenne(double plusPetiteMoyenne) {
        this.plusPetiteMoyenne = plusPetiteMoyenne;
    }

    public int getNombreMoyenneSuperieureQuinze() {
        return nombreMoyenneSuperieureQuinze;
    }

    public void setNombreMoyenneSuperieureQuinze(int nombreMoyenneSuperieureQuinze) {
        this.nombreMoyenneSuperieureQuinze = nombreMoyenneSuperieureQuinze;
    }

    public int getNombreMoyenneEntre10et15() {
        return nombreMoyenneEntre10et15;
    }

    public void setNombreMoyenneEntre10et15(int nombreMoyenneEntre10et15) {
        this.nombreMoyenneEntre10et15 = nombreMoyenneEntre10et15;
    }

    public int getNombreMoyenneInferieurA10() {
        return nombreMoyenneInferieurA10;
    }

    public void setNombreMoyenneInferieurA10(int nombreMoyenneInferieurA10) {
        this.nombreMoyenneInferieurA10 = nombreMoyenneInferieurA10;
    }
}
