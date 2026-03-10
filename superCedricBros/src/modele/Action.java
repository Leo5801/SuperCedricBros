package modele;

public class Action {
	
	protected String label;
	
	
	protected Action(String label) {
		
		this.label = label;
	}
	
	
	protected Action() {}
	
	
	protected void setLabel(String label) {
		
		this.label = label;
	}
	
	
	protected String getLabel() {
		
		return this.label;
	}

}
