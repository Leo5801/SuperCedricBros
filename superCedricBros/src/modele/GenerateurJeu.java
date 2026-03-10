package modele;

import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu {
	
	
	public static Map<String, Lieu> creerLeMonde() {
		Lieu lieuCourant;
		ActionChangementMap actionCourante;
		Map<String,Lieu> catalogue = new HashMap<>();
		
		
		lieuCourant = new Lieu("hall", "miam miam");
		actionCourante = new ActionChangementMap("aller dans le bureau du BDE", "bureauBde");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		actionCourante = new ActionChangementMap("aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		catalogue.put("hall", lieuCourant);
		
		
		lieuCourant = new Lieu("bureauBde", "Eroll est beau");
		actionCourante = new ActionChangementMap("aller dans le hall","hall");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		actionCourante = new ActionChangementMap("parler à Eroll");
		lieuCourant.ajouterActionDevant(actionCourante);
		catalogue.put("bureauBde", lieuCourant);
		
		
		lieuCourant = new Lieu("couloir", "eroll est magnifique");
		actionCourante = new ActionChangementMap("aller dans le hall", "hall");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("aller dans la salle de Rémi", "salleRemi");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("aller dans la salle info", "salleInfo");
		lieuCourant.ajouterActionDevant(actionCourante);
		catalogue.put("couloir", lieuCourant);
		
		
		lieuCourant = new Lieu("salleRemi", "il est si musclé");
		actionCourante = new ActionChangementMap("aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		catalogue.put("salleRemi", lieuCourant);
		
		
		lieuCourant = new Lieu("salleInfo", "oh l'ordeur...");
		actionCourante = new ActionChangementMap("Aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		actionCourante = new ActionChangementMap("Aller dans la salle de madame Michu", "salleMichu");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		catalogue.put("salleInfo", lieuCourant);
		
		
		lieuCourant = new Lieu("salleMichu", "");
		actionCourante = new ActionChangementMap("Aller dans la salle info", "salleInfo");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		catalogue.put("salleMichu", lieuCourant);
		
		
		return catalogue;
	}
}
