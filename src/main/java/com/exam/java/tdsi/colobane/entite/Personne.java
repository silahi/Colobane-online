package com.exam.java.tdsi.colobane.entite;

 
import com.exam.java.tdsi.colobane.Colobane.Fonction;
import com.exam.java.tdsi.colobane.Colobane.Statut;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author silah
 */
@Entity
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 15, message = "Le nom n'est pas valide !")
    @NotNull(message = "Il faut saisir le nom !")
    private String nom;

    @Size(min = 2, max = 15, message = "Le prénom n'est pas valide !")
    @NotNull(message = "Il faut saisir le prénom !")
    private String prenom;

    @Size(min = 5, max = 20, message = "Le numéro de téléphone n'est pas valide !")
    @NotNull(message = "Il faut saisir le numéro de téléphone !")
    private String telephone;

    @Pattern(regexp = "[\\w\\.]+@\\w+(\\.\\w+)+", message = "L'adresse email n'est pas valide !")
    @NotNull(message = "Il faut saisir l'adresse email !")
    @Column(unique = true)
    private String email;

   
    private String adresse;

    @Size(min = 6, max = 256, message = "Le numéro de passe n'est pas valide !")
    @NotNull(message = "Il faut saisir le mot de passe !")
    private String mot_de_passe;

    @Enumerated(value = EnumType.STRING)
    private Fonction fonction;

    private Statut statut;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Commandes_des_Acheteurs",
            joinColumns = @JoinColumn(name = "Acheteur"),
            inverseJoinColumns = @JoinColumn(name = "Numero_de_Commande"))
    private List<Commande> commandes = new ArrayList();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Articles_Des_Vendeurs",
            joinColumns = @JoinColumn(name = "Vendeur"),
            inverseJoinColumns = @JoinColumn(name = "Article"))
    private List<Article> articles = new ArrayList();

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
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
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.online.colobane.entites.Personne[ id=" + id + " ]";
    }

}
