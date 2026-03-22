package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import affichage.FenetrePrincipale;

public class GenerateurJeu {
	
	private Map<String,Lieu> catalogueSalles = new HashMap<>();
	private Map<String,Pnj> cataloguePnj = new HashMap<>();
	private Map<String,Item> catalogueItems = new HashMap<>();
	
	
	public GenerateurJeu() {}
	
	
	public void creerLeMonde() {
		List<Action> listeActions = new ArrayList<>();

		
		//début création map
		this.ajouterCatalogueSalles(new Lieu("hallDevant", "Un lieu paisible...","miniMapHall", "Aller dans le hall"));
		this.ajouterCatalogueSalles(new Lieu("hallDerriere", "Un lieu paisible...","miniMapHall", "Aller dans le hall"));
		this.ajouterCatalogueSalles(new Lieu("bureauBdeDevant", "Un si grand bureau pour de si petites révisions","miniMapBureauBde", "Aller dans le bureau du bde"));
		this.ajouterCatalogueSalles(new Lieu("bureauBdeDerriere", "Un si grand bureau pour de si petites révisions","miniMapBureauBde", "Aller dans le bureau du bde"));
		this.ajouterCatalogueSalles(new Lieu("couloir", "wow il est balaise lui...", "miniMapCouloir", "Aller dans le couloir"));
		this.ajouterCatalogueSalles(new Lieu("salleInfoDevant", "Oh l'odeur...", "miniMapSalleInfo", "Aller dans la salle info"));
		this.ajouterCatalogueSalles(new Lieu("salleInfoDerriere", "C'est pire de derrière...", "miniMapSalleInfo", "Aller dans la salle info"));
		this.ajouterCatalogueSalles(new Lieu("salleMichuDevant", "", "miniMapSalleMichu", "Aller dans la salle de madame Michu"));
		this.ajouterCatalogueSalles(new Lieu("salleMichuDerriere", "", "miniMapSalleMichu", "Aller dans la salle de madame Michu"));
		this.ajouterCatalogueSalles(new Lieu("salleRemiDevant", "Il est si musclé...", "miniMapSalleRemi", "Aller dans la salle de Rémi"));
		this.ajouterCatalogueSalles(new Lieu("salleRemiDerriere", "Il est si musclé...", "miniMapSalleRemi", "Aller dans la salle de Rémi"));
		this.ajouterCatalogueSalles(new Lieu("monik", "Cedric : ok je suis foutu...\n*Cedric perd 25 PV*", "", ""));

		//fin de la création des maps
		
		
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
			
			if(lieuCourant.getLieuxVoisins() != null) {
				for(Lieu l : lieuCourant.getLieuxVoisins()) {
					lieuCourant.ajouterAction(new ActionChangementMap(l.getLabelBouton(),l));
				}
			}
			
			
			if (lieuCourant.getLieuOpposé() != null) {
				lieuCourant.ajouterAction(new ActionChangementMap("Se retourner", lieuCourant.getLieuOpposé()));
			}
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
		listeActions.add(new ActionChangementVue("Regarder par la fenêtre", "Aucun étudiant dehors, ah oui c'est les vacances de février, enfin pas pour tout le monde...", "vueFenetreHall"));
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
		listeActions.add(new ActionChangementVue("Regarder sous le tapis","Cedric : oh un sol !", "vueSol"));
		getSalle("bureauBdeDevant").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		
		//bureauBdeDerriere
		listeActions.add(new ActionChangementVue("Regarder l'écran d'Eroll","Cedric : c'est vrai qu'il est beau","vueEcranEroll"));
		listeActions.add(new ActionDialogue("Prendre un livre", "Synopsis : Un homme, Steve, a vu sa vie bouleversée lorsqu'il a trouvé du diamant en minant chez lui jusqu'à 60 à 100 mètres de profondeur, suivez son évolution."));
		getSalle("bureauBdeDerriere").ajouterActions(listeActions);
		//fin bureauBde
		
		//couloir
		listeActions.add(new ActionDialogue("Parler au gardien", "Gardien : Ah toi t'es pas étudiant ici... \nCedric : si si je suis en Master\nGardien : ah ouais ? pourtant t'as pas la tête de quelqu'un qui mange des pâtes depuis 6 mois."));
		listeActions.add(new ActionDialogue("Demander l'heure au gardien", "Gardien : 14h37.\nCedric : et les cours reprennent à...?\nGardien : 14h00.\nCedric : ..."));
		listeActions.add(new ActionDialogue("Regarder le tableau d'affichage", "Cedric : 'Réunion annulation des réunions - ANNULÉE'\nCedric : logique."));
		listeActions.add(new ActionDialogue("Examiner l'extincteur", "Cedric : la dernière vérification date de 2019...\nGardien : chut."));
		listeActions.add(new ActionDialogue("Lire les petites annonces", "'Cherche coéquipier projet groupe sérieux et motivé - URGENT'\nCedric : posté il y a 3 ans. Toujours là."));
		listeActions.add(new ActionDialogue("Tenter d'ouvrir une salle", "*verrouillé*\nGardien : t'as pas le badge.\nCedric : et si j'avais le badge ?\nGardien : t'aurais quand même pas le droit."));
		listeActions.add(new ActionDialogue("Regarder sous le tapis de l'entrée", "Cedric : oh une clé !\nGardien : c'est la clé des toilettes, elle est là depuis 2018, personne n'a jamais posé la question."));
		listeActions.add(new ActionDialogue("Demander le wifi au gardien", "Gardien : c'est 'Fac-Wifi-Officiel'\nCedric : le mot de passe ?\nGardien : 'Fac-Wifi-Officiel'\nCedric : ...ça marche pas.\nGardien : je sais."));
		getSalle("couloir").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		//fin couloir
		
		
		//salleInfoDerriere
		listeActions.add(new ActionDialogue("Parler à Stéphane", "Stéphane : ouais donc moi j'ai commencé le TP... enfin j'ai ouvert Eclipse\nCedric : c'est bien\nStéphane : ouais ça fait 1h"));

		listeActions.add(new ActionDialogue("Parler à Cédric2", "Cédric2 : *dit quelque chose en mandarin*\nCedric : euh...\nCédric2 : *montre son écran*\nCedric : ... il a déjà fini le TP de la semaine prochaine."));

		listeActions.add(new ActionDialogue("Regarder l'écran de la rangée intelligente", "Cedric : ... ils codent en vim, sans souris, avec 3 terminaux ouverts.\nCedric : l'un d'eux compile sans erreur.\nCedric : je me sens mal."));

		listeActions.add(new ActionDialogue("Tenter de communiquer avec tout le monde", "Cedric : bon... quelqu'un peut m'aider ?\n*silence*\nStéphane: je sais pas\nOussama : wallah moi non plus\nCédric2 : *envoie le corrigé complet sur le chat sans rien dire*\nCedric : ...merci je crois"));

		listeActions.add(new ActionDialogue("Regarder le chat de classe", "[14:02] Cédric2 : *envoie le TP entier résolu*\n[14:03] Oussama : t'es un dieu\n[14:03] Stéphane : merci bg\n[14:04] Cédric2 : *envoie le TP de la semaine prochaine*\n[14:04] Tout le monde : ???"));

		listeActions.add(new ActionDialogue("Demander de l'aide à la rangée intelligente", "Cedric : hé, tu peux m'expliquer le TP ?\nLéo : *explique en 40 secondes chrono, avec un schéma, deux analogies et une optimisation que le prof connaît pas*\nCedric : ...\nCedric : et en plus lent ?\nLéo : *recommence encore plus vite*"));


		getSalle("salleInfoDerriere").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		//fin salleInfoDerriere
		
		//salleInfoDevant
		listeActions.add(new ActionDialogue("Parler à Oussama", "Oussama : wallah frère le prof il nous a donné 6 TPs pour demain\nCedric : t'as commencé ?\nOussama : j'ai ouvert le sujet\nCedric : et ?\nOussama : y'a écrit 'bon courage' en bas... j'ai fermé."));
		listeActions.add(new ActionDialogue("Regarder l'écran de la rangée paresseuse", "Cedric : Adam regarde YouTube.\nMehdi regarde Adam regarder YouTube.\nWissem dort.\nOussama surveille que le prof arrive pas.\nC'est un système rodé."));
		listeActions.add(new ActionDialogue("Réveiller Wissem", "Cedric : hé, t'es réveillé ?\nWissem : ouais ouais je réfléchis\nCedric : t'avais les yeux fermés\nWissem : c'est pour mieux me concentrer\n*se rendort*"));
		getSalle("salleInfoDevant").ajouterActions(listeActions);
		listeActions = new ArrayList<>();
		
		
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
	
	
	private void ajouterCataloguePnj(Pnj p) {
		
		cataloguePnj.put(p.getNom(), p);
	}
	
	
	private void ajouterCatalogueItems(Item i) {
		
		catalogueItems.put(i.getNom(), i);
	}
	
	
	private Lieu getSalle(String nom) {
		
		return this.catalogueSalles.get(nom);
	}
	
	
	public Map<String,Lieu> getCatalogueSalles() {
		
		return this.catalogueSalles;
	}
	
	
	public Map<String, Pnj> getCataloguePnj() {
		
		return this.cataloguePnj;
	}
}
	
	
	

