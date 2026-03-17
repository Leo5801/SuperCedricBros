package modele;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.UIManager;

import affichage.FenetrePrincipale;


public class GestionnaireJeu {
	
	
    private FenetrePrincipale fenetre;
    private Map<String, Lieu> catalogue;
    private Lieu lieuActuel;
    private boolean aCasquette;
    

    public GestionnaireJeu(FenetrePrincipale fenetre, Map<String, Lieu> catalogue) {
        this.fenetre = fenetre;
        this.catalogue = catalogue;
        this.aCasquette = true;
    }

    

    public void afficherLieu(String nomLieu) {
        //si on change de pièce on repasse devant
        this.lieuActuel = catalogue.get(nomLieu);
        rafraichirAffichage();
        fenetre.viderZoneTexte();
        fenetre.afficherDescription(lieuActuel.getDescription());
    }

    // Cette méthode est la big boss de l'affichage
    private void rafraichirAffichage() {
    	
        fenetre.viderActions();
        fenetre.setSalle(lieuActuel.getNom());
        
        
        for (Action a : lieuActuel.getActions()) {
            	genererBouton(a);
        }
   
        
        fenetre.setPortrait(lieuActuel.getMiniMap());
    }
    

    
    private void genererBouton(Action a) {
    	
        JButton btn = new JButton(a.getLabel());
        
        
        btn.addActionListener(e -> {
            if(a instanceof ActionChangementMap) {
                // On "cast" l'action pour accéder aux méthodes spécifiques du changement de map
                ActionChangementMap acm = (ActionChangementMap) a;
                this.afficherLieu(acm.getDestination());
                
            } else if(a instanceof ActionChangementVue) {
            	
            	ActionChangementVue ad = (ActionChangementVue) a;
            	this.changerVue(ad.getDestination());
            	fenetre.afficherTexte(ad.getTexte());
            	
            } else if(a instanceof ActionDialoguePnj) {
                // On "cast" pour accéder au texte du dialoguePnj
                ActionDialoguePnj ad = (ActionDialoguePnj) a;
                this.afficherDialoguePnj(ad.getLeNomDuPerso(), ad.getTexte());
                
            } else if(a instanceof ActionDialogue) {
            	// On "cast" pour accéder au texte du dialoguePnj
            	ActionDialogue ad = (ActionDialogue) a;
            	fenetre.afficherTexte(ad.getTexte());
            	
            }
        });
        
        
        fenetre.ajouterBoutonAction(btn);
    }
    
    
    private void afficherDialoguePnj(String leNomPnj, String leTexte) {
    	
    	if(leNomPnj == "eroll") {
    		if(aCasquette) {
    			leNomPnj += "AvecCasquette";
    		} else {
    			leNomPnj += "SansCasquette";
    		}
    	}
    	
 
    	fenetre.dialoguePnj(leNomPnj, leTexte);
    	fenetre.lancerTimerRetourMinimap(this.lieuActuel.getMiniMap());
    }
    
    
    public void changerVue(String laNouvelleVue) {
    	
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
        
        fenetre.ajouterBoutonAction(btn);
    }
    
    
    public static void main(String[] args) {

    	try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}
    	

        FenetrePrincipale fenetre = new FenetrePrincipale();
        Map<String, Lieu> catalogue = GenerateurJeu.creerLeMonde();
        
        
        for(String clé : catalogue.keySet()) {
        	
        	if(clé != "couloir") {
        		Lieu lieuCourant = catalogue.get(clé);
            	lieuCourant.ajouterAction(new ActionChangementMap("Se retourner", lieuCourant.getLieuOpposé()));
        	}
        }
        
        // On crée le contrôleur
        GestionnaireJeu controleur = new GestionnaireJeu(fenetre, catalogue);
        
        // On lance le premier lieu
        controleur.afficherLieu("hallDevant");
        
        
        fenetre.setVisible(true);
    }
}
