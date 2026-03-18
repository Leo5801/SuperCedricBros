package modele;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.UIManager;

import affichage.FenetrePrincipale;


public class GestionnaireJeu {
	
	
    private FenetrePrincipale fenetre;
    private Map<String, Lieu> catalogueSalles;
    private Lieu lieuActuel;
    private Joueur persoPrincipal;
    

    public GestionnaireJeu(FenetrePrincipale fenetre, Map<String, Lieu> catalogue) {
        this.fenetre = fenetre;
        this.catalogueSalles = catalogue;
        this.lieuActuel = new Lieu();
        this.persoPrincipal = new Joueur(100, 100);
    }
    

    public void afficherLieu(Lieu nouveauLieu) {
    	Lieu ancienLieu = this.lieuActuel;
    	this.lieuActuel = nouveauLieu;
		rafraichirAffichage();
		
		//si le joueur se retourne on vide pas le texte et on n'affiche pas la description
    	if(ancienLieu.getLieuOpposé() != nouveauLieu) {
    		
    		fenetre.viderZoneTexte();
            fenetre.afficherDescription(lieuActuel.getDescription());
    	} 
    	
    	this.monik();
    }
    
    
    public void monik() {
    	
    	Random aleatoire = new Random();
    	
    	
    	if(aleatoire.nextInt(8) == 0) {
    		this.changementPvJoueur(-25);
    		
    		
    		Lieu monik = this.catalogueSalles.get("monik");
    		this.fenetre.setSalle(monik.getNom());
    		this.fenetre.viderActions();
    		this.fenetre.afficherTexte(monik.getDescription());
    		
    		Timer timerRetour = new Timer(3000, e -> {
                // Cette partie s'exécute APRES les 3 secondes
                this.rafraichirAffichage(); // On utilise ta méthode existante pour tout rafraîchir
                this.fenetre.afficherTexte("*Monik a disparu... pour l'instant.*");
            });

            timerRetour.setRepeats(false); // TRÈS IMPORTANT : pour que ça n'arrive qu'une fois
            timerRetour.start();
    	}
    }

    
    // Cette méthode est la big boss de l'affichage
    private void rafraichirAffichage() {
        fenetre.viderActions();
        fenetre.setSalle(lieuActuel.getNom());
        
        //on affiche tous les boutons des actions
        for(Action a : lieuActuel.getActions()) {
            	genererBouton(a);
        }
        
        
        //on affiche tous les dialogues avec PNJ
        for(Pnj p : lieuActuel.getPersos()) {
        	JButton btn = new JButton("Parler à " + p.getNom());
        	
        	btn.addActionListener(e -> {
        		this.afficherDialoguePnj(p.getNomPortrait(), "Eroll : " + p.getDialogue()[0]);
        	});
    
            fenetre.ajouterBoutonAction(btn);
        }
        
        //on affiche les boutons pour prendre les objets
        for(Item i : lieuActuel.getObjets()) {
        	
        	JButton btn = new JButton(i.getLabel());
        	int index = fenetre.premierSlotDispo();
        	
        	btn.addActionListener(e -> {
        		if(i.getIsItemDeQuete()) {
        			
        		} else {
        			this.persoPrincipal.ajouterItem(index, i);
        			this.fenetre.setObjetUsuel(i);
        		}
        		lieuActuel.retirerObjet(i);
        		this.rafraichirAffichage();
        	});
        	
        	fenetre.ajouterAction(btn);
        }
        
        fenetre.setPortrait(lieuActuel.getMiniMap());
    }
        
        
    private void genererBouton(Action a) {
        JButton btn = new JButton(a.getLabel());
        
        
        btn.addActionListener(e -> {
            a.executer(this);
        });
        
        
        fenetre.ajouterBoutonAction(btn);
    }
    
    
    private void afficherDialoguePnj(String leNomPortrait, String leTexte) {
    	fenetre.dialoguePnj(leNomPortrait, leTexte);
    	fenetre.lancerTimerRetourMinimap(this.lieuActuel.getMiniMap());
    }
    
    
    public void changerVue(String laNouvelleVue, String texte) {
    	fenetre.setSalle(laNouvelleVue);
    	Component[] mesComposants = fenetre.getBoutons();
    	
    	JButton btn = new JButton("s'éloigner");
    	
    	
    	fenetre.viderActions();
    	
        
        btn.addActionListener(e -> {
            fenetre.setSalle(lieuActuel.getNom());
            fenetre.viderActions();
            for(Component c : mesComposants) {
            	if(c instanceof JButton) {
            		JButton bouton = (JButton) c;
            		fenetre.ajouterBoutonAction(bouton);
            	}
            }
        });
        
        fenetre.afficherTexte(texte);
        fenetre.ajouterBoutonAction(btn);
    }
    
    
    public FenetrePrincipale getFenetre() {
    	
    	return this.fenetre;
    }
    
    
    public void changementPvJoueur(int montant) {
    	this.persoPrincipal.changerPv(montant);
    	fenetre.getBarreVie().setValue(this.persoPrincipal.getPvActuel());
    	this.rafraichirAffichage();
    }
    
    
    public void clicSurSlot(int index) {
        Item itemChoisi = this.persoPrincipal.getInventaire()[index];
        
        if (itemChoisi != null) {
            this.fenetre.afficherTexte("Cédric utilise : " + itemChoisi.getNom());
            this.changementPvJoueur(itemChoisi.getPvRendue());
            // C'est ici que la magie opère !
            // Si ton item est une Action (Pattern Command), tu fais :
            // itemChoisi.executer(this);
            
            // Puis on vide le slot si l'objet est consommé
            this.persoPrincipal.getInventaire()[index] = null;
            this.fenetre.mettreAJourSlot(this.fenetre.getSlotObjet()[index], "vide");
        }
    }
    
    
    public static void main(String[] args) {
    	try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}
    	

        FenetrePrincipale fenetre = new FenetrePrincipale();
        GenerateurJeu remplissage = new GenerateurJeu();
        remplissage.start();
        
    
        // On crée le contrôleur
        GestionnaireJeu controleur = new GestionnaireJeu(fenetre, remplissage.getCatalogueSalles());
        fenetre.setControleur(controleur);
        fenetre.getBarreVie().setValue(controleur.persoPrincipal.getPvActuel());
        
        // On lance le premier lieu
        controleur.afficherLieu(controleur.catalogueSalles.get("hallDevant"));
        
        
        fenetre.setVisible(true);
    }
}
