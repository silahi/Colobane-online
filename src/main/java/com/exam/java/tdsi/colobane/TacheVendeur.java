package com.exam.java.tdsi.colobane;

import com.exam.java.tdsi.colobane.Colobane.Categorie;
import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author silah
 */
@Named(value = "tacheVendeur")
@RequestScoped
public class TacheVendeur implements Serializable {

    private Article article = new Article();
    @Inject
    PersonneFacade pf;

    @Inject
    ArticleFacade af;

    private UploadedFile uploadedFile;

    private Colobane.Categorie categorie;
    List<Colobane.Categorie> categos;

    private List<Article> articles;

    @Inject
    SecurityContext securityContext;

    @PostConstruct
    public void init() {
        categos = Arrays.asList(Categorie.values());
        String email = securityContext.getCallerPrincipal().getName();
        articles = pf.findAll().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst().get().getArticles();
    }

    public void ajouterArticle(Personne p) {
        p.getArticles().add(article);
        article.setImage(uploadedFile.getContent());
        af.create(article);
        pf.edit(p);
        addMessage("Article ajouté !", article.getNom());
    }

    public Colobane.Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Colobane.Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Colobane.Categorie> getCategos() {
        return categos;
    }

    public void setCategos(List<Colobane.Categorie> categos) {
        this.categos = categos;
    }

    public TacheVendeur() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
