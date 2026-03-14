package modele;

public class ActionChangementVue extends ActionDialogue {
	
	private String destination;
	
	
	public ActionChangementVue(String label, String texte, String destination) {
		
		super(label,texte);
		this.destination = destination;
	}
	
	
	public String getDestination() {
		
		return this.destination;
	}

}
