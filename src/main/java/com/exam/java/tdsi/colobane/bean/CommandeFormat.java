package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.Colobane.Statut;
import com.exam.java.tdsi.colobane.entite.Commande;

/**
 *
 * @author silah
 */
public class CommandeFormat {

    private Long numero;
    private int nombreArticles;
    private Statut statut;
    private float prix = 0.0f;
    private Commande commande;
  

    public CommandeFormat(Commande commande) {
        this.commande = commande;
        numero = commande.getId();
        nombreArticles = commande.getListeArticles().size();
        statut = (commande.isLivreAuClient()) ? Statut.LIVREE : Statut.ATTENTE;
        commande.getListeArticles().stream()
                .forEach(a -> {
                    prix += a.getPrix();
                });
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public int getNombreArticles() {
        return nombreArticles;
    }

    public void setNombreArticles(int nombreArticles) {
        this.nombreArticles = nombreArticles;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

}
