package com.douwe.notes.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * I really think we don't need this class
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Deprecated
@Entity
@XmlRootElement(name = "coursUeAnnee")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ANNEEACADEMIQUE_ID", "DEPARTEMENT_ID", "COURS_ID" , "TYPECOURS_ID"}))
public class CoursUEAnnee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "COURS_ID")
    private Cours cours;

    @ManyToOne(optional = false)
     @JoinColumn(name = "TYPECOURS_ID")  
    private TypeCours typeCours;

     @ManyToOne(optional = false)
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ANNEEACADEMIQUE_ID")
    private AnneeAcademique anneeAcademique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }


    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    @Override
    public String toString() {
        return "CoursUEAnnee{" + "id=" + id + ", cours=" + cours + ", typeCours=" + typeCours + ", departement=" + departement + ", anneeAcademique=" + anneeAcademique + '}';
    }
    
}
