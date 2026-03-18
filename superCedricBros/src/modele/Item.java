package modele;

public class Item {
	private String nom;
	private int pvRendue;
	private boolean itemDeQuete;

//item pour gagner des vies 
	public Item(String nom, int pvRendue, boolean itemDeQuete) {
		this.nom = nom;
		this.pvRendue = pvRendue;
		this.itemDeQuete = itemDeQuete;
	}

	// Constructeur simplifié pour les items basiques:la casquette de madame michu
	// pour debloquer la suite dune quete
	public Item(String nom) {
		this.nom = nom;
		this.pvRendue = 0;
		this.itemDeQuete = false;
	}// tous les items de quête ont un nom, mais pas forcément des pv rendue ou une
		// indication qu'ils sont de quête

// Lien avec joueur:
	
	public void ramasserParJoueur(Joueur joueur) {
		if (joueur.getInventaire().size() < joueur.getTailleMaxInventaire()) {
			joueur.getInventaire().add(this);
			System.out.println(joueur.getNom() + " a ramassé : " + nom);
		} else {
			System.out.println("Inventaire plein ! Impossible de ramasser " + nom);
		}
	}// verifier le nomage de taillemaxinvetaire avec JEAN
	
	
	// Un item peut être utilisé par un joueur:rend des PV si consommable
	public void utiliserParJoueur(Joueur joueur) {
		if (itemDeQuete) {
			System.out.println(nom + " est un objet de quête, il ne peut pas être consommé !");
		} else {
			joueur.changerVie(pvRendue);
			joueur.getInventaire().remove(this);
			System.out.println(joueur.getNom() + " a utilisé " + nom + " et regagne " + pvRendue + " PV.");
		}
	}
    // LIEN AVEC LIEU
	 public void apparaitreInLieu(Lieu lieu) {
	        lieu.getObjets().add(this);
	        System.out.println(nom + " a été placé dans le lieu : " + lieu.getNomImage());
	    }//on demande à LEO si il ya rajouter une propriete de type liste pour les objets attribués à chaque lieu 
	 
	 //au cas ou on consomme un objet ,on dois le retirer de notre inventaire 
	 public void retirerDuLieu(Lieu lieu) {
	        lieu.getObjets().remove(this);
	        System.out.println(nom + " a été retiré du lieu : " + lieu.getNomImage());
	    }
	  // LIEN AVEC PNJ
	 public void donnerParPnj(Pnj pnj, Joueur joueur) {
	        System.out.println(pnj.getNom() + " donne l'objet '" + nom + "' à " + joueur.getNom());
	        ramasserParJoueur(joueur);
	    }
	public String getNom() {
		return nom;
	}

	public int getPvRendue() {
		return pvRendue;
	}

	public boolean isItemDeQuete() {
		return itemDeQuete;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPvRendue(int pvRendue) {
		this.pvRendue = pvRendue;
	}

	public void setItemDeQuete(boolean itemDeQuete) {
		this.itemDeQuete = itemDeQuete;
	}

	@Override
	public String toString() {
		return "Item{nom='" + nom + "', pvRendue=" + pvRendue + ", itemDeQuete=" + itemDeQuete + "}";
	}

}
