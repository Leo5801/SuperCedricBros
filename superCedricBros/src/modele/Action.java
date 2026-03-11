package modele;

public class Action {
	
	//Cette classe sera utilisée pour définir les actions de nos boutons, c'est une classe mère qui aura autant de fille que de boutons de types différents
	protected String label;
	

	protected Action() {}
	
	
	protected Action(String label) {
		
		this.label = label;
	}
	
	
	protected void setLabel(String label) {
		
		this.label = label;
	}
	
	
	protected String getLabel() {
		
		return this.label;
	}

}
