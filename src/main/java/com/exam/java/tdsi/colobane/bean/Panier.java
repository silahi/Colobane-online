package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.CommandeFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import com.exam.java.tdsi.colobane.entite.Commande;
import com.exam.java.tdsi.colobane.entite.Personne;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "panier")
@SessionScoped
public class Panier implements Serializable { 

    private List<Article> mesArticles = new ArrayList();

    @Inject
    PersonneFacade pf;

    @Inject
    CommandeFacade cf;

    private Personne personne = new Personne();

    @Inject
    FacesContext facesContext;

    public Panier() {
    }

    public void ajouter(Article article) {
        mesArticles.add(article);
        addMessage(article.getNom() + " est ajouté dans le panier !", "");
    }

    public void retirer(Article article) {
        List<Article> ls = new ArrayList();
        mesArticles.stream().forEach(a -> {
            if (!Objects.equals(a.getId(), article.getId())) {
                ls.add(a);
            }
        });
        mesArticles = ls;
        addMessage(article.getNom() + " est retiré du  panier !", "");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void effectuerCommande() {

        Optional<Personne> p = pf.findAll().stream()
                .filter(per -> per.getEmail().equals(personne.getEmail()))
                .findFirst();
        if (p.isPresent()) {
            Commande com = new Commande();
            com.setDateCommande(new Date());
        } else {
            addMessage("Identifiant ou mot de passe incorrecte !", "");
        }
    }

    public List<Article> getMesArticles() {
        return mesArticles;
    }

    public void setMesArticles(List<Article> mesArticles) {
        this.mesArticles = mesArticles;
    }
 

}
