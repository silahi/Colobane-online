package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.EspaceUtilisateur;
import com.exam.java.tdsi.colobane.InfoSession;
import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author silah
 */
@Named(value = "service")
@RequestScoped
public class ServiceCommun {

    @Inject
    FacesContext facesContext;

    @Inject
    PersonneFacade pf;

    @Inject
    ArticleFacade af;

    private String motCle;
    private List<Article> articles = new ArrayList();
    private Article Article = new Article();
    private Personne personne = new Personne();

    public void cherche() {
        articles = af.findAll().stream()
                .filter(art -> (art.getNom().toLowerCase().contains(motCle))
                || art.getCategorie().toString().toLowerCase().contains(motCle))
                .collect(Collectors.toList());
        resultPage();
    }

    public void resultPage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/colobane/recherche.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void vendeurs() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/vendeurs.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void adminHome() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/home.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void administration() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/admin.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      public void effectuerCommande(){
          
      }

    public void acheteurs() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/acheteurs.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commandes() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/commandes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commissions() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/commissions.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void activer() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/admin/activer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void monPanier() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/colobane/mon-panier.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String logout() {
        try {
            ExternalContext ec = facesContext.getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
        } catch (ServletException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le serveur a rencontrer un probleme lors de la déconnexion", null));
            Logger.getLogger(InfoSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/colobane/login.xhtml?faces-redirect=true";
    }

    public void homePage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/colobane/colobane.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loginPage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/colobane/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Article getArticle() {
        return Article;
    }

    public void setArticle(Article Article) {
        this.Article = Article;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public ServiceCommun() {
    }

}
