package modele;

public class ActionChangementVue extends Action {
	
	private Vue destination;
	
	
	public ActionChangementVue(String label, Vue destination) {
		
		super(label);
		this.destination = destination;
	}
	
	
	public Vue getDestination() {
		
		return this.destination;
	}
	

	public void executer(GestionnaireJeu g) {
		
		g.gererVue(this.destination);;
	}

	
}
