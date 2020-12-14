package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.Colobane;
import com.exam.java.tdsi.colobane.ejb.CommandeFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Commande;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;

/**
 *
 * @author silah
 */
@Named(value = "user")
@RequestScoped
public class Utilisateur {

    private Colobane.Fonction fonction;

    @Inject
    PersonneFacade pf;

    @Inject
    SecurityContext securityContext;

    @Inject
    FacesContext facesContext;

    private String username;

    private boolean connected = false;
    private String email = null;

    Optional<Personne> pers;

    @Inject
    Panier panier;

    @Inject
    CommandeFacade cf;

    @PostConstruct
    public void init() {
        try {
            if (securityContext.getCallerPrincipal() != null) {
                email = securityContext.getCallerPrincipal().getName();

                pers = pf.findAll().stream()
                        .filter(p -> p.getEmail().equals(email))
                        .findFirst();
                pers.ifPresent(p -> {
                    fonction = p.getFonction();
                    username = pers.get().getNom();
                    connected = true;
                });
            }
        } catch (NullPointerException e) {
            connected = false;
            Logger.getLogger(Utilisateur.class.getName()).log(Level.INFO, null, e);
        }

    }

    public void effectuerCommande() {
        if (connected) {
            Personne p = pers.get();
            if (p.getFonction() == Colobane.Fonction.ACHETEUR) {
                Commande comd = new Commande();
                comd.setDateCommande(new Date());
                comd.setListeArticles(panier.getMesArticles());
                comd.setId_acheteur(p.getId());
                comd.setLivreAuClient(false);
                p.getCommandes().add(comd);
                pf.edit(p);
                cf.create(comd);
                addMessage("Votre commande est en attente !", "Le point de livraison apparaitera dans votre espave acheteur!");
                panier.getMesArticles().clear();
                loginPage();
            } else {
                loginPage();
            }
        } else {
            loginPage();
        }
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void loginPage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/colobane/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void homePage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                    + "/" + pers.get().getFonction().toString().toLowerCase() + "/home.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    public Colobane.Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Colobane.Fonction fonction) {
        this.fonction = fonction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Utilisateur() {
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
