package com.exam.java.tdsi.colobane.bean;

import com.exam.java.tdsi.colobane.Colobane.Statut;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author silah
 */
@Named(value = "etat")
@ViewScoped
public class EtatCompte implements Serializable {

    @Inject
    PersonneFacade pf;
    private Long id;
    private Personne personne = new Personne();
    private boolean active = false;
    private boolean renderedTable = false;

    public void verifie() {
        Optional<Personne> p1 = pf.findAll().stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst();
        if (p1.isPresent()) {
            personne = p1.get();
            active = (personne.getStatut() == Statut.ACTIVE);
            renderedTable = true;
        } else {
            addMessage("Cette identifiant n'est pas valide !", "  ID = " + id);
        }
    }

    public void activer() {
        personne.setStatut(Statut.ACTIVE);
        pf.edit(personne);
        addMessage("Changement effectué !", "Le compte est maintenant Actif");
    }

    public void suspendre() {
        personne.setStatut(Statut.INACTIVE);
        pf.edit(personne);
        addMessage("Changement effectué !", "Le compte est maintenant inactif");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EtatCompte() {
    }

    public boolean isRenderedTable() {
        return renderedTable;
    }

    public void setRenderedTable(boolean renderedTable) {
        this.renderedTable = renderedTable;
    }

}
