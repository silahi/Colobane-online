package com.exam.java.tdsi.colobane;

 
import com.exam.java.tdsi.colobane.ejb.PersonneFacade;
import com.exam.java.tdsi.colobane.entite.Personne;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author silahi
 */
 
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/CoinDS",
        callerQuery = "select Mot_DE_PASSE from personne where EMAIL = ?",
        groupsQuery = "select FONCTION from personne where EMAIL = ?"
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "",
                useForwardToLogin = false               
        )
        
        
)

@FacesConfig(
        version = FacesConfig.Version.JSF_2_3
)
@Named(value = "appConfig")
@ApplicationScoped
public class AppConfig {

    @Inject
    Pbkdf2PasswordHash pph;

    @Inject
    PersonneFacade pf;

    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event) {
        if (pf.findAll().isEmpty()) {
            // Admin par défaut
            Personne p = new Personne();
            p.setNom("Colobane");
            p.setPrenom("Online");
            p.setEmail("colobaneonline@gmail.com");
            p.setTelephone("781585646");
            p.setFonction(Colobane.Fonction.ADMIN);
            p.setMot_de_passe(pph.generate("colobane".toCharArray()));
            pf.create(p);

            // Vendeur 
            p = new Personne();
            p.setNom("Kande");
            p.setPrenom("Youssouf");
            p.setEmail("kande@gmail.com");
            p.setTelephone("781585647");
            p.setFonction(Colobane.Fonction.VENDEUR);
            p.setAdresse("Fann Hock 61 X 86");
            p.setStatut(Colobane.Statut.INACTIVE);
            p.setMot_de_passe(pph.generate("colobane".toCharArray()));
            pf.create(p);

            // Acheteur
            p = new Personne();
            p.setNom("Sellou");
            p.setPrenom("Diallo");
            p.setEmail("sellou@gmail.com");
            p.setTelephone("781585648");
            p.setFonction(Colobane.Fonction.ACHETEUR);
            p.setMot_de_passe(pph.generate("colobane".toCharArray()));
            pf.create(p);
        }

    }

    public AppConfig() {
    }

}
