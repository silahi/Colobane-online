package com.exam.java.tdsi.colobane.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author silah
 */
@Entity
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long id_acheteur;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCommande;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Articles_Commandes",
            joinColumns = @JoinColumn(name = "Numero_de_Commande"),
            inverseJoinColumns = @JoinColumn(name = "Article")) 
    private List<Article> listeArticles = new ArrayList();

    private boolean livreAuClient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public boolean isLivreAuClient() {
        return livreAuClient;
    }

    public void setLivreAuClient(boolean livreAuClient) {
        this.livreAuClient = livreAuClient;
    }

    public long getId_acheteur() {
        return id_acheteur;
    }

    public List<Article> getListeArticles() {
        return listeArticles;
    }

    public void setListeArticles(List<Article> listeArticles) {
        this.listeArticles = listeArticles;
    }

    public void setId_acheteur(long id_acheteur) {
        this.id_acheteur = id_acheteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.online.colobane.entites.Commande[ id=" + id + " ]";
    }

}
