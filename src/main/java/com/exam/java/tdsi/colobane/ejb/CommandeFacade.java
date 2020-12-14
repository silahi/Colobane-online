 
package com.exam.java.tdsi.colobane.ejb;

 
import com.exam.java.tdsi.colobane.entite.Commande;
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
public class CommandeFacade extends AbstractFacade<Commande> {

    @PersistenceContext
    private EntityManager em;

    public CommandeFacade() {
        super(Commande.class);
    }

    public CommandeFacade(Class<Commande> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
