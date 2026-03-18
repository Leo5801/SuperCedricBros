package modele;

public class ActionChangementVue extends Action {
	
	private String destination;
	private String texte;
	
	
	public ActionChangementVue(String label, String texte, String destination) {
		
		super(label);
		this.texte = texte;
		this.destination = destination;
	}
	
	
	public String getTexte() {
		
		return this.texte;
	}
	
	
	public String getDestination() {
		
		return this.destination;
	}
	
	
	public void executer(GestionnaireJeu g) {
		
		g.changerVue(this.destination, this.texte);
	}

	
}
