package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.Colobane.Statut;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author silah
 */
@Named(value = "connexion")
@RequestScoped
public class Connexion implements Serializable {

    private String email;
    private String motDePasse;

    @Inject
    private FacesContext facesContext;

    @Inject
    PersonneFacade cm;

    @Inject
    private SecurityContext securityContext;

    public void execute() {
        switch (procesAuthenticationStatus()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Identifiant ou mot de passe incorrect !", null));
                break;
            case SUCCESS:
                Personne p;
                try {
                    p = cm.findAll().stream()
                            .filter(personne -> personne.getEmail().equals(email))
                            .findFirst().get();
                    switch (p.getFonction()) {
                        case ADMIN:
                            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/home.xhtml?faces-redirect=true");
                            break;
                        case VENDEUR:
                            if (p.getStatut().equals(Statut.INACTIVE)) {
                                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre Compte est inactive !", null));
                            } else {
                                getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/vendeur/home.xhtml?faces-redirect=true");
                            }
                            break;
                        case ACHETEUR:
                            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/acheteur/home.xhtml?faces-redirect=true");
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException e) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Identifiant ou mot de passe incorrect !", null));
                } catch (IOException ie) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le serveur a rencontré un problème ! ", null));
                    Logger.getLogger(Connexion.class.getName()).log(Level.INFO, "Erreur lors de l''authentification : {0}", ie.getMessage());
                }

                break;
        }
    }

    public String logout() {
        try {
            ExternalContext ec = facesContext.getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
        } catch (ServletException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le serveur a rencontrer un probleme lors de la déconnexion", null));
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/login.xhtml?faces-redirect=true";
    }

    private ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    private AuthenticationStatus procesAuthenticationStatus() {
        ExternalContext ec = getExternalContext();
        return securityContext.authenticate((HttpServletRequest) ec.getRequest(),
                (HttpServletResponse) ec.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(email, motDePasse)));
    }

    public Connexion() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
