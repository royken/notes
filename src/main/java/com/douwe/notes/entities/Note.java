package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Note.findNoteEvaluationCours",query = "SELECT n from Note n WHERE n.etudiant = :param1 and n.evaluation = :param2 and n.cours = :param3 and n.anneeAcademique = :param4")
})
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ETUDIANT_ID","EVALUATION_ID","COURS_ID","ANNEEACADEMIQUE_ID","SESSIONS"}))
public class Note implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column
    @Min(0)
    @Max(20)
    private double valeur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ETUDIANT_ID")
    private Etudiant etudiant;
    
    //les trois derniers a mettre à l'interface importation
    @ManyToOne(optional = false)
    @JoinColumn(name = "EVALUATION_ID")
    @XmlTransient
    private Evaluation evaluation;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "COURS_ID")
    @XmlTransient
    private Cours cours;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "ANNEEACADEMIQUE_ID")
    @XmlTransient
    private AnneeAcademique anneeAcademique;
    
    @Column(name = "SESSIONS", nullable = false)
    private Session session = Session.normale;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    
    public Note(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    @JsonIgnore
    public Etudiant getEtudiant() {
        return etudiant;
    }

    @JsonIgnore
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @JsonIgnore
    public Evaluation getEvaluation() {
        return evaluation;
    }

    @JsonIgnore
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @JsonIgnore
    public Cours getCours() {
        return cours;
    }

    @JsonIgnore
    public void setCours(Cours cours) {
        this.cours = cours;
    }

    @JsonIgnore
    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    @JsonIgnore
    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @JsonIgnore
    public int getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public int getActive() {
        return active;
    }

    @JsonIgnore
    public void setActive(int active) {
        this.active = active;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", version=" + version + ", valeur=" + valeur + ", etudiant=" + etudiant + ", evaluation=" + evaluation + ", cours=" + cours + ", anneeAcademique=" + anneeAcademique + ", session=" + session + ", active=" + active + '}';
    }
    
    
}
