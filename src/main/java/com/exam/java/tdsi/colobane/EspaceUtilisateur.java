package com.exam.java.tdsi.colobane;

import com.exam.java.tdsi.colobane.ejb.ArticleFacade;
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Article;
import com.exam.java.tdsi.colobane.entite.Personne;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author silah
 */
@Named(value = "espUse")
@RequestScoped
public class EspaceUtilisateur {

    private Article article = new Article();
    private Personne personne = new Personne();
    private UploadedFile uploadedFile;
    @Inject
    PersonneFacade pf;

    @Inject
    ArticleFacade af;

    @Inject
    FacesContext facesContext;
    @Inject
    Pbkdf2PasswordHash pph;
    
     

    public void enregistrerArticle() {
        article.setImage(uploadedFile.getContent());
        article.setNom("Téléphone");
        article.setCategorie(Colobane.Categorie.Téléphone);
        article.setPrix(15000);
        Personne p = pf.find(new Long(52));
        p.getArticles().add(article);
        af.create(article);
        pf.edit(p);
    }

    public String creerCompteAdmin() {
        personne.setFonction(Colobane.Fonction.ADMIN);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Vous pouvez vous connecter et administrer la platforme !");
        return "login";
    }

    public String creerCompteAcheteur() {
        personne.setFonction(Colobane.Fonction.ACHETEUR);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Vous pouvez vous connecter et effectuer vos achats !");
        return "login";
    }

    public void creerCompteVendeur() {
        personne.setFonction(Colobane.Fonction.VENDEUR);
        personne.setStatut(Colobane.Statut.INACTIVE);
        personne.setMot_de_passe(pph.generate(personne.getMot_de_passe().toCharArray()));
        pf.create(personne);
        addMessage("Votre compte est bien créé !", "Veillez contacter l'administrateur pour activer votre compte");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void loginPage() {
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/login.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(EspaceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    public EspaceUtilisateur() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
