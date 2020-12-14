package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "categorie")
@RequestScoped
public class Categorie {

    @Inject
    ArticleFacade af;

    private List<Article> articles = new ArrayList();

    @Inject
    FacesContext context;

    @PostConstruct
    public void init() {
        String categorie = context.getExternalContext().getRequestParameterMap().get("categorie");
        articles = af.findAll().stream()
                .filter(a -> a.getCategorie() != null)
                .filter(article -> article.getCategorie().toString().equals(categorie))
                .collect(Collectors.toList());

    }

    public Categorie() {
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
