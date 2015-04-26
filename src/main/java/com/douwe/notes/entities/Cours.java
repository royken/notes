package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@XmlRootElement(name = "cours")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({

@NamedQuery(name = "Cours.deleteActive",query = "update Cours c set c.active = 0 where c.id = :idParam"),
@NamedQuery(name = "Cours.findAllActive",query = "select c from Cours c WHERE c.active=1"),
@NamedQuery(name = "Cours.findByIntitule",query = "SELECT c FROM Cours c WHERE c.intitule like :param"),
@NamedQuery(name = "Cours.findByUE",query = "select co from Cours co, UniteEnseignement ue JOIN ue.courses c_e where ue.id = :id_parameter and c_e.id =co.id")
})
public class Cours implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column(unique = true)
    private String intitule;
    
    @Column
    private Integer credit;
    
    @ManyToOne(optional = false)
    private TypeCours typeCours;
    
    @XmlTransient
    @ManyToMany(mappedBy = "courses")
    private List<UniteEnseignement> uniteEnseignements;
    
    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Cours(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public TypeCours getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
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
    public List<UniteEnseignement> getUniteEnseignements() {
        return uniteEnseignements;
    }

    @JsonIgnore
    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
        this.uniteEnseignements = uniteEnseignements;
    }
    
    

    @Override
    public String toString() {
        return "Cours{" + "id=" + id + ", version=" + version + ", intitule=" + intitule + ", credit=" + credit + ", typeCours=" + typeCours + ", active=" + active + '}';
    }
    
    
}
