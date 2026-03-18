package modèle;

public class Joueur extends Entité {
   
    private int pvActuel;
    private int pvMax;

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

    
    public void consommerItem(/* Item item */) {
    	
        // Pseudo-code : vérifier si l'item est une boisson
        // if (item est une boisson) {

    	
    	
    	
    	
    	
        // Condition 1 : les PV actuels doivent être inférieurs au PV max
        if (pvActuel < pvMax) {
    
            // Condition 2 : le joueur consomme une boisson
            // Exemple : if( item.getType() == "boisson")
        	
        	
        	
            pvActuel += 1; // le joueur regagne 1 PV
            
            
            
            if (pvActuel == pvMax) {
                pvActuel = pvMax; // on ne dépasse jamais le PV max
            }
        }

        // }
    }

	String getPvActuel() {
		
		return null;
	}

	String getPvMax() {
		
		return null;
	}
}