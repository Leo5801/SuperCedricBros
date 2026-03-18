package modele;

public class Item {
	private String nom;
	private String label;
	private String texteRamassage;
	private int pvRendue;
	private boolean itemDeQuete;

//item pour gagner des vies 
	public Item(String nom, String label, String texteRamassage, int pvRendue, boolean itemDeQuete) {
		this.nom = nom;
		this.label = label;
		this.texteRamassage = texteRamassage;
		this.pvRendue = pvRendue;
		this.itemDeQuete = itemDeQuete;
	}

	// Constructeur simplifié pour les items basiques:la casquette de madame michu
	// pour debloquer la suite dune quete
	public Item(String nom) {
		this.nom = nom;
		this.pvRendue = 0;
		this.itemDeQuete = true;
	}// tous les items de quête ont un nom, mais pas forcément des pv rendue ou une
		// indication qu'ils sont de quête

// Lien avec joueur:
	
	
	/* Un item peut être utilisé par un joueur:rend des PV si consommable
	public void utiliserParJoueur(Joueur joueur) {
		if (itemDeQuete) {
			System.out.println(nom + " est un objet de quête, il ne peut pas être consommé !");
		} else {
			joueur.changerVie(pvRendue);
			joueur.getInventaire().remove(this);
			System.out.println(joueur.getNom() + " a utilisé " + nom + " et regagne " + pvRendue + " PV.");
		}
	}*/

	
	  // LIEN AVEC PNJ
	public String getNom() {
		return nom;
	}

	public int getPvRendue() {
		return pvRendue;
	}

	public boolean getIsItemDeQuete() {
		return itemDeQuete;
	}
	
	
	public String getLabel() {
		
		return this.label;
	}
	
	
	public String getTexteRamassage() {
		
		return this.texteRamassage;
	}


	@Override
	public String toString() {
		return "Item{nom='" + nom + "', pvRendue=" + pvRendue + ", itemDeQuete=" + itemDeQuete + "}";
	}

}
