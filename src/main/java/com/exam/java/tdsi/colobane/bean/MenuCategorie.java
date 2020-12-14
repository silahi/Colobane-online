package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author silah
 */
@Named(value = "menu")
@RequestScoped
public class MenuCategorie {

    private MenuModel model;
    @Inject
    ArticleFacade af;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        List<String> categos = new ArrayList();
        af.findAll().stream()
                .forEach(article -> {
                    if (article.getCategorie() != null) {
                        if (!categos.stream().filter(cat -> cat.equals(article.getCategorie().toString()))
                                .findFirst().isPresent()) {
                            categos.add(article.getCategorie().toString());
                            Map<String, List<String>> map = new HashMap();
                            map.put("categorie", Arrays.asList(article.getCategorie().toString()));
                            DefaultMenuItem item = DefaultMenuItem.builder()
                                    .value(article.getCategorie().toString())
                                    .url("/colobane/colobane/categorie.xhtml")
                                    .includeViewParams(true)
                                    .params(map)
                                    .id(article.getCategorie().toString())
                                    .build();
                            model.getElements().add(item);
                        }
                    }
                });
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public MenuCategorie() {
    }
}
