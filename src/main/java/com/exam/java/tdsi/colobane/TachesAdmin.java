package com.exam.java.tdsi.colobane;

 
import com.exam.java.tdsi.colobane.Colobane.Categorie;
import com.exam.java.tdsi.colobane.Colobane.Fonction;
import com.exam.java.tdsi.colobane.Colobane.Statut;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 *
 * @author silah
 */
@Named(value = "tachesAdmin")
@ViewScoped
public class TachesAdmin implements Serializable {

    @Inject
    PersonneFacade pf;

    List<Personne> vendeursInactives;
    List<Personne> vendeursActives;
    List<Personne> acheteurs;
    List<Personne> vendeurs;
    private Personne personne;
    
     

    private Long id;

    @PostConstruct
    public void init() {
        
        vendeursInactives = pf.findAll().stream()
                .filter(p -> p.getFonction() == Fonction.VENDEUR)
                .filter(vendeur -> vendeur.getStatut() == Statut.INACTIVE)
                .collect(Collectors.toList());

        vendeurs = pf.findAll().stream()
                .filter(p -> p.getFonction() == Fonction.VENDEUR) 
                .collect(Collectors.toList());

        acheteurs = pf.findAll().stream().
                filter(p -> p.getFonction() == Fonction.ACHETEUR)
                .collect(Collectors.toList());
    }

    public void changerEtatCompte() {
        Personne p = pf.find(id);
        if (p != null) {
            Statut st = (p.getStatut() == Statut.ACTIVE) ? Statut.INACTIVE : Statut.ACTIVE;
            p.setStatut(st);
            pf.edit(p);
            addMessage("Modification effectuée !", "");
        } else {
            addMessage("L'identifiant sasie est incorrecte !", "");
        }
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Personne> getVendeursInactives() {
        return vendeursInactives;
    }

    public void setVendeursInactives(List<Personne> vendeursInactives) {
        this.vendeursInactives = vendeursInactives;
    }

    public List<Personne> getAcheteurs() {
        return acheteurs;
    }

    public void setAcheteurs(List<Personne> acheteurs) {
        this.acheteurs = acheteurs;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<Personne> getVendeursActives() {
        return vendeursActives;
    }

    public void setVendeursActives(List<Personne> vendeursActives) {
        this.vendeursActives = vendeursActives;
    }

    public List<Personne> getVendeurs() {
        return vendeurs;
    }

    public void setVendeurs(List<Personne> vendeurs) {
        this.vendeurs = vendeurs;
    }

    public TachesAdmin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
