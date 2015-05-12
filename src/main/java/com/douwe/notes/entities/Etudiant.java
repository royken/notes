package com.douwe.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@XmlRootElement(name = "etudiant")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
@NamedQuery(name = "Etudiant.deleteActive",query = "update Etudiant e set e.active = 0 where e.id = :idParam"),
@NamedQuery(name = "Etudiant.findAllActive",query = "select e from Etudiant e where e.active=1"),    
@NamedQuery(name = "Etudiant.findByMatricule",query = "SELECT e from Etudiant e WHERE e.matricule like :param")
})
public class Etudiant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    @XmlTransient
    private int version;
    
    @Column(unique = true)
    private String matricule;
    
    @Column(nullable = false)
    private String nom;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean validDate;
    
    @Column
    private String lieuDeNaissance;
    
    @Column
    private String email;
    
    @Column
    private String numeroTelephone;
    
    @Column
    private Genre genre;
    
     @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Etudiant(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getLieuDeNaissance() {
        return lieuDeNaissance;
    }

    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public boolean isValidDate() {
        return validDate;
    }

    public void setValidDate(boolean validDate) {
        this.validDate = validDate;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "id=" + id + ", version=" + version + ", matricule=" + matricule + ", nom=" + nom + ", dateDeNaissance=" + dateDeNaissance + ", lieuDeNaissance=" + lieuDeNaissance + ", email=" + email + ", numeroTelephone=" + numeroTelephone + ", genre=" + genre + ", active=" + active + '}';
    }
    
    
}
