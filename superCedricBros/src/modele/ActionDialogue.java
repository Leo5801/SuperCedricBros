package modele;

public class ActionDialogue extends Action{
	
	//Pour cette action, on a juste besoin du texte à afficher, pas besoin d'indiquer sur quel objet cela s'applique car le lieu contient cette information et affichera le bouton
	private String texte;
	
	
	public ActionDialogue() {}
	
	
	public ActionDialogue(String label, String texte) {
		
		super(label);
		this.texte = texte;
	}
	
	
	public String getTexte() {
		
		return this.texte;
	}
	
	@Override
	public void executer(GestionnaireJeu g) {
		
		g.getFenetre().afficherTexte(this.texte);
	}
	
	
	

}
