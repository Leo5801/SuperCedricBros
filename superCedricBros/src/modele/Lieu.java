package modele;

import java.util.ArrayList;
import java.util.List;

public class Lieu {
	
	private String nom;
	private String description;
	private String miniMap;
	private String labelBouton;
	private Lieu lieuOpposé;
	private List<Action> actions;
	private List<Lieu> lieuxVoisins;
	private List<Pnj> persos;
	private List<Item> objets;
	
	
	public Lieu(String nom, String description, String miniMap, String labelBouton) {
		super();
		this.nom = nom;
		this.description = description;
		this.miniMap = miniMap;
		this.labelBouton = labelBouton;
		this.lieuOpposé = null;
		this.actions = new ArrayList<>();
		this.lieuxVoisins = new ArrayList<>();
		this.persos = new ArrayList<>();
		this.objets = new ArrayList<>();
	}
	
	
	public Lieu() {}
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}


	public String getMiniMap() {
		return miniMap;
	}


	public Lieu getLieuOpposé() {
		return lieuOpposé;
	}


	public void setLieuOpposé(Lieu lieuOpposé) {
		this.lieuOpposé = lieuOpposé;
	}


	public List<Action> getActions() {
		return actions;
	}


	public List<Lieu> getLieuxVoisins() {
		return lieuxVoisins;
	}
	
	
	public List<Pnj> getPersos() {
		return persos;
	}
	
	
	public void setLieuxVoisins(List<Lieu> lieuxVoisins) {
		this.lieuxVoisins = lieuxVoisins;
	}
	
	
	public String getLabelBouton() {
		return this.labelBouton;
	}
	
	
	public List<Item> getObjets(){
		
		return this.objets;
	}

	
	public void ajouterPnj(Pnj nouveau) {
		
		this.persos.add(nouveau);
	}
	
	
	public void retirerPnj(Pnj nouveau) {
		
		this.persos.remove(nouveau);
	}
	

	public void ajouterLieuxVoisons(Lieu lieuxVoisins) {
		this.lieuxVoisins.add(lieuxVoisins);
	}


	public void ajouterAction(Action action) {
		
		this.actions.add(action);
	}
	

	public void ajouterActions(List<Action> actions) {
		
		this.actions.addAll(actions);
	}
	
	
	public void ajouterObjet(Item nouveau) {
		
		this.objets.add(nouveau);
	}
	
	
	public void retirerObjet(Item retrait) {
		this.objets.remove(retrait);
	}


	public void retirerObjetParNom(Item i) {
		
		this.objets.remove(i);
	}
	
	
}
