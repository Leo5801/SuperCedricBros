package modele;

import java.util.ArrayList;
import java.util.List;

public class Lieu {
	
	private String nom;
	private String nomDevant; //chaque map est divisé en 2, chaque section a des actions propres mais des salles voisines communes
	private String nomDerriere;
	private String description;
	private String miniMap;
	private List<Action> actionsDevant;
	private List<Action> actionsDerriere;
	
	
	public Lieu(String nom, String description) {
		super();
		this.description = description;
		this.nom = nom;
		this.nomDevant = this.nom + "Devant";
		this.nomDerriere = this.nom + "Derriere";
		this.miniMap = "miniMap" + (this.nom.substring(0,1).toUpperCase() + this.nom.substring(1));
		this.actionsDevant = new ArrayList<>();
		this.actionsDerriere = new ArrayList<>();
	}
	
	
	public Lieu(String nom) {
		this(nom,null);
	}
	
	
	public Lieu() {}
	
	
	public void ajouterActionDevant(Action action) {
		this.actionsDevant.add(action);
	}
	
	
	public void ajouterActionDerriere(Action action) {
		this.actionsDerriere.add(action);
	}


	public String getNom() {
		return this.nom;
	}


	public List<Action> getActionsDerriere() {
		return this.actionsDerriere;
	}
	
	
	public List<Action> getActionsDevant() {
		return this.actionsDevant;
	}


	public String getDescription() {
		return description;
	}

	public String getMiniMap() {
		return miniMap;
	}

	public String getNomDevant() {
		return nomDevant;
	}

	public String getNomDerriere() {
		return nomDerriere;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
