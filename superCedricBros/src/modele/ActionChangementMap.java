package modele;

public class ActionChangementMap extends Action {

	
	private String destination;
	
	
	public ActionChangementMap() {}
	
	
	public ActionChangementMap(String label, String destination) {
		super(label);
		this.destination = destination;
	}
	
	
	public ActionChangementMap(String label) {
		this(label, null);
	}
	

	public String getLabel() {
		return label;
	}


	public String getDestination() {
		return destination;
	}
	
}
