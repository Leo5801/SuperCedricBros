package modele;

import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu {
	
	
	public static Map<String, Lieu> creerLeMonde() {
		Lieu lieuCourant;
		Action actionCourante;
		Map<String,Lieu> catalogue = new HashMap<>();
		
		
		//le hall
		lieuCourant = new Lieu("hall", "miam miam");
		actionCourante = new ActionChangementMap("aller dans le bureau du BDE", "bureauBde");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		actionCourante = new ActionChangementMap("aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		//fin action changement map
		catalogue.put("hall", lieuCourant);
		
		
		//bureauBde
		lieuCourant = new Lieu("bureauBde", "Eroll est beau");
		actionCourante = new ActionChangementMap("aller dans le hall","hall");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		//fin action changement map
		actionCourante = new ActionDialoguePnj("parler à Eroll", "coucou", "eroll");
		lieuCourant.ajouterActionDevant(actionCourante);
		catalogue.put("bureauBde", lieuCourant);
		
		
		//couloir
		lieuCourant = new Lieu("couloir", "eroll est magnifique");
		actionCourante = new ActionChangementMap("aller dans le hall", "hall");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("aller dans la salle de Rémi", "salleRemi");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("aller dans la salle info", "salleInfo");
		lieuCourant.ajouterActionDevant(actionCourante);
		//fin action changement map
		catalogue.put("couloir", lieuCourant);
		
		
		//salleRémi
		lieuCourant = new Lieu("salleRemi", "il est si musclé");
		actionCourante = new ActionChangementMap("aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		//fin action changement map
		catalogue.put("salleRemi", lieuCourant);
		
		
		//salleInfo
		lieuCourant = new Lieu("salleInfo", "oh l'ordeur...");
		actionCourante = new ActionChangementMap("Aller dans le couloir", "couloir");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		actionCourante = new ActionChangementMap("Aller dans la salle de madame Michu", "salleMichu");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		//fin action changement map
		catalogue.put("salleInfo", lieuCourant);
		
		
		//salleMichu
		lieuCourant = new Lieu("salleMichu", "");
		actionCourante = new ActionChangementMap("Aller dans la salle info", "salleInfo");
		lieuCourant.ajouterActionDevant(actionCourante);
		lieuCourant.ajouterActionDerriere(actionCourante);
		//fin action changement map
		catalogue.put("salleMichu", lieuCourant);
		
		
		return catalogue;
	}
	
	
	public static Map<String, Pnj> creerLesPersos() {
		
		Map<String,Pnj> catalogue = new HashMap<>();
		
		catalogue.put("eroll", new Pnj("eroll"));
		
		
		return catalogue;
	}
}
