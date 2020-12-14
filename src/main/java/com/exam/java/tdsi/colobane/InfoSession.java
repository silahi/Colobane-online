package com.exam.java.tdsi.colobane;

import com.exam.java.tdsi.colobane.Colobane.Fonction;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.IOException;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author silah
 */
@Named(value = "infoSession")
@RequestScoped
public class InfoSession {

    private Optional<Personne> currentPersonne;
    private Fonction fonction;

    @Inject
    PersonneFacade pf;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;
    private String messageButton;

    @PostConstruct
    public void init() {
        try {
            String email = securityContext.getCallerPrincipal().getName();
            currentPersonne = pf.findAll().stream()
                    .filter(p -> p.getEmail().equals(email))
                    .findFirst();
            currentPersonne.ifPresent(p -> {
                fonction = p.getFonction();
                messageButton = currentPersonne.get().getNom();
            });
        } catch (Exception e) {
            Logger.getLogger(InfoSession.class.getName()).log(Level.INFO, null, e);
        }

    }

    private boolean renderRole = (currentPersonne != null);

    public String logout() {
        try {
            ExternalContext ec = facesContext.getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
        } catch (ServletException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le serveur a rencontrer un probleme lors de la déconnexion", null));
            Logger.getLogger(InfoSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/login.xhtml?faces-redirect=true";
    }

    public void homePage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    public InfoSession() {
    }

    public String getMessageButton() {
        return messageButton;
    }

    public void setMessageButton(String messageButton) {
        this.messageButton = messageButton;
    }

    public boolean isRenderRole() {
        return renderRole;
    }

    public void setRenderRole(boolean renderRole) {
        this.renderRole = renderRole;
    }

    public Optional<Personne> getCurrentPersonne() {
        return currentPersonne;
    }

    public void setCurrentPersonne(Optional<Personne> currentPersonne) {
        this.currentPersonne = currentPersonne;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

}
