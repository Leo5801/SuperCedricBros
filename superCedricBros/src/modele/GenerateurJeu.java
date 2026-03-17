package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu {
	
	
	public static Map<String, Lieu> creerLeMonde() {
		
		Lieu lieuCourantDevant, lieuCourantDerriere;
		Action actionCourante;
		Map<String,Lieu> catalogue = new HashMap<>();
		List<Action> listeActions = new ArrayList<>();
		Map<String, Pnj> mesPnj = GenerateurJeu.creerLesPersos();

		
		//le hall
		lieuCourantDevant = new Lieu("hallDevant", "Un lieu paisible...","miniMapHall", "hallDerriere");
		lieuCourantDerriere = new Lieu("hallDerriere", "Un lieu paisible...","miniMapHall", "hallDevant");
		listeActions.add(new ActionChangementMap("Aller dans le bureau du BDE", "bureauBdeDevant"));
		listeActions.add(new ActionChangementMap("Aller dans le couloir", "couloir"));
		lieuCourantDevant.ajouterActions(listeActions);
		lieuCourantDerriere.ajouterActions(listeActions);
		listeActions.clear();
		//fin action changement map
		
		listeActions.add(new ActionDialogue("Allumer le robot","Salut moi c'est Gemimisce, une IA capable de voler ton travail post Master ! bonnes revisions !"));
		listeActions.add(new ActionDialogue("Regarder le journal", "Cedric : QUOI ! un élève de L3G a trouvé un stage, c'est pas arrivé depuis 2005 !"));
		listeActions.add(new ActionDialogue("Allumer la radio","*Non merci, éteins-moi s'il te plait*\n Cedric : euh okk...."));
		listeActions.add(new ActionDialogue("Fouiller la poubelle","Cedric : Oh les plans de la nouvelle fac... pourquoi mettre l'écran tout à gauche de la salle ? et les élèves assis à droite ?"));
		listeActions.add(new ActionChangementVue("Regarder par la fenêtre", "Aucun étudiant dehors, ah oui c'est les vacances de février, enfin pas pour tout le monde...", "vueFenetreHall"));
		lieuCourantDevant.ajouterActions(listeActions);
		listeActions.clear();
		//fin ajout devant
		
		listeActions.add(new ActionDialogue("Prendre un livre","Titre : Cours de POO : des classes encore plus... classes"));
		listeActions.add(new ActionDialogue("Fouiller dans les cartons","Cedric : oh une canette, ça doit servir"));
		listeActions.add(new ActionDialogue("Intégragir avec le distributeur","*Hors service*"));
		listeActions.add(new ActionDialoguePnj("Parler aux élèves","Nina : Olala... c'est quoi le concept de polymorphisme, je comprends rien à rien !", "nina"));
		listeActions.add(new ActionDialogue("S'asseoir sur le canapé","*prout*\nCedric : oh.. un coussin péteur.."));
		lieuCourantDerriere.ajouterActions(listeActions);
		listeActions.clear();
		catalogue.put("hallDevant", lieuCourantDevant);
		catalogue.put("hallDerriere", lieuCourantDerriere);
		//fin ajout derrière et fin hall
		
		
		
		//bureauBde
		lieuCourantDevant = new Lieu("bureauBdeDevant", "Un si grand bureau pour de si petites révisions","miniMapBureauBde", "bureauBdeDerriere");
		lieuCourantDerriere = new Lieu("bureauBdeDerriere", "Un si grand bureau pour de si petites révisions", "miniMapBureauBde", "bureauBdeDevant");
		listeActions.add(new ActionChangementMap("Aller dans le hall","hallDevant"));
		lieuCourantDevant.ajouterActions(listeActions);
		listeActions.clear();
		//fin action changement map
		
		listeActions.add(new ActionDialogue("Prendre la clé","Ethan : hop hop hop pas touche à ça, c'est la clé pour l'armoir à bières"));
		listeActions.add(new ActionDialogue("Regarder sous le tapis","Cedric : oh un sol !"));
		listeActions.add(new ActionDialogue("Ouvrir la malette","Cedric :  ce n'est pas une valise mais un thermos géant à café ! j'en prends un peu"));
		lieuCourantDevant.ajouterActions(listeActions);
		listeActions.clear();
		//fin ajout devant
		
		listeActions.add(new ActionDialoguePnj("Parler à Lucie","coucou","lucie"));
		listeActions.add(new ActionChangementVue("Regarder l'écran d'Eroll","Cedric : c'est vrai qu'il est beau","vueEcranEroll"));
		listeActions.add(new ActionDialogue("Prendre un livre", "Synopsis : Un homme, Steve, a vu sa vie bouleversée lorsqu'il a trouvé du diamant en minant chez lui jusqu'à 60 à 100 mètres de profondeur, suivez son évolution."));
		lieuCourantDerriere.ajouterActions(listeActions);
		listeActions.clear();
		catalogue.put("bureauBdeDevant", lieuCourantDevant);
		catalogue.put("bureauBdeDerriere", lieuCourantDerriere);
		//fin ajout derriere et fin bureauBde
		
		
		
		//couloir
		lieuCourantDevant = new Lieu("couloir", "eroll est magnifique","miniMapCouloir", null);
		listeActions.add(new ActionChangementMap("Aller dans le hall", "hallDevant"));
		listeActions.add(new ActionChangementMap("Aller dans la salle de Rémi", "salleRemiDevant"));
		listeActions.add(new ActionChangementMap("Aller dans la salle info", "salleInfoDevant"));
		//fin action changement map
		
		listeActions.add(new ActionDialoguePnj("Parler à Marcus","Marcus : désolé ça va pas être possible", "marcus"));
		lieuCourantDevant.ajouterActions(listeActions);
		catalogue.put("couloir", lieuCourantDevant);
		
		
		//salleRémi
		lieuCourantDevant = new Lieu("salleRemiDevant", "il est si musclé","miniMapSalleRemi", "salleRemiDerriere");
		lieuCourantDerriere = new Lieu("salleRemiDerriere", "il est si musclé", "miniMapSalleRemi", "salleRemiDevant");
		actionCourante = new ActionChangementMap("Aller dans le couloir", "couloir");
		lieuCourantDevant.ajouterAction(actionCourante);
		lieuCourantDerriere.ajouterAction(actionCourante);
		//fin action changement map
		catalogue.put("salleRemiDevant", lieuCourantDevant);
		catalogue.put("salleRemiDerriere", lieuCourantDerriere);
		
		
		//salleInfo
		lieuCourantDevant = new Lieu("salleInfoDevant", "oh l'ordeur...", "miniMapSalleInfo","salleInfoDerriere");
		lieuCourantDerriere = new Lieu("salleInfoDerriere", "oh l'ordeur...","miniMapSalleInfo", "salleInfoDevant");
		actionCourante = new ActionChangementMap("Aller dans le couloir", "couloir");
		lieuCourantDevant.ajouterAction(actionCourante);
		lieuCourantDerriere.ajouterAction(actionCourante);
		actionCourante = new ActionChangementMap("Aller dans la salle de madame Michu", "salleMichu");
		lieuCourantDevant.ajouterAction(actionCourante);
		lieuCourantDerriere.ajouterAction(actionCourante);
		//fin action changement map
		catalogue.put("salleInfo", lieuCourantDevant);
		
		
		//salleMichu
		lieuCourantDevant = new Lieu("salleMichu", "","miniMapSalleMichu","");
		actionCourante = new ActionChangementMap("Aller dans la salle info", "salleInfo");
		lieuCourantDevant.ajouterAction(actionCourante);
		lieuCourantDevant.ajouterAction(actionCourante);
		//fin action changement map
		catalogue.put("salleMichu", lieuCourantDevant);
		
		
		for(String clé : mesPnj.keySet()) {
			
			Pnj monPerso = mesPnj.get(clé);
			Lieu leLieu = catalogue.get(monPerso.localisation);
			
			leLieu.ajouterAction(actionDialoguePnj("Parler à " + monPerso.nom, monPerso.dialogue, monPerso.nomPortrait));
		}
		
		
		return catalogue;
	}
	
	
	public static Map<String, Pnj> creerLesPersos() {
		
		Map<String,Pnj> catalogue = new HashMap<>();
		
		catalogue.put("eroll", new Pnj("eroll", "eroll", "bureauBde",true));
		
		
		return catalogue;
	}
}
