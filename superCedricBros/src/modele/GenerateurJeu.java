package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu {
	
	
	public static Map<String, Lieu> creerLeMonde() {
		Lieu lieuCourant;
		Action actionCourante;
		Map<String,Lieu> catalogue = new HashMap<>();
		List<Action> listeActions = new ArrayList<>();
		
		
		//le hall
		lieuCourant = new Lieu("hall", "Un lieu paisible...");
		listeActions.add(new ActionChangementMap("Aller dans le bureau du BDE", "bureauBde"));
		listeActions.add(new ActionChangementMap("Aller dans le couloir", "couloir"));
		lieuCourant.ajouterActionsDevant(listeActions);
		lieuCourant.ajouterActionsDerriere(listeActions);
		listeActions.clear();
		//fin action changement map
		
		listeActions.add(new ActionDialogue("Allumer le robot","Salut moi c'est Gemimisce, une IA capable de voler ton travail post Master ! bonnes revisions !"));
		listeActions.add(new ActionDialogue("Regarder le journal", "Cedric : QUOI ! un élève de L3G a trouvé un stage, c'est pas arrivé depuis 2005 !"));
		listeActions.add(new ActionDialogue("Allumer la radio","*Non merci, éteins-moi s'il te plait*\n Cedric : euh okk...."));
		listeActions.add(new ActionDialogue("Fouiller la poubelle","Cedric : Oh les plans de la nouvelle fac... pourquoi mettre l'écran tout à gauche de la salle ? et les élèves assis à droite ?"));
		lieuCourant.ajouterActionsDevant(listeActions);
		listeActions.clear();
		//fin ajout devant
		
		listeActions.add(new ActionDialogue("Prendre un livre","Titre : Cours de POO : des classes encore plus... classes"));
		listeActions.add(new ActionDialogue("Fouiller dans les cartons","Cedric : oh une canette, ça doit servir"));
		listeActions.add(new ActionDialogue("Intégragir avec le distributeur","*Hors service*"));
		listeActions.add(new ActionDialogue("Parler aux élèves","Nina : Olala... c'est quoi le concept de polymorphisme, je comprends rien à rien !"));
		listeActions.add(new ActionDialogue("S'asseoir sur le canapé","*prout*\nCedric : oh.. un coussin péteur.."));
		lieuCourant.ajouterActionsDerriere(listeActions);
		listeActions.clear();
		catalogue.put("hall", lieuCourant);
		
		
		//bureauBde
		lieuCourant = new Lieu("bureauBde", "Une odeur de cigarettes...");
		listeActions.add(new ActionChangementMap("Aller dans le hall","hall"));
		listeActions.add(new ActionChangementMap("Aller dans le hall","hall"));
		lieuCourant.ajouterActionsDerriere(listeActions);
		lieuCourant.ajouterActionsDevant(listeActions);
		listeActions.clear();
		//fin action changement map
		
		listeActions.add(new ActionDialoguePnj("Parler à Eroll", "", "eroll"));
		listeActions.add(new ActionDialogue("Faire une remarque sur la repousse d'Eroll",""));
		lieuCourant.ajouterActionsDevant(listeActions);
		//fin ajout devant
		catalogue.put("bureauBde", lieuCourant);
		
		
		//couloir
		lieuCourant = new Lieu("couloir", "eroll est magnifique");
		actionCourante = new ActionChangementMap("Aller dans le hall", "hall");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("Aller dans la salle de Rémi", "salleRemi");
		lieuCourant.ajouterActionDevant(actionCourante);
		actionCourante = new ActionChangementMap("Aller dans la salle info", "salleInfo");
		lieuCourant.ajouterActionDevant(actionCourante);
		//fin action changement map
		catalogue.put("couloir", lieuCourant);
		
		
		//salleRémi
		lieuCourant = new Lieu("salleRemi", "il est si musclé");
		actionCourante = new ActionChangementMap("Aller dans le couloir", "couloir");
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
