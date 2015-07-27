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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@XmlRootElement(name = "unites")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "UE.deleteActive", query = "update UniteEnseignement ue set ue.active = 0 where ue.id = :idParam"),
    @NamedQuery(name = "UE.findAllActive", query = "select ue from UniteEnseignement ue where ue.active=1")

})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"INTITULE","PARCOURS_ID"}))
public class UniteEnseignement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @XmlTransient
    private int version;

    @Column(name = "INTITULE")
    private String intitule;

    @Column(unique = true)
    private String code;

    @Column
    private boolean hasOptionalChoices;
  
    @ManyToOne(optional = false)
    @XmlTransient
    @JoinColumn(name = "PARCOURS_ID")
    private Parcours parcours;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Cours> cours;

    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;

    public UniteEnseignement() {

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isHasOptionalChoices() {
        return hasOptionalChoices;
    }

    public void setHasOptionalChoices(boolean hasOptionalChoices) {
        this.hasOptionalChoices = hasOptionalChoices;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }
    
    @Override
    public String toString() {
        return "UniteEnseignement{" + "id=" + id + ", version=" + version + ", intitule=" + intitule + ", code=" + code + ", active=" + active + '}';
    }
}
