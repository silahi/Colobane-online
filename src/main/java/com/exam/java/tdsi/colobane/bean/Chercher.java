package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.EspaceUtilisateur;
import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "chercher")
@SessionScoped
public class Chercher implements Serializable {

    private String motCle;
    private List<Article> articlesTrouves = new ArrayList(); 
    @Inject
    ArticleFacade af;

    @Inject
    FacesContext facesContext;

    public void cherche() {
        articlesTrouves = af.findAll().stream() 
                .filter(a-> a.getCategorie() != null)
                .filter(article -> (article.getNom().toLowerCase().contains(motCle))
                || article.getCategorie().toString().toLowerCase().contains(motCle))
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

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    } 

    public Chercher() {
    }

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }

    public List<Article> getArticlesTrouves() {
        return articlesTrouves;
    }

    public void setArticlesTrouves(List<Article> articlesTrouves) {
        this.articlesTrouves = articlesTrouves;
    }

}
