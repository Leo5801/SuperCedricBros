package modele;



public class Entité {

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