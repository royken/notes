package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity
@NamedQueries({
@NamedQuery(name = "UE.deleteActive",query = "update UniteEnseignement ue set ue.active = 0 where ue.id = :idParam"),
@NamedQuery(name = "UE.findAllActive",query = "select ue from UniteEnseignement ue where ue.active=1")    

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
    
    @Column
    private String code;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    @ManyToMany(mappedBy = "uniteEnseignements")
    private List<Parcours> parcours;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Cours> courses;
    
    
    
    
    public UniteEnseignement(){
        
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

    public List<Parcours> getParcours() {
        return parcours;
    }

    public void setParcours(List<Parcours> parcours) {
        this.parcours = parcours;
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

    public List<Cours> getCourses() {
        return courses;
    }

    public void setCourses(List<Cours> courses) {
        this.courses = courses;
    }
    
    

    @Override
    public String toString() {
        return "UniteEnseignement{" + "id=" + id + ", version=" + version + ", intitule=" + intitule + ", code=" + code + ", active=" + active + ", parcours=" + parcours + '}';
    }
    
    
}
