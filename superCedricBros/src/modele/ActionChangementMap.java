package modele;

public class ActionChangementMap extends Action {

	//pour le changement de map, on a besoin que d'une seule information en plus : la destination
	private Lieu destination;
	
	
	public ActionChangementMap() {}
	
	
	public ActionChangementMap(String label, Lieu destination) {
		super(label);
		this.destination = destination;
	}
	
	
	public void setDestination(Lieu destination) {
		
		this.destination = destination;
	}
	
	
	public Lieu getDestination() {
		return destination;
	}
	
}
