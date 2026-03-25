package modele;

public class Joueur extends Entité {
   
    private int pvActuel;
    private int pvMax;
    private Item[] inventaire = new Item[3];
    private Item inventaireQuete;
    private boolean[] quetes = {false,false,false};
    

    public Joueur() {
    }

    public Joueur(String nom,int pvActuel, int pvMax) {
     
    	super(nom);
        this.pvActuel = pvActuel;
        this.pvMax = pvMax;
        this.inventaireQuete = null;
    }

    // Méthode pour changer les PV du joueur
    public void changerPv(int changement) {
        pvActuel += changement;

        // On s'assure que les PV restent dans les limites
        if (pvActuel > pvMax) {
            pvActuel = pvMax;
        } else if (pvActuel < 0) {
            pvActuel = 0;
        }
    }
    
    
    public String consommerObjet(int index) {
        if (index < 0 || index >= 3 || this.inventaire[index] == null) {
            return null;
        }
        
        Item leObjet = this.inventaire[index];
        this.changerPv(leObjet.getPvRendue());
        this.inventaire[index] = null;
        
        return ("Cedric utilise : " + leObjet.getNom());
    }

    
    public void validerQuete(int index) {
        if (index >= 0 && index < quetes.length) {
            this.quetes[index] = true;
        }
    }
    
    
    public boolean aToutGagne() {
        for (boolean q : quetes) {
            if (!q) return false;
        }
        return true;
    }
    
    
    public boolean[] getQuetes() { 
    	
    	return quetes; 
    }
    
    
    public void setQuetes(boolean[] q) { 
    	
    	this.quetes = q; 
    }
    


	int getPvActuel() {
		
		return this.pvActuel;
	}

	int getPvMax() {
		
		return this.pvMax;
	}

	public Item[] getInventaire() {
		return inventaire;
	}

	
	
	public void ajouterItem(int i, Item objet) {
	    if (i >= 0 && i < 3) {
	        this.inventaire[i] = objet;
	    }
	}
	
	
	public void ajouterItemQuete(Item objet) {
		
		this.inventaireQuete = objet;
	}
	
	
	public void retirerItemQuete() {
		
		this.inventaireQuete = null;
	}
	
	
	public boolean aObjetQuete() {
			
		return this.inventaireQuete != null;
	}
	

	public void setPvActuel(int pvActuel) {
		
		this.pvActuel = pvActuel;
	}
	
	
	public void setNom(String nom) {
		
		this.nom = nom;
	}

	public void viderInventaire() {
		
		this.inventaire = new Item[3];
	}
}