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
@XmlRootElement(name = "unites")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "UE.deleteActive", query = "update UniteEnseignement ue set ue.active = 0 where ue.id = :idParam"),
    @NamedQuery(name = "UE.findAllActive", query = "select ue from UniteEnseignement ue where ue.active=1")

})
public class UniteEnseignement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @XmlTransient
    private int version;

    @Column
    private String intitule;

    @Column(unique = true)
    private String code;

    @Column
    private boolean hasOptionalChoices;
  
    @ManyToOne
    private Parcours parcours;

    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;

//     @XmlTransient
//    @ManyToMany(mappedBy = "uniteEnseignements")
//    private List<Parcours> parcours;
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

//    @JsonIgnore
//    public List<Parcours> getParcours() {
//        return parcours;
//    }
//
//    @JsonIgnore
//    public void setParcours(List<Parcours> parcours) {
//        this.parcours = parcours;
//    }
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

    @Override
    public String toString() {
        return "UniteEnseignement{" + "id=" + id + ", version=" + version + ", intitule=" + intitule + ", code=" + code + ", active=" + active + '}';
    }
}
