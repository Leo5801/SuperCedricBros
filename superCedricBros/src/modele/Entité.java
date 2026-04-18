package modele;

import java.io.Serializable;

public class Entité implements Serializable{

    
	private static final long serialVersionUID = 1L;
	protected String nom;

    public Entité(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    public Entité() {
    	
    }
}