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
@Entity(name = "options")
@XmlRootElement(name = "option")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "Option.findDepartement", query = "select d from Departement d where d.id = :idParam"),
        @NamedQuery(name = "Option.findByCode",query = "SELECT o from options o WHERE o.code like :param"),
        @NamedQuery(name = "Option.findAllActive",query = "SELECT o from options o WHERE o.active=1")
})
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @XmlTransient
    private int version;

    @Column(unique = true)
    private String code;

    @Column
    private String description;

    @ManyToOne
    private Departement departement;

    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;

    public Option() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Departement getDepartement() {
        return departement;
    }

    @JsonIgnore
    public void setDepartement(Departement departement) {
        this.departement = departement;
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
        return "Option{" + "id=" + id + ", version=" + version + ", code=" + code + ", description=" + description + ", departement=" + departement + ", active=" + active + '}';
    }

}
