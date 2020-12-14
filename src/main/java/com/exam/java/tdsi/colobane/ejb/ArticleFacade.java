 
package com.exam.java.tdsi.colobane.ejb;

 
import com.exam.java.tdsi.colobane.entite.Article;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author silah 
 */
@Stateless
@Startup
public class ArticleFacade extends AbstractFacade<Article> {

    @PersistenceContext
    private EntityManager em;

    public ArticleFacade() {
        super(Article.class);
    }

    public ArticleFacade(Class<Article> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
