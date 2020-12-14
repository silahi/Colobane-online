package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.ejb.CommandeFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Commande;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;

/**
 *
 * @author silah
 */
@Named(value = "acheteur")
@RequestScoped
public class Acheteur {

    @Inject
    SecurityContext securityContext;

    @Inject
    CommandeFacade cf;

    @Inject
    PersonneFacade pf;

    List<Commande> mesCommandes = new ArrayList();
    private List<CommandeFormat> mesCommandesAttente = new ArrayList();
    private List<CommandeFormat> mesCommandesLivre = new ArrayList();

    @PostConstruct
    public void init() {
        String email = securityContext.getCallerPrincipal().getName();
        Personne personne = pf.findAll().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst().get();
        mesCommandes = personne.getCommandes();

          mesCommandes.stream()
                .filter(c -> c.isLivreAuClient())
                .forEach(c -> { 
                    mesCommandesLivre.add(new CommandeFormat(c));
                });
             

          mesCommandes.stream()
                .filter(c -> !c.isLivreAuClient())
                 .forEach(c -> { 
                    mesCommandesAttente.add(new CommandeFormat(c));
                });

    }

    public List<CommandeFormat> getMesCommandesLivre() {
        return mesCommandesLivre;
    }

    public void setMesCommandesLivre(List<CommandeFormat> mesCommandesLivre) {
        this.mesCommandesLivre = mesCommandesLivre;
    }

    public List<CommandeFormat> getMesCommandesAttente() {
        return mesCommandesAttente;
    }

    public void setMesCommandesAttente(List<CommandeFormat> mesCommandesAttente) {
        this.mesCommandesAttente = mesCommandesAttente;
    } 

    public Acheteur() {
    }

}
