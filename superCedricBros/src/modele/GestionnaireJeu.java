package modele;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.UIManager;

import affichage.FenetrePrincipale;


public class GestionnaireJeu  {
	
	
    private FenetrePrincipale fenetre;
    private Map<String, Lieu> catalogueSalles;
    private Map<String, Item> catalogueItems;
    private Lieu lieuActuel;
    private Joueur persoPrincipal;
    private EtatJeu etat;
    private List<String> listeObjetsPlusSurMap = new ArrayList<>();
    

    public GestionnaireJeu(FenetrePrincipale fenetre, Map<String, Lieu> catalogueSalles, Map<String, Item> catalogueItems) {
        this.fenetre = fenetre;
        this.catalogueSalles = catalogueSalles;
        this.catalogueItems = catalogueItems;
        this.lieuActuel = this.catalogueSalles.get("hallDevant");
        this.persoPrincipal = new Joueur("", 100, 100);
        this.etat = EtatJeu.ACCUEIL;
    }
    
    
    public void Init() {
    	
    	 this.fenetre.setControleur(this);
         this.fenetre.afficherEcranAccueil();
    }
    

    public void afficherLieu(Lieu nouveauLieu) {
    	Lieu ancienLieu = this.lieuActuel;
    	this.lieuActuel = nouveauLieu;
		rafraichirAffichage();
		
		//si le joueur change de map (pas se retourner) alors on vide la boite de dialogue, on affiche la description et on lance Monik
    	if(ancienLieu.getLieuOpposé() != nouveauLieu) {
    		fenetre.viderZoneTexte();
            fenetre.afficherDescription(lieuActuel.getDescription());
            this.monik();
    	}
    }

    
    // Cette méthode est la big boss de l'affichage
    public void rafraichirAffichage() {
        
        switch (this.etat) {
        case ACCUEIL:
        	fenetre.afficherEcranAccueil();
            break;
            
        case ENCOURS:
        	fenetre.viderActions();
            this.afficherInterface();
            break;
            
        }
    }
    
    
    private void afficherEcranGameOver() {
    	
    	this.fenetre.getJMenuBar().setVisible(false);
		this.fenetre.viderActions();
		this.fenetre.setPortrait("");
		this.fenetre.setSalle("gameOver");
		this.fenetre.afficherTexte("\n Cedric : quelle idée d'avoir utilisé une boucle FOR...");
    	this.fenetre.genererBoutonRestart();
    	this.fenetre.genererBoutonSupprimer(this.persoPrincipal.getNom());
	}
    
    
    private void afficherEcranVictoire() {
		
    	this.fenetre.getJMenuBar().setVisible(false);
		this.fenetre.viderActions();
		this.fenetre.setPortrait("");
		this.fenetre.setSalle("win");
		this.fenetre.afficherTexte("\n Cedric : youpi");
    	this.fenetre.genererBoutonRestart();
    	this.fenetre.genererBoutonSupprimer(this.persoPrincipal.getNom());
	}
    
    public void reStart() {
        
        this.persoPrincipal.setPvActuel(100);
        this.persoPrincipal.setNom(""); // On reset le nom pour forcer la reconnexion
        this.persoPrincipal.setQuetes(new boolean[]{false, false, false});
        this.persoPrincipal.viderInventaire(); // Pense à créer cette méthode dans Joueur

        
        this.lieuActuel = this.catalogueSalles.get("hallDevant");
        
        GenerateurJeu2 generateur = new GenerateurJeu2(); 
        generateur.start();
        this.catalogueSalles = generateur.getCatalogueSalles();
        this.catalogueItems = generateur.getCatalogueItems();
        
        this.setEtat(EtatJeu.ACCUEIL);
  
        this.fenetre.afficherEcranAccueil();
    }


	public void afficherInterface() {
    	
    	fenetre.mettreAJourVie(this.persoPrincipal.getPvActuel());
        fenetre.setSalle(lieuActuel.getNom());
        
        //on affiche tous les boutons des actions
        for(Action a : lieuActuel.getActions()) {
            	fenetre.genererBouton(a);
        }
        

        //on affiche tous les dialogues avec PNJ
        for(Pnj p : lieuActuel.getPersos()) {
        	fenetre.genererBoutonPnj(p);
        }
        
        //on affiche les boutons pour prendre les objets
        for(Item i : lieuActuel.getObjets()) {
        	fenetre.genererBoutonItem(i);
        }
        
        fenetre.setPortrait(lieuActuel.getMiniMap()); //le revalidate et repaint se fait ici
    }
    
    
    public void monik() {
    	
    	Random aleatoire = new Random();
    	
    	
    	if(aleatoire.nextInt(8) == 0) {
    		Lieu monik = this.catalogueSalles.get("monik");
    		this.fenetre.setSalle(monik.getNom());
    		this.fenetre.viderActions();
    		this.fenetre.afficherTexte(monik.getDescription());
    		
    		
    		Timer timerRetour = new Timer(5000, e -> {
                
                this.fenetre.afficherTexte("*Monik a disparu... pour l'instant.*");
                this.changementPvJoueur(-25);
            });

            timerRetour.setRepeats(false);
            timerRetour.start();
    	}
    }
        
    
    public void gererVue(Vue v) {
    	
    	fenetre.changerVue(v);// hum c'est pour les niveaux d'abstractions, donc il sert d'intermédiaire
    }
    
    
    public void ramasserObjet(Item i) {
       
        if(!i.getIsItemDeQuete()) {
        	Integer index = fenetre.premierSlotDispo();
        	if (index != null) { 
                this.persoPrincipal.ajouterItem(index, i);
                this.fenetre.setObjetUsuel(i, index);
                lieuActuel.retirerObjet(i);
                this.fenetre.afficherTexte(i.getTexteRamassage());
                
                this.listeObjetsPlusSurMap.add(i.getNom());
                this.rafraichirAffichage();
                
            } else {
                fenetre.afficherTexte("Inventaire plein !");
            }
        	
        } else if(i.getIsItemDeQuete()) { //1 seul item de quête à la fois tout est encadré mais on sait jamais on supprime l'ancien, tant pis !
        	this.persoPrincipal.retirerItemQuete();
        	this.persoPrincipal.ajouterItemQuete(i);
        	this.fenetre.setObjetQuete(i);
        	
        	this.rafraichirAffichage();
        }
    }
    
    
    
    public void afficherDialoguePnj(String leNomPortrait, String leTexte) {
    	
    	fenetre.dialoguePnj(leNomPortrait, leTexte);
    	fenetre.lancerTimerRetourMinimap(this.lieuActuel.getMiniMap());
    }
    
    
    public FenetrePrincipale getFenetre() {
    	
    	return this.fenetre;
    }
    
    
    public void changementPvJoueur(int montant) {
    	
    	this.persoPrincipal.changerPv(montant);
    	this.verifierConditionGameOver();
    	fenetre.mettreAJourVie(this.persoPrincipal.getPvActuel());
    	this.rafraichirAffichage();
    }
    
    
    public void clicSurSlot(int index) {
    	
    	//on gère le clic sur l'objet pour le consommer ! 
        String mess = this.persoPrincipal.consommerObjet(index);//on récupère l'objet;
        
        if (mess != null) {
            this.fenetre.afficherTexte(mess);
            fenetre.mettreAJourVie(this.persoPrincipal.getPvActuel());
            this.fenetre.mettreAJourSlot(this.fenetre.getSlotObjet()[index], "vide");
        }
    }
    
    
    public void verifierConditionVictoire() {
    	
        if (this.persoPrincipal.aToutGagne()) {
            this.setEtat(EtatJeu.VICTOIRE);
        }
    }
    
    
    public void verifierConditionGameOver() {
    	
    	if(this.persoPrincipal.getPvActuel() == 0) {
    		this.setEtat(EtatJeu.GAMEOVER);
    	}
    }
    
    
    public void setEtat(EtatJeu nouvelEtat) {
        
        if (this.etat == nouvelEtat) return;

        this.etat = nouvelEtat;

        
        if (this.etat == EtatJeu.GAMEOVER) { //on gère le gameover
        	this.sauvegarder();
            this.afficherEcranGameOver();
            
        } else if (this.etat == EtatJeu.VICTOIRE) { //on gère la victoire
        	this.sauvegarder();
            this.afficherEcranVictoire();
        }

        this.rafraichirAffichage();
    }

	
	public void sauvegarder() {
	    try {
	        Partie p = new Partie();
	        p.setNomLieuActuel(this.lieuActuel.getNom());
	        p.setPvActuel(this.persoPrincipal.getPvActuel());
	        p.setEtatActuel(this.etat);
	        p.setProgressionQuetes(this.persoPrincipal.getQuetes());
	        p.setObjetsRamasses(this.listeObjetsPlusSurMap);

	        
	        String[] nomsItems = new String[3];
	        for (int i = 0; i < 3; i++) {
	            Item it = this.persoPrincipal.getInventaire()[i]; 
	            nomsItems[i] = (it != null) ? it.getNom() : null;
	        }
	        p.setNomsItemsInventaire(nomsItems);

	        sauvegarde.GestionnaireSauvegarde.sauvegarder(p, this.persoPrincipal.getNom());
	        fenetre.afficherTexte("Partie sauvegardée !");
	    } catch (Exception e) {
	        fenetre.afficherTexte("Erreur lors de la sauvegarde.");
	        e.printStackTrace();
	    }
	}
	
	
	public void charger() {
		
	    try {
	        Partie p = sauvegarde.GestionnaireSauvegarde.charger(this.persoPrincipal.getNom());

	        // charger les données simples
	        this.fenetre.initialiserComposants();
	        this.persoPrincipal.setPvActuel(p.getPvActuel());
	        this.persoPrincipal.setQuetes(p.getProgressionQuetes());
	        this.lieuActuel = this.catalogueSalles.get(p.getNomLieuActuel());
	        this.listeObjetsPlusSurMap = p.getObjetsRamasses();

	        // charger l'inventaire via le catalogue du générateur
	        for (int i = 0; i < 3; i++) {
	            String nom = p.getNomsItemsInventaire()[i];
	            if (nom != null) {
	                
	                Item it = this.catalogueItems.get(nom); 
	                this.persoPrincipal.ajouterItem(i, it);
	                this.fenetre.setObjetUsuel(it, i);
	            }
	        }
	        
	        
	        for (String nomItem : this.listeObjetsPlusSurMap) {
	            for (Lieu l : catalogueSalles.values()) {
	                // On cherche l'item dans chaque salle et on le vire s'il y est
	                l.retirerObjetParNom(this.catalogueItems.get(nomItem)); 
	            }
	        }

	        // 3. Appliquer l'état chargé (ça lancera le rafraîchissement)
	        this.setEtat(p.getEtatActuel());
	        
	    } catch (Exception e) {
	        fenetre.afficherMessageAccueil("Aucune sauvegarde trouvée.");
	    }
	}
	
	
    public void receptionnerConnexion(String nomSaisi) {
        if (nomSaisi == null || nomSaisi.trim().isEmpty()) {
            // Au lieu de fenetre.afficherTexte, on utilise le nouveau message
            fenetre.afficherMessageAccueil("Le doyen refuse les étudiants sans nom !");
            return;
        }

        String nomNettoye = nomSaisi.trim();
        this.persoPrincipal.setNom(nomNettoye);

        if (sauvegarde.GestionnaireSauvegarde.existe(nomNettoye)) {
            this.charger(); 
        } else {
            this.demarrerNouvellePartie();
        }
    }

    private void demarrerNouvellePartie() {
    	
    	this.fenetre.initialiserComposants();
        this.setEtat(EtatJeu.ENCOURS);
        this.rafraichirAffichage();
        this.fenetre.afficherTexte("Bienvenue, " + persoPrincipal.getNom() + " !");
    }
    

	public Lieu getLieuActuel() {
    	
    	return this.lieuActuel;
    }
	
    
    public static void main(String[] args) {
    	try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}
    	

        FenetrePrincipale fenetre = new FenetrePrincipale();
        GenerateurJeu2 remplissage = new GenerateurJeu2();
        remplissage.start();
        
    
        // On crée le contrôleur
        GestionnaireJeu controleur = new GestionnaireJeu(fenetre, remplissage.getCatalogueSalles(), remplissage.getCatalogueItems());
        controleur.Init();
        

        fenetre.setVisible(true);
    }
}
