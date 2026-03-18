package modele;


public class Joueur extends Entité {
   
    private int pvActuel;
    private int pvMax;
    private Item[] inventaire = new Item[3];

    public Joueur() {
    }

    public Joueur( int pvActuel, int pvMax) {
     
        this.pvActuel = pvActuel;
        this.pvMax = pvMax;
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

    


	int getPvActuel() {
		
		return this.pvActuel;
	}

	int getPvMax() {
		
		return this.pvMax;
	}

	public Item[] getInventaire() {
		return inventaire;
	}

	public void setInventaire(Item[] inventaire) {
		this.inventaire = inventaire;
	}
	
	
	public void ajouterItem(int i, Item objet) {
		
		this.inventaire[i] = objet;
	}
}