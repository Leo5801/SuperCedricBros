package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu2 {
	
	private Map<String, Vue> catalogueVues = new HashMap<>();
	private Map<String,Lieu> catalogueSalles = new HashMap<>();
	private Map<String,Pnj> cataloguePnj = new HashMap<>();
	private Map<String,Item> catalogueItems = new HashMap<>();
	
	
	public GenerateurJeu2() {}
	
	
	public void creerLeMonde() {
		List<Action> listeActions = new ArrayList<>();

		
		//début création map
		this.ajouterCatalogueSalles(new Lieu("hallDevant", "Un lieu paisible...","miniMapHall", "Aller dans le hall"));
		this.ajouterCatalogueSalles(new Lieu("hallDerriere", "Un lieu paisible...","miniMapHall", "Aller dans le hall"));
		this.ajouterCatalogueSalles(new Lieu("bureauBdeDevant", "Un si grand bureau pour de si petites révisions","miniMapBureauBde", "Aller dans le bureau du bde"));
		this.ajouterCatalogueSalles(new Lieu("bureauBdeDerriere", "Un si grand bureau pour de si petites révisions","miniMapBureauBde", "Aller dans le bureau du bde"));
		this.ajouterCatalogueSalles(new Lieu("couloir", "", "miniMapCouloir", "Aller dans le couloir"));
		this.ajouterCatalogueSalles(new Lieu("salleInfoDevant", "Oh l'odeur...", "miniMapSalleInfo", "Aller dans la salle info"));
		this.ajouterCatalogueSalles(new Lieu("salleInfoDerriere", "Oh l'odeur...", "miniMapSalleInfo", "Aller dans la salle info"));
		this.ajouterCatalogueSalles(new Lieu("salleMichuDevant", "", "miniMapSalleMichu", "Aller dans la salle de madame Michu"));
		this.ajouterCatalogueSalles(new Lieu("salleMichuDerriere", "", "miniMapSalleMichu", "Aller dans la salle de madame Michu"));
		this.ajouterCatalogueSalles(new Lieu("salleRemiDevant", "Il est si musclé...", "miniMapSalleRemi", "Aller dans la salle de Rémi"));
		this.ajouterCatalogueSalles(new Lieu("salleRemiDerriere", "Il est si musclé...", "miniMapSalleRemi", "Aller dans la salle de Rémi"));
		this.ajouterCatalogueSalles(new Lieu("monik", "\n*Monik apparait, son aura nous affaiblit*\nCedric : ok je suis foutu...\n*Cedric perd 25 PV*", "", ""));
		//fin de la création des maps
		
		
		//on remplit les vues
		this.ajouterCatalogueVues(new Vue("vueEcranEroll","c'est vrai qu'il est beau","Regarder l'écran d'Eroll"));
		this.ajouterCatalogueVues(new Vue("vueFenetreHall", "aucun étudiant dehors, ah oui c'est les vacances de février, enfin pas pour tout le monde...", "Regarder par la fenêtre"));
		this.ajouterCatalogueVues(new Vue("vueSol","Cedric : oh un sol !", "Regarder sous le tapis"));
		//fin remplissage vues
		
		
		//on relie les 2 côtés des maps 
		this.relierMap(catalogueSalles.get("hallDevant"), catalogueSalles.get("hallDerriere"));
		this.relierMap(catalogueSalles.get("bureauBdeDevant"), catalogueSalles.get("bureauBdeDerriere"));
		this.relierMap(catalogueSalles.get("salleInfoDevant"), catalogueSalles.get("salleInfoDerriere"));
		this.relierMap(catalogueSalles.get("salleMichuDevant"), catalogueSalles.get("salleMichuDerriere"));
		this.relierMap(catalogueSalles.get("salleRemiDevant"), catalogueSalles.get("salleRemiDerriere"));
		//fin des liaisons
		
		
		//c'est parti pour remplir les salles voisines, le set ne pose pas problème car les deux côtés auront tout le temps les mêmes voisins
		getSalle("hallDevant").ajouterLieuxVoisons(getSalle("bureauBdeDevant"));
		getSalle("hallDevant").ajouterLieuxVoisons(getSalle("couloir"));
		getSalle("hallDerriere").setLieuxVoisins(getSalle("hallDevant").getLieuxVoisins());
		
		//bureauBde
		getSalle("bureauBdeDevant").ajouterLieuxVoisons(getSalle("hallDevant"));
		getSalle("bureauBdeDerriere").setLieuxVoisins(getSalle("bureauBdeDevant").getLieuxVoisins());
		
		//couloir
		getSalle("couloir").ajouterLieuxVoisons(getSalle("hallDevant"));
		getSalle("couloir").ajouterLieuxVoisons(getSalle("salleInfoDevant"));
		getSalle("couloir").ajouterLieuxVoisons(getSalle("salleRemiDevant"));
		
		//salle info
		getSalle("salleInfoDevant").ajouterLieuxVoisons(getSalle("salleMichuDevant"));
		getSalle("salleInfoDevant").ajouterLieuxVoisons(getSalle("couloir"));
		getSalle("salleInfoDerriere").setLieuxVoisins(getSalle("salleInfoDevant").getLieuxVoisins());
		
		//salle madame Michu
		getSalle("salleMichuDevant").ajouterLieuxVoisons(getSalle("salleInfoDevant"));
		getSalle("salleMichuDerriere").setLieuxVoisins(getSalle("salleMichuDevant").getLieuxVoisins());
		
		//salle rémi
		getSalle("salleRemiDevant").ajouterLieuxVoisons(getSalle("couloir"));
		getSalle("salleRemiDerriere").setLieuxVoisins(getSalle("salleRemiDevant").getLieuxVoisins());
		//fin remplissage salles voisines
		
		
		//c'est parti pour remplir les actions Changement Map, vu qu'elles ne changent pas on les calcule une fois maintenant et on crée le bouton "se retourner" qui permet, ma foi, de se retourner
		for(String key : catalogueSalles.keySet()) {
			Lieu lieuCourant = getSalle(key);
			
			//pas besoin de if != null car on initialise la liste à 0, jamais null (youpi au moins 2 lignes de code en moins)
			for(Lieu l : lieuCourant.getLieuxVoisins()) {
					lieuCourant.ajouterAction(new ActionChangementMap(l.getLabelBouton(),l));
			}
			
			
			lieuCourant.ajouterAction(new ActionChangementMap("Se retourner", lieuCourant.getLieuOpposé()));
		}
		//fin remplissage actions changement Map
		
		
		//on place les pnj à leur emplacement de base
		getSalle("bureauBdeDevant").ajouterPnj(cataloguePnj.get("eroll"));
		//fin placement pnj
		
		
		//on place les items à leur emplacement de base
		getSalle("bureauBdeDevant").ajouterObjet(catalogueItems.get("cafe"));
		
		//c'est parti pour remplir les actions dialogues classiques qui sont fixes, les intéractions PNJ sont calculées dans gestionnaire jeu car elles peuvent changer
		//hallDevant
		listeActions.add(new ActionDialogue("Allumer le robot","Salut moi c'est Gemimisce, une IA capable de voler ton travail post Master ! bonnes revisions !"));
		listeActions.add(new ActionDialogue("Regarder le journal", "Cedric : QUOI ! un élève de L3G a trouvé un stage, c'est pas arrivé depuis 2005 !"));
		listeActions.add(new ActionDialogue("Allumer la radio","*Non merci, éteins-moi s'il te plait*\n Cedric : euh okk...."));
		listeActions.add(new ActionDialogue("Fouiller la poubelle","Cedric : Oh les plans de la nouvelle fac... pourquoi mettre l'écran tout à gauche de la salle ? et les élèves assis à droite ?"));
		listeActions.add(new ActionChangementVue(this.getVue("vueFenetreHall").getLabelBouton(), this.getVue("vueFenetreHall")));
		getSalle("hallDevant").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		
		//hallDerriere
		listeActions.add(new ActionDialogue("Prendre un livre","Titre : Cours de POO : des classes encore plus... classes"));
		listeActions.add(new ActionDialogue("Fouiller dans les cartons","Cedric : oh une canette, ça doit servir"));
		listeActions.add(new ActionDialogue("Intégragir avec le distributeur","*Hors service*"));
		listeActions.add(new ActionDialogue("S'asseoir sur le canapé","*prout*\nCedric : oh.. un coussin péteur.."));
		getSalle("hallDerriere").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		//fin hall
		
		
		//bureauBdeDevant
		listeActions.add(new ActionDialogue("Prendre la clé","Ethan : hop hop hop pas touche à ça, c'est la clé pour l'armoir à bières"));
		listeActions.add(new ActionChangementVue(this.getVue("vueSol").getLabelBouton(), this.getVue("vueSol")));
		getSalle("bureauBdeDevant").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		
		//bureauBdeDerriere
		listeActions.add(new ActionDialogue("Prendre un livre", "Synopsis : Un homme, Steve, a vu sa vie bouleversée lorsqu'il a trouvé du diamant en minant chez lui jusqu'à 60 à 100 mètres de profondeur, suivez son évolution."));
		listeActions.add(new ActionChangementVue(this.getVue("vueEcranEroll").getLabelBouton(), this.getVue("vueEcranEroll")));
		getSalle("bureauBdeDerriere").ajouterActions(listeActions);
		//fin bureauBde
	}
	
	
	public void creerLesPersos() {
		String[] test = {"coucou"};
		ajouterCataloguePnj(new Pnj("eroll", "erollAvecCasquette",test));
		ajouterCataloguePnj(new Pnj("nina", "nina",test));
		ajouterCataloguePnj(new Pnj("marcus", "marcus", test));
		ajouterCataloguePnj(new Pnj("mme michu", "michu", test));
		
	}
	
	
	public void creerLesItems() {
		
		ajouterCatalogueItems(new Item("cafe", "Ouvrir la malette", "Cedric :  ce n'est pas une valise mais un thermos géant à café ! j'en prends un peu", 25,false));
	}

	
	public void start() {
		this.creerLesItems();
		this.creerLesPersos();
		this.creerLeMonde();
	}
	
	
	private void relierMap(Lieu devant, Lieu derriere) {
		
		devant.setLieuOpposé(derriere);
		derriere.setLieuOpposé(devant);
	}
	
	
	private void ajouterCatalogueSalles(Lieu l) {
		
        catalogueSalles.put(l.getNom(), l);
    }
	
	
	private void ajouterCatalogueVues(Vue v) {
		
        catalogueVues.put(v.getNom(), v);
    }
	
	
	private void ajouterCataloguePnj(Pnj p) {
		
		cataloguePnj.put(p.getNom(), p);
	}
	
	
	private void ajouterCatalogueItems(Item i) {
		
		catalogueItems.put(i.getNom(), i);
	}
	
	
	private Lieu getSalle(String nom) {
		
		Lieu l = this.catalogueSalles.get(nom);
		if (l == null) System.err.println("ERREUR : La salle '" + nom + "' n'existe pas dans le catalogue !");
	    return l;
	}
	
	
	private Vue getVue(String nom) {
		
		Vue v =  this.catalogueVues.get(nom);
		if (v == null) System.err.println("ERREUR : La vue '" + nom + "' n'existe pas dans le catalogue !");
	    return v;
	}
	
	
	public Map<String,Lieu> getCatalogueSalles() {
		
		return this.catalogueSalles;
	}
	
	
	public Map<String, Pnj> getCataloguePnj() {
		
		return this.cataloguePnj;
	}
	
	
public Map<String, Item> getCatalogueItems() {
		
		return this.catalogueItems;
	}
}
	
	
	

