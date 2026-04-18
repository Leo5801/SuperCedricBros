package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Partie implements Serializable {
    
    private static final long serialVersionUID = 1L;
	// progession
    private String nomLieuActuel;
    private EtatJeu etatActuel;
    
    // Clé : Nom de la salle | Valeur : Liste des noms des items présents dans cette salle
    private Map<String, List<String>> emplacementItems;
    
    // Clé : Nom de la salle | Valeur : Liste des noms des PNJ présents dans cette salle
    private Map<String, List<String>> emplacementPnj;

    // Clé : Nom de la salle | Valeur : Liste des labels des actions disponibles
    
    private Map<String, List<String>> actionsParSalle;

    // joueur
    private Joueur j;
    
    // inventaire
    // On ne stocke que les NOMS (IDs) des items. 
    // Le Gestionnaire ira les rechercher dans le catalogue au chargement.
    private String[] nomsItemsInventaire;

    public Partie() {
        this.nomsItemsInventaire = new String[3];
        this.emplacementItems = new HashMap<>();
        this.emplacementPnj = new HashMap<>();
        this.actionsParSalle = new HashMap<>();
    }


    public String getNomLieuActuel() {
    	
    	return nomLieuActuel; 
    }
    
    
    public void setNomLieuActuel(String nomLieuActuel) {
    	
    	this.nomLieuActuel = nomLieuActuel; 
    }

    
    public EtatJeu getEtatActuel() { 
    	
    	return etatActuel; 
    }
    
    
    public void setEtatActuel(EtatJeu etatActuel) { 
    	
    	this.etatActuel = etatActuel; 
    }
    
    
    public String[] getNomsItemsInventaire() { 
    	
    	return nomsItemsInventaire; 
    }
    
    
    public void setNomsItemsInventaire(String[] noms) { 
    	
    	this.nomsItemsInventaire = noms; 
    }


	public Joueur getJ() {
		return j;
	}


	public void setJ(Joueur j) {
		this.j = j;
	}
	
	
	public Map<String, List<String>> getEmplacementItems() {
        return emplacementItems;
    }

    public void setEmplacementItems(Map<String, List<String>> emplacementItems) {
        this.emplacementItems = emplacementItems;
    }

    public Map<String, List<String>> getEmplacementPnj() {
        return emplacementPnj;
    }

    public void setEmplacementPnj(Map<String, List<String>> emplacementPnj) {
        this.emplacementPnj = emplacementPnj;
    }

    public Map<String, List<String>> getActionsParSalle() {
        return actionsParSalle;
    }

    public void setActionsParSalle(Map<String, List<String>> actionsParSalle) {
        this.actionsParSalle = actionsParSalle;
    }
}