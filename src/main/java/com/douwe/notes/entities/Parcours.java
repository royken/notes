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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Entity
@XmlRootElement(name = "parcours")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Parcours.deleteActive", query = "update Parcours p set p.active = 0 where p.id = :idParam"),
    @NamedQuery(name = "Parcours.findAllActive", query = "select p from Parcours p where p.active=1"),
    @NamedQuery(name = "Parcours.findByNiveauOption", query = "SELECT p from Parcours p WHERE p.niveau.id = :param1 and p.option.id = :param2")

})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"NIVEAU_ID","OPTION_ID"}))
public class Parcours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @XmlTransient
    private int version;

    @ManyToOne(optional = false)
    @JoinColumn(name = "NIVEAU_ID")
    private Niveau niveau;

    @ManyToOne(optional = false)
    @JoinColumn(name = "OPTION_ID")
    private Option option;

//    @ManyToMany
//    @XmlTransient
//    private List<UniteEnseignement> uniteEnseignements;

    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;

    public Parcours() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

//    public List<UniteEnseignement> getUniteEnseignements() {
//        return uniteEnseignements;
//    }
//
//    public void setUniteEnseignements(List<UniteEnseignement> uniteEnseignements) {
//        this.uniteEnseignements = uniteEnseignements;
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

    @Override
    public String toString() {
        return "Parcours{" + "id=" + id + ", version=" + version +  ", option=" + option  + ", active=" + active + '}';
    }

}
