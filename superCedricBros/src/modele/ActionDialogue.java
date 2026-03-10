package modele;

public class ActionDialogue extends Action{
	
	protected String texte;
	
	
	protected ActionDialogue(String label, String texte) {
		
		super(label);
		this.texte = texte;
	}
	
	
	protected ActionDialogue(String label) {
		
		this(label, null);
	}
	
	
	protected ActionDialogue() {}
	
	
	protected String getTexte() {
		
		return this.texte;
	}
	
	
	protected void setString(String texte) {
		
		this.texte = texte;
	}

}
