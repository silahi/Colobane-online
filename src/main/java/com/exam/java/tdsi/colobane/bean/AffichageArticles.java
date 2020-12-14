package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "articles")
@RequestScoped
public class AffichageArticles {

    @Inject
    ArticleFacade af;

    private List<Article> articles;

    @PostConstruct
    public void init() {
        articles = af.findAll().parallelStream()
                .filter(a -> a.getCategorie() != null)
                .collect(Collectors.toList());
                

    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public AffichageArticles() {
    }

}
