package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@XmlRootElement(name = "typeCours")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
@NamedQuery(name = "TypeCours.findAllActive",query = "SELECT t FROM TypeCours t WHERE t.active = 1")

})
public class TypeCours implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column(unique = true)
    private String nom;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
     
     
    @OneToMany(mappedBy = "typeCours", fetch = FetchType.EAGER)
     private List<EvaluationDetails> evaluations;
    
    public TypeCours(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public List<EvaluationDetails> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<EvaluationDetails> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String toString() {
        return "TypeCours{" + "id=" + id + ", version=" + version + ", nom=" + nom + ", active=" + active + '}';
    }
    
    
}
