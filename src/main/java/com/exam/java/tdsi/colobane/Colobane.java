package com.exam.java.tdsi.colobane;

import java.io.Serializable;

/**
 *
 * @author silahi
 */
public abstract class Colobane implements Serializable {

    public enum Fonction {
        ADMIN, VENDEUR, ACHETEUR
    }

    public enum Categorie {
        Eléctroménager, Téléphone, Télévision,
        Parfum, Habillement, Cosmétique,
        Eléctronique, Informatique,Mode,
        Alimentation,Boisson,Maison_et_Bureu,
        Produits_pour_bébés,Santé_et_beauté,Sports
    }
    
    public enum Statut {
        ACTIVE, INACTIVE,ATTENTE,LIVREE
    }
     
}
