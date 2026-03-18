package modele;

public class Compte {
	    private String pseudo;
	    private String motDePasse;

	    public Compte(String pseudo, String motDePasse) {
	        this.pseudo = pseudo;
	        this.motDePasse = motDePasse;
	    }

	    // Vérifier si le pseudo et le mot de passe sont corrects
	    public boolean seConnecter(String pseudoSaisi, String motDePasseSaisi) {
	        if (this.pseudo.equals(pseudoSaisi) && this.motDePasse.equals(motDePasseSaisi)) {
	            System.out.println("Connexion réussie ! Bienvenue " + pseudo);
	            return true;
	        } else {
	            System.out.println("Pseudo ou mot de passe incorrect.");
	            return false;
	        }
	    }

	    public String getPseudo() {
	        return pseudo;
	    }

	    public String getMDP() {
	        return motDePasse;
	    }

	    public void setPseudo(String pseudo) {
	        this.pseudo = pseudo;
	    }

	    public void setMDP(String motDePasse) {
	        this.motDePasse = motDePasse;
	    }
	    //Est ce que on va faire une interface de connexion pour se connecter au jeu.Si oui ,on doit avoir une BD pour se connecter
	    
	}

