 
package com.exam.java.tdsi.colobane.ejb;

 
import com.exam.java.tdsi.colobane.entite.Personne;
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
public class PersonneFacade extends AbstractFacade<Personne> {

    @PersistenceContext
    private EntityManager em;

    public PersonneFacade() {
        super(Personne.class);
    }

    public PersonneFacade(Class<Personne> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

