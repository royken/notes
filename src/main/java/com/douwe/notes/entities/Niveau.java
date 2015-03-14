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
@XmlRootElement(name = "niveau")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
@NamedQuery(name = "Niveau.findByCode",query = "SELECT n from Niveau n where n.code like :param")
})
public class Niveau implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column(unique = true)
    private String code;
    
    @ManyToOne
    private Cycle cycle;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Niveau(){
        
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

    @JsonIgnore
    public Cycle getCycle() {
        return cycle;
    }

    @JsonIgnore
    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
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
        return "Niveau{" + "id=" + id + ", version=" + version + ", code=" + code +  ", active=" + active + '}';
    }
    
    
   
}
