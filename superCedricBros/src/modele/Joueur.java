package modele;

import java.io.Serializable;

public class Joueur extends Entité implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private int pvActuel;
    private int pvMax;
    private transient Item[] inventaire = new Item[3];
    private transient Item inventaireQuete;
    private int etapeQueteEroll;
    private int etapeQueteRemi;
    private boolean[] progressionEroll = new boolean[4];
    private boolean[] progressionRemi = new boolean[0];
    private boolean aChoisiEroll;
    private boolean aChoisiRemi;
    

    public Joueur() {
    }

    public Joueur(String nom,int pvActuel, int pvMax) {
     
    	super(nom);
        this.pvActuel = pvActuel;
        this.pvMax = pvMax;
        this.inventaireQuete = null;
        this.etapeQueteEroll = 0;
        this.etapeQueteRemi = 0;
        this.aChoisiEroll = false;  
        this.aChoisiRemi = false;
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

    
    public void validerQueteEroll(int index) {
        if (index >= 0 && index < progressionEroll.length) {
            this.progressionEroll[index] = true;
        }
    }
    
    
    public boolean aToutGagne() {
        for (boolean q : progressionEroll) {
            if (!q) return false;
        }
        return true;
    }
   
	
	public void ajouterItem(int i, Item objet) {
	    if (i >= 0 && i < 3) {
	        this.inventaire[i] = objet;
	    }
	}
	
	
	public void retirerItemQuete() {
		
		this.setInventaireQuete(null);
	}

	public void ajouterItemQuete(Item i) {
		
		if(this.inventaireQuete == null) {
			this.setInventaireQuete(i);
		}
	}

	public int getPvActuel() {
		return pvActuel;
	}

	public void setPvActuel(int pvActuel) {
		this.pvActuel = pvActuel;
	}

	public int getPvMax() {
		return pvMax;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public Item[] getInventaire() {
		return inventaire;
	}

	public void setInventaire(Item[] inventaire) {
		this.inventaire = inventaire;
	}

	public Item getInventaireQuete() {
		return inventaireQuete;
	}

	public void setInventaireQuete(Item inventaireQuete) {
		this.inventaireQuete = inventaireQuete;
	}

	public int getEtapeQueteEroll() {
		return etapeQueteEroll;
	}

	public void setEtapeQueteEroll(int etapeQueteEroll) {
		this.etapeQueteEroll = etapeQueteEroll;
	}

	public int getEtapeQueteRemi() {
		return etapeQueteRemi;
	}

	public void setEtapeQueteRemi(int etapeQueteRemi) {
		this.etapeQueteRemi = etapeQueteRemi;
	}

	public boolean[] getProgressionEroll() {
		return progressionEroll;
	}

	public void setProgressionEroll(boolean[] progressionEroll) {
		this.progressionEroll = progressionEroll;
	}

	public boolean[] getProgressionRemi() {
		return progressionRemi;
	}

	public void setProgressionRemi(boolean[] progressionRemi) {
		this.progressionRemi = progressionRemi;
	}

	public boolean isaChoisiEroll() {
		return aChoisiEroll;
	}

	public void setaChoisiEroll(boolean aChoisiEroll) {
		this.aChoisiEroll = aChoisiEroll;
	}

	public boolean isaChoisiRemi() {
		return aChoisiRemi;
	}

	public void setaChoisiRemi(boolean aChoisiRemi) {
		this.aChoisiRemi = aChoisiRemi;
	}

	
	
	
	
}