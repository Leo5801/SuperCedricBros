package modele;

public class ActionChangementMap extends Action {

	//pour le changement de map, on a besoin que d'une seule information en plus : la destination
	private String destination;
	
	
	public ActionChangementMap() {}
	
	
	public ActionChangementMap(String label, String destination) {
		super(label);
		this.destination = destination;
	}
	
	
	public void setDestination(String destination) {
		
		this.destination = destination;
	}
	
	
	public String getDestination() {
		return destination;
	}
	
}
