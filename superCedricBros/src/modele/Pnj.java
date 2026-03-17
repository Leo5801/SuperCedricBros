package modele;

public class Pnj {

	String nom;
	String nomPortrait;
	String localisation;
	String[] dialogue;
	
	
	public Pnj(String nom, String nomPortrait, String localisation, String[] dialogue) {
		
		this.nom = nom;
		this.nomPortrait = nomPortrait;
		this.localisation = localisation;
		this.dialogue = dialogue;
	}
	
	
	public Pnj(String nom, String nomPortrait, String localisation) {
		
		this(nom, nomPortrait, localisation, null);
	}
}
