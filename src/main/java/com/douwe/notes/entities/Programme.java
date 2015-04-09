package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity
@XmlRootElement(name = "programme")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
@NamedQuery(name = "Programme.deleteActive",query = "update Programme p set p.active = 0 where p.id = :idParam"),
@NamedQuery(name = "Programme.findAllActive",query = "select p from Programme p where p.active=1"),
@NamedQuery(name = "Programme.findByNiveauOption",query = "SELECT p FROM Programme p WHERE p.parcours.niveau = :param1 and p.parcours.option = :param2 and p.anneeAcademique = :param3 and p.semestre = :param4")


})
public class Programme implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @ManyToOne
    @XmlTransient
    private AnneeAcademique anneeAcademique;
    
    @ManyToOne
    @XmlTransient
    private UniteEnseignement uniteEnseignement;
    
    @ManyToOne
    @XmlTransient
    private Parcours parcours;
    
    @ManyToOne
    @XmlTransient
    private Semestre semestre;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    
    
    
    public Programme(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
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

    @Override
    public String toString() {
        return "Programme{" + "id=" + id + ", version=" + version + ", anneeAcademique=" + anneeAcademique + ", uniteEnseignement=" + uniteEnseignement + ", parcours=" + parcours + ", active=" + active + '}';
    }
    
    
}
