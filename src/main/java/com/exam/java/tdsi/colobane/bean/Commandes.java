package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.CommandeFacade;
import com.exam.java.tdsi.colobane.entite.Commande;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "commandes")
@RequestScoped
public class Commandes {

    @Inject
    CommandeFacade cf;
    private List<Commande> commandes;

    @PostConstruct
    public void init() {
           commandes = cf.findAll();
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
 
    public Commandes() {
    }

}
