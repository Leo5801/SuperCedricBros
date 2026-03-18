package modele;

import java.util.Random;

public class Boss extends Entité {

	private int id;
	
	public Boss() {
	}

	public Boss(String nom, int id) {
		super(nom);
		this.setId(id);
	}

	

	public void attaquer(Joueur joueur) {            	// Méthode  attaquer  joueur

		joueur.changerPv(-25);

		System.out.println("Monik t'a trouvé : tu n'es pas en cours Cedric !");
		System.out.println("Le boss te retire 1 point de vie");
		System.out.println("PV actuels du joueur : " + joueur.getPvActuel() + "/" + joueur.getPvMax());
	}

	public static boolean probaBoss() {                          // proba du boss débarque
		 
		Random rand = new Random();
		double chance = rand.nextDouble();
		return chance <= 0.25;
	}

	public void verifierApparitionBoss(Joueur joueur) {          // méthode qui invoque en cas de proba correct
		if (probaBoss()) {
			afficherBoss();
			attaquer(joueur);
		} else {
			System.out.println(" ça va je suis pas tombé sur la prof ici .");
		}
	}

	public void afficherBoss() {                                          // affichage du boss
		System.out.println("Le Boss Monik apparaît dans la zone !!!");
		System.out.println("Cédric, tu fous quoi ici, aller hop !!!");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}