package modele;

public class Vue {

	//ce sont des "maps" plus simplifiées, juste des vues pour rendre le jeu plus vivant : par exemple l'écran d'eroll.
	private String nom;
	private String description;
	private String labelBouton;
	
	
	public Vue(String nom, String description, String labelBouton) {
		super();
		this.nom = nom;
		this.description = description;
		this.labelBouton = labelBouton;
	}


	public String getNom() {
		return nom;
	}


	public String getDescription() {
		return description;
	}


	public String getLabelBouton() {
		return labelBouton;
	}
	
	
	
}
