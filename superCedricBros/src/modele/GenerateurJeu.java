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
		listeActions.add(new ActionChangementVue("Regarder par la fenêtre", "Aucun étudiant dehors, ah oui c'est les vacances de février, enfin pas pour tout le monde...", "vueFenetreHall"));
		lieuCourant.ajouterActionsDevant(listeActions);
		listeActions.clear();
		//fin ajout devant
		
		listeActions.add(new ActionDialogue("Prendre un livre","Titre : Cours de POO : des classes encore plus... classes"));
		listeActions.add(new ActionDialogue("Fouiller dans les cartons","Cedric : oh une canette, ça doit servir"));
		listeActions.add(new ActionDialogue("Intégragir avec le distributeur","*Hors service*"));
		listeActions.add(new ActionDialoguePnj("Parler aux élèves","Nina : Olala... c'est quoi le concept de polymorphisme, je comprends rien à rien !", "nina"));
		listeActions.add(new ActionDialogue("S'asseoir sur le canapé","*prout*\nCedric : oh.. un coussin péteur.."));
		lieuCourant.ajouterActionsDerriere(listeActions);
		listeActions.clear();
		catalogue.put("hall", lieuCourant);
		//fin ajout derrière et fin hall
		
		
		
		//bureauBde
		lieuCourant = new Lieu("bureauBde", "Un si grand bureau pour de si petites révisions");
		listeActions.add(new ActionChangementMap("Aller dans le hall","hall"));
		lieuCourant.ajouterActionsDerriere(listeActions);
		lieuCourant.ajouterActionsDevant(listeActions);
		listeActions.clear();
		//fin action changement map
		
		listeActions.add(new ActionDialoguePnj("Parler à Eroll", "coucou", "eroll"));
		listeActions.add(new ActionDialogue("Prendre la clé","Ethan : hop hop hop pas touche à ça, c'est la clé pour l'armoir à bières"));
		listeActions.add(new ActionDialogue("Regarder sous le tapis","Cedric : oh un sol !"));
		listeActions.add(new ActionDialogue("Ouvrir la malette","Cedric :  ce n'est pas une valise mais un thermos géant à café ! j'en prends un peu"));
		lieuCourant.ajouterActionsDevant(listeActions);
		listeActions.clear();
		//fin ajout devant
		
		listeActions.add(new ActionDialoguePnj("Parler à Lucie","coucou","lucie"));
		listeActions.add(new ActionChangementVue("Regarder l'écran d'Eroll","Cedric : c'est vrai qu'il est beau","vueEcranEroll"));
		listeActions.add(new ActionDialogue("Prendre un livre", "Synopsis : Un homme, Steve, a vu sa vie bouleversée lorsqu'il a trouvé du diamant en minant chez lui jusqu'à 60 à 100 mètres de profondeur, suivez son évolution."));
		lieuCourant.ajouterActionsDerriere(listeActions);
		listeActions.clear();
		catalogue.put("bureauBde", lieuCourant);
		//fin ajout derriere et fin bureauBde
		
		
		
		//couloir
		lieuCourant = new Lieu("couloir", "eroll est magnifique");
		listeActions.add(new ActionChangementMap("Aller dans le hall", "hall"));
		listeActions.add(new ActionChangementMap("Aller dans la salle de Rémi", "salleRemi"));
		listeActions.add(new ActionChangementMap("Aller dans la salle info", "salleInfo"));
		//fin action changement map
		
		listeActions.add(new ActionDialoguePnj("Parler à Marcus","Marcus : désolé ça va pas être possible", "marcus"));
		lieuCourant.ajouterActionsDevant(listeActions);
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
