package modele;

public class Pnj {

	private String nom;
	private String nomPortrait;
	private String[] dialogue;
	
	
	public Pnj(String nom, String nomPortrait, String[] dialogue) {
		
		this.nom = nom;
		this.nomPortrait = nomPortrait;
		this.dialogue = dialogue;
	}
	
	
	public Pnj() {}


	public String getNom() {
		return nom;
	}


	public String getNomPortrait() {
		return nomPortrait;
	}


	public String[] getDialogue() {
		return dialogue;
	}


	public void setDialogue(String[] dialogue) {
		this.dialogue = dialogue;
	}
}
