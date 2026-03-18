package modele;

public class Action {
	
	//Cette classe sera utilisée pour définir les actions de nos boutons, c'est une classe mère qui aura autant de fille que de boutons de types différents
	private String label;
	

	public Action() {}
	
	
	public Action(String label) {
		
		this.label = label;
	}
	
	
	public String getLabel() {
		
		return this.label;
	}
	
	
	public void executer(GestionnaireJeu g) {}

}
