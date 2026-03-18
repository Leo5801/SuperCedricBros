package modele;

public class Item2 {
	
	private String nom;
	private String label;
	private String texteRamassage;
	private boolean estQuete;
	
	
	public Item2(String nom, String label, String texteRamassage, boolean estQueste) {
		
		this.nom = nom;
		this.label = label;
		this.texteRamassage = texteRamassage;
		this.estQuete = estQueste;
	}
	
	
	public String getNom() {
			
			return this.nom;
	}
	
	
	public String getLabel() {
		
		return this.label;
	}
	
	
	public boolean getEstQuete() {
		
		return this.estQuete;
	}
	
	
	public String getTexteRamassage() {
		
		return this.texteRamassage;
	}
}

