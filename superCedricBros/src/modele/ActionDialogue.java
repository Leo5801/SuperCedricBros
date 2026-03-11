package modele;

public class ActionDialogue extends Action{
	
	//Pour cette action, on a juste besoin du texte à afficher, pas besoin d'indiquer sur quel objet cela s'applique car le lieu contient cette information et affichera le bouton
	protected String texte;
	
	
	protected ActionDialogue() {}
	
	
	protected ActionDialogue(String label, String texte) {
		
		super(label);
		this.texte = texte;
	}
	
	
	protected String getTexte() {
		
		return this.texte;
	}
	
	
	protected void setTexte(String texte) {
		
		this.texte = texte;
	}

}
