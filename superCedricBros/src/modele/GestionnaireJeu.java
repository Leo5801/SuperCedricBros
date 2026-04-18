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
    private GenerateurJeu generateur;
    private Lieu lieuActuel;
    private Joueur persoPrincipal;
    private EtatJeu etat;
    

    public GestionnaireJeu(FenetrePrincipale fenetre, GenerateurJeu generateur) {
        this.fenetre = fenetre;
        this.generateur = generateur;
        this.lieuActuel = this.generateur.getCatalogueSalles().get("hallDevant");
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
        
        case VICTOIRE:
        	this.afficherEcranVictoire();
        	break;
        
        case GAMEOVER:
        	this.afficherEcranGameOver();
            
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
		
    	this.fenetre.masquerComposants();
		this.fenetre.viderActions();
		this.fenetre.setPortrait("");
		this.fenetre.setSalle("win");
		this.fenetre.afficherTexte("\n Cedric : youpi");
    	this.fenetre.genererBoutonRestart();
    	this.fenetre.genererBoutonSupprimer(this.persoPrincipal.getNom());
	}
    
    public void reStart() {
        
        this.persoPrincipal = new Joueur("", 100, 100);

        this.generateur = new GenerateurJeu();
        this.generateur.start();
        this.lieuActuel = this.generateur.getCatalogueSalles().get("hallDevant");
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
        	if(p.getNom().equals("eroll")) {
        		this.interactionPnjQueteEroll(p);
        	
        	} else if(p.getNom().equals("marcus")) {
        		this.gererMarcus(p);
        		
        	} else {
        		fenetre.genererBoutonPnj(p,p.parler());       			
        	}
        }
        
        //on affiche les boutons pour prendre les objets
        for(Item i : lieuActuel.getObjets()) {
        	fenetre.genererBoutonItem(i);
        }
        
        fenetre.setPortrait(lieuActuel.getMiniMap()); //le revalidate et repaint se fait ici
    }
    
    
    private void gererMarcus(Pnj marcus) {
		
		if(this.persoPrincipal.aToutGagne() && (this.persoPrincipal.getInventaireQuete().getNom().equals("certificat"))) {
			fenetre.genererBoutonVictoire(marcus);
			
			
		} else {
			fenetre.genererBoutonPnj(marcus,marcus.parler());   
		}
	}


	private void interactionPnjQueteEroll(Pnj lePnj) {
		
		int num = this.persoPrincipal.getEtapeQueteEroll();
		
		switch(num) {
		case 0:
			fenetre.genererBoutonDebut(lePnj, "je peux t'aider à sortir, mais faut que tu m'aides. T'es partant ? Par contre Rémi me déteste si tu choisis de m'aider compte pas sur lui !");
			break;
			
			//le joueur vient d'accepter la quête d'Eroll et doit trouver le badge
		case 1:
			fenetre.genererBoutonPnj(lePnj, lePnj.getDialogueQuete());
			break;
		
			//le joueur vient de trouver le badge
		case 2:
			fenetre.genererBoutonEtape2Eroll(lePnj, "Merci, pour le badge, ce truc là ça permet d'avoir 0.5 points de plus sur la moyenne ! C'est précieux, c'est pour ça que je l'ai perdu");
			break;
			
			//le joueur a accepté la prochaine quête : trouver la casquette
		case 3:
			fenetre.genererBoutonPnj(lePnj, lePnj.getDialogueQuete());
			break;
		
			//le joueur vient de trouver la casquette
		case 4:
			fenetre.genererBoutonEtape4Eroll(lePnj, "Olala merci tu me sauves là, je t'en dois une belle ! bon c'est pas tout mais il est 9h00m00s01ms, c'est l'heure de la pause café non ? Rejoins-moi dans le hall");
			break;
			
			//le joueur vient de rendre la casquette et a accepté la prochaine quete 
		case 5:
			fenetre.genererBoutonPnj(lePnj, lePnj.getDialogueQuete());
			break;
			
			//mauvais code
		case 6:
			fenetre.genererBoutonErollEtape6(lePnj, lePnj.getDialogueQuete());
			break;
		
			//bon code
		case 7:
			fenetre.genererBoutonErollEtape7(lePnj, "Ah voilà mon café du Seigneur, allez tiens prends la clé USB");
			break;
		
		case 8:
			fenetre.genererBoutonPnj(lePnj, lePnj.getDialogueQuete());
			break;
		}
	}


	public void monik() {
    	
    	Random aleatoire = new Random();
    	
    	
    	/*if(aleatoire.nextInt(8) == 0) {
    		Lieu monik = this.generateur.getCatalogueSalles().get("monik");
    		this.fenetre.setSalle(monik.getNom());
    		this.fenetre.viderActions();
    		this.fenetre.afficherTexte(monik.getDescription());
    		
    		
    		Timer timerRetour = new Timer(3000, e -> {
                
                this.fenetre.afficherTexte("*Monik a disparu... pour l'instant.*");
                this.changementPvJoueur(-25);
            });

            timerRetour.setRepeats(false);
            timerRetour.start();
    	}*/
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
                
                this.rafraichirAffichage();
                
            } else {
                fenetre.afficherTexte("Cedric : j'ai les poches pleines");
            }
        	
        } else if(i.getIsItemDeQuete()) { //1 seul item de quête à la fois tout est encadré mais on sait jamais on supprime l'ancien, tant pis !
        	if(this.persoPrincipal.getInventaireQuete() != null) {
        		if(!i.getNom().equals("certificat")) {
        			fenetre.afficherTexte("Cedric : j'ai les poches pleines");
            		return; 
        		} else {
        			this.persoPrincipal.retirerItemQuete();
        		}
        	} 
        	
        	this.persoPrincipal.ajouterItemQuete(i);
            lieuActuel.retirerObjet(i);
            this.verifierObjetQuete(i);
            this.fenetre.setObjetQuete(i);
            this.fenetre.afficherTexte(i.getTexteRamassage());
            	
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
            p.setEtatActuel(this.etat);
            p.setJ(this.persoPrincipal);

            // --- Sauvegarde de l'inventaire ---
            String[] nomsItemsInventaire = new String[3];
            for (int i = 0; i < 3; i++) {
                Item it = this.persoPrincipal.getInventaire()[i]; 
                nomsItemsInventaire[i] = (it != null) ? it.getNom() : null;
            }
            p.setNomsItemsInventaire(nomsItemsInventaire);

            // --- Sauvegarde dynamique du Monde ---
            for (Lieu l : this.generateur.getCatalogueSalles().values()) {
                // Sauvegarde des items par salle
                List<String> itemsDansSalle = new ArrayList<>();
                for (Item it : l.getObjets()) {
                    itemsDansSalle.add(it.getNom());
                }
                p.getEmplacementItems().put(l.getNom(), itemsDansSalle);

                // Sauvegarde des PNJ par salle
                List<String> pnjDansSalle = new ArrayList<>();
                for (Pnj perso : l.getPersos()) {
                	if(perso != null) {
                		pnjDansSalle.add(perso.getNom());
                	}
                }
                p.getEmplacementPnj().put(l.getNom(), pnjDansSalle);
            }

            sauvegarde.GestionnaireSauvegarde.sauvegarder(p, this.persoPrincipal.getNom());
            fenetre.afficherTexte("Partie sauvegardée avec succès !");
        } catch (Exception e) {
            fenetre.afficherTexte("Erreur lors de la sauvegarde.");
            e.printStackTrace();
        }
    }
	
	
    public void charger() {
        try {
            Partie p = sauvegarde.GestionnaireSauvegarde.charger(this.persoPrincipal.getNom());

            //On réinitialise le monde via les fichiers texte (Monde à l'état "neuf")
            this.generateur.start(); 
            this.fenetre.initialiserComposants();
            
            //On restaure le joueur et l'état
            this.persoPrincipal = p.getJ();
            this.persoPrincipal.setInventaire(new Item[3]);
          
            
            this.etat = p.getEtatActuel();
            
            //l'inventaire
            for (int i = 0; i < 3; i++) {
                String nomItem = p.getNomsItemsInventaire()[i];
                if (nomItem != null) {
                    Item it = this.generateur.getCatalogueItems().get(nomItem);
                    // On remet l'objet physique dans le slot du joueur
                    this.persoPrincipal.ajouterItem(i, it);
                    this.fenetre.setObjetUsuel(it, i);
                } else {
                    this.persoPrincipal.getInventaire()[i] = null;
                }
            }

            //On nettoie et on replace les éléments selon la sauvegarde
            for (Lieu salleCatalogue : this.generateur.getCatalogueSalles().values()) {
                String nomSalle = salleCatalogue.getNom();

                // Restaurer les items de cette salle
                salleCatalogue.getObjets().clear(); // On vide ce que le .txt a mis par défaut
                if (p.getEmplacementItems().containsKey(nomSalle)) {
                    for (String nomItem : p.getEmplacementItems().get(nomSalle)) {
                        salleCatalogue.ajouterObjet(this.generateur.getCatalogueItems().get(nomItem));
                    }
                }

                // Restaurer les PNJ de cette salle
                salleCatalogue.getPersos().clear();
                if (p.getEmplacementPnj().containsKey(nomSalle)) {
                    for (String nomPnj : p.getEmplacementPnj().get(nomSalle)) {
                        salleCatalogue.ajouterPnj(this.generateur.getCataloguePnj().get(nomPnj));
                    }
                }
            }
            

            //On restaure le lieu actuel
            this.lieuActuel = this.generateur.getCatalogueSalles().get(p.getNomLieuActuel());

            if(this.persoPrincipal.isaChoisiEroll()) {
            	this.gereQueteEroll();
            }
            //On rafraîchit l'interface graphique
            this.rafraichirAffichage(); 
            
            fenetre.afficherTexte("Chargement terminé !");

        } catch (Exception e) {
            e.printStackTrace();
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
    
    
    public void accepterQueteEroll() {
    	
        if(this.persoPrincipal.isaChoisiEroll()==false && this.persoPrincipal.isaChoisiRemi()==false) {
        	this.persoPrincipal.setaChoisiEroll(true);
        	this.persoPrincipal.setEtapeQueteEroll(1);
        	this.gereQueteEroll();
        	this.rafraichirAffichage();
        	Pnj p = this.getPnj("eroll");
        	this.afficherDialoguePnj(p.getNomPortrait(), p.getNom() + " : " + p.getDialogueQuete());
        }
    }
    

	public void gereQueteEroll() {
		
		if(this.persoPrincipal.isaChoisiEroll()==true) {
			int num = this.persoPrincipal.getEtapeQueteEroll();
			Pnj eroll = this.getPnj("eroll");
			
			switch(num) {
			//le joueur vient d'accepter la quête d'Eroll
			case 1:
				eroll.setDialogueQuete("T'y es un tigre toi, bon moi je veux pas sortir si je croise Monik c'est ma fin. Faut que t'ailles faire des besognes pour moi, t'inquiète il te suffit d'explorer et de chercher des objets, facile non ? d'ailleurs en salle info y a mes gâtés de la L3G tu devrais leur dire coucou. Bref, moi j'ai perdu mon badge, ramène-le moi, je l'avais avant de taper ma séance pec, tu veux toucher ?");
				this.generateur.getCatalogueItems().put("badge",new Item("badge", "Soulever l'haltère", "Cédric : J'ai le badge ! Je dois vite le ramener à Eroll.",0, true));
				this.getSalle("salleRemiDevant").ajouterObjet(this.getItem("badge"));
				break;
			
			//le joueur vient de trouver le badge
			case 2:
				eroll.setNomPortrait("erollSansCasquette");
				this.getSalle("bureauBdeDevant").setNom("bureauBdeDevantSansCasquette");
				this.getSalle("bureauBdeDerriere").setNom("bureauBdeDerriereSansCasquette");
				break;
				
			//le joueur a rendu le badge
			case 3:
				this.persoPrincipal.validerQueteEroll(0);
				this.persoPrincipal.retirerItemQuete();
				this.fenetre.setObjetQuete(null);
				this.generateur.getCatalogueItems().remove("badge");
				
				eroll.setDialogueQuete("Mon frère ultime, tu peux le voir à ma beauté anormale, j'ai perdu ma casquette. Petit hic :  j'ai la copine de mon meilleur ami qui se balade dans la fac, si elle me voit comme ça tu peux être sûr qu'elle va me sauter dessus alors aide-moi !");
				
				
				this.generateur.getCatalogueItems().put("casquette", new Item("casquette", "arroser la plante", "Cedric : la fameuse casquette, allez je la remène à l'autre BG !", 0, true));
				this.getSalle("salleMichuDerriere").ajouterObjet(this.getItem("casquette"));
				
				this.rafraichirAffichage();
				this.afficherDialoguePnj(eroll.getNomPortrait(), eroll.getNom() + " : " + eroll.getDialogueQuete());
				break;
			
			//le joueur a accepté la dernière quête
			case 5:
				this.persoPrincipal.validerQueteEroll(1);
				this.persoPrincipal.retirerItemQuete();
				this.fenetre.setObjetQuete(null);
				this.generateur.getCatalogueItems().remove("casquette");
				
				eroll.setDialogueQuete("C'est bon avec ma casquette personne ne voit la beauté de mon crâne. Bon, j'allais te donner la clé USB pour imprimer ton certificat mais la machine à café demande un code. Sans café je peux rien faire, amène-moi un café ou trouve le code du distributeur et tu pourras sortir pépère");
				
				this.getSalle("bureauBdeDevant").setNom("bureauBdeDevantSansEroll");
				this.getSalle("bureauBdeDerriere").setNom("bureauBdeDerriereSansEroll");
				this.getSalle("bureauBdeDevant").retirerPnj(eroll);
				
				this.getSalle("hallDerriere").ajouterPnj(eroll);
				this.getSalle("hallDerriere").setNom("hallDerriereAvecEroll");
				
				this.getSalle("salleInfoDerriere").ajouterObjet(new Item("code","Fouiller la poubelle","Cedric : bon ça doit être le bon code",0,true));
				
				this.getSalle("salleInfoDevant").ajouterObjet(new Item("codeFaux1","Regarder sous le coude de Léo","Cedric : bon ça doit être le bon code",0,true));
				this.getSalle("couloir").ajouterObjet(new Item("codeFaux2","Regarder dans sa chaussure","Cedric : bon ça doit être le bon code",0,true));
				this.getSalle("bureauBdeDevant").ajouterObjet(new Item("codeFaux3","Fouiller la poubelle","Cedric : bon ça doit être le bon code",0,true));
				
				eroll.setNomPortrait("erollAvecCasquette");
				
				this.rafraichirAffichage();
				break;
				
			//le joueur a le mauvais code
			case 6:
				eroll.setDialogueQuete("Pas le bon code mon coco");
				break;
			
				
			//le joeuur a le bon code et la clé USB
			case 8:
				eroll.setDialogueQuete("Allez échappe-toi avant que je t'embrasse !.. nan que je m'embrase pardon...");
				
				this.persoPrincipal.validerQueteEroll(2);
				this.generateur.getCatalogueItems().remove("code");
				for(int i=1; i<=3; i++) {
					this.generateur.getCatalogueItems().remove("codeFaux"+i);
				}
				
				this.persoPrincipal.retirerItemQuete();
				this.fenetre.setObjetQuete(null);
				this.ramasserObjet(new Item("usb", "", "Cedric : allez faut l'imprimer", 0, true));
				
				this.getSalle("salleInfoDevant").ajouterObjet(new Item("certificat", "Imprimer le certificat", "Cedric : enfin, allez je vais voir Marcus", 0 , true));
				this.rafraichirAffichage();
			}
		}
	}
	
	private void verifierObjetQuete(Item i) {
		
		if(i.getNom().equals("badge")) {
			this.persoPrincipal.setEtapeQueteEroll(2);
			this.gereQueteEroll();
			return;
			
		} else if(i.getNom().equals("casquette")) {
			this.persoPrincipal.setEtapeQueteEroll(4);
			this.gereQueteEroll();
			return;
			
		} else if(i.getNom().contains("codeFaux")) {
			this.persoPrincipal.setEtapeQueteEroll(6);
			this.gereQueteEroll();
			return ;
			
		} else if(i.getNom().equals("code")) {
			this.persoPrincipal.setEtapeQueteEroll(7);
			this.gereQueteEroll();
			return ;
			
		} else if(i.getNom().equals("certificat")) {
			
			this.persoPrincipal.validerQueteEroll(3);
			this.rafraichirAffichage();
			return ;
		}
	}
	
	
	private Lieu getSalle(String nom) {
		
		return this.generateur.getCatalogueSalles().get(nom);
	}
	
	private Pnj getPnj(String nom) {
		
		return this.generateur.getCataloguePnj().get(nom);
	}
	
	private Item getItem(String nom) {
		
		return this.generateur.getCatalogueItems().get(nom);
	}


	public Lieu getLieuActuel() {
    	
    	return this.lieuActuel;
    }
	
	
	public void changeEtapeQueteEroll(int etape) {
		
		this.persoPrincipal.setEtapeQueteEroll(etape);
	}
	
    
    public static void main(String[] args) {
    	try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}
    	

        FenetrePrincipale fenetre = new FenetrePrincipale();
        GenerateurJeu remplissage = new GenerateurJeu();
        remplissage.start();
        
    
        // On crée le contrôleur
        GestionnaireJeu controleur = new GestionnaireJeu(fenetre, remplissage);
        controleur.Init();
        

        fenetre.setVisible(true);
    }


	public void viderInventaireQuete() {
		
		this.persoPrincipal.retirerItemQuete();
		this.fenetre.setObjetQuete(null);
	}
}
