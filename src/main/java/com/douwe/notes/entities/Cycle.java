package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity(name="cycles")
@NamedQueries({
@NamedQuery(name = "Cycle.deleteActive",query ="update cycles c set c.active = 0 where c.id = :idParam"),
@NamedQuery(name = "Cycle.findAllActive",query = "select c from cycles c where c.active = 1")

})
public class Cycle implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column (unique = true)
    private String nom;
    
    @OneToMany(mappedBy = "cycle")
    @XmlTransient
    private List<Niveau> niveaux;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Cycle(){
        
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
    public List<Niveau> getNiveaux() {
        return niveaux;
    }

    @JsonIgnore
    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
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
        return "Cycle{" + "id=" + id + ", version=" + version + ", nom=" + nom +  '}';
    }
}
