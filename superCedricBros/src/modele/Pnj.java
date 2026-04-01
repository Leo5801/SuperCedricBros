package modele;

import java.util.Random;

public class Pnj {

	private String nom;
	private String nomPortrait;
	private String[] dialogue;
	
	
	public Pnj(String nom, String nomPortrait, String[] dialogue) {
		
		this.nom = nom;
		this.nomPortrait = nomPortrait;
		this.dialogue = dialogue;
	}
	
	
	public Pnj(String nom, String nomPortrait) {
		this(nom, nomPortrait, new String[0]);
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
	
	
	public String parler() {
		
		Random aleatoire = new Random();
		int parole = aleatoire.nextInt(this.dialogue.length);
		
		return this.dialogue[parole];
	}
}
