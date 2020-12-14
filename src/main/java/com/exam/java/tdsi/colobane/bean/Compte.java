package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.Colobane;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author silah
 */
@Named(value = "compte")
@RequestScoped
public class Compte implements Serializable {

    @Inject
    PersonneFacade pf;

    @Inject
    Pbkdf2PasswordHash pph;

    @Inject
    ServiceCommun sc;

    private Personne personne = new Personne();

    public String creerCompteAdmin() {
        personne.setFonction(Colobane.Fonction.ADMIN);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Vous pouvez vous connecter et administrer la platforme !");
        return "login";
    }

    public void modifierAdmin() {
        personne.setFonction(Colobane.Fonction.ADMIN);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        Personne p1 = pf.findAll().stream().filter(p -> p.getEmail().equals("colobaneonline@gmail.com"))
                .findFirst().get();
        p1.setNom(personne.getNom());
        p1.setPrenom(personne.getPrenom());
        p1.setTelephone(personne.getTelephone());
        p1.setMot_de_passe(personne.getMot_de_passe());
        p1.setEmail(personne.getEmail());
        pf.edit(p1);
        addMessage("Modifications prises en compte !", "");
        sc.logout();
    }

    public String creerCompteAcheteur() {
        personne.setFonction(Colobane.Fonction.ACHETEUR);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Vous pouvez vous connecter et effectuer vos achats !");
        return "login";
    }

    public void creerCompteVendeur() {
        personne.setFonction(Colobane.Fonction.VENDEUR);
        personne.setStatut(Colobane.Statut.INACTIVE);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Veillez contacter l'administrateur pour activer votre compte");
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

    public Compte() {
    }

}
