package modele;

import java.util.ArrayList;
import java.util.List;

public class Lieu {
	
	private String nom;
	private String description;
	private String miniMap;
	private String lieuOpposé;
	private List<Action> actions;
	
	
	public Lieu(String nom, String description, String miniMap, String mapOpposée) {
		super();
		this.description = description;
		this.nom = nom;
		this.lieuOpposé = mapOpposée;
		this.miniMap = miniMap;
		this.actions = new ArrayList<>();
	}
	
	
	public Lieu() {}
	
	
	public void ajouterAction(Action action) {
		
		this.actions.add(action);
	}
	

	public void ajouterActions(List<Action> actions) {
		
		this.actions.addAll(actions);
	}
	

	public String getNom() {
		
		return this.nom;
	}


	public List<Action> getActions() {
		
		return this.actions;
	}


	public String getDescription() {
		
		return description;
	}

	public String getMiniMap() {
		
		return miniMap;
	}


	public void setNom(String nom) {
		
		this.nom = nom;
	}

	
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	
	public String getLieuOpposé() {
		
		return this.lieuOpposé;
	}
	
}
