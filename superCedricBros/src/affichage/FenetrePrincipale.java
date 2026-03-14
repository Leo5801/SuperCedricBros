package affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class FenetrePrincipale extends JFrame{
	
	private JLabel imagePiece;
	private JPanel zoneBoutonsChoix;
	private JProgressBar barreVie;       
    private JLabel slotItemQuete;        
    private JLabel portraitPNJ;
    private JTextArea zoneTexte;
    private JLabel slotObjet;
	
	private Map<String, ImageIcon> cacheImages; // petit tableau associatif qui contiendra les maps et les portraits 
	
	
	public FenetrePrincipale() {
		
		setTitle("SUPER CEDRIC BROS");
		setSize(1300,850);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Espacement entre les zones
        
        this.cacheImages = new HashMap<>();//on crée le tableau associatif
        this.prepareCache("map",900,500); //on remplit ce dernier des images au bon format, pas besoin d'aller les rechercher à chaque changement
        this.prepareCache("miniMap", 220, 300);
        this.prepareCache("portrait", 220, 360);
        
        //on charge la fenêtre
        this.initialiserComposants();
	}
	
	
	
	private void initialiserComposants() {
		
		//On commence par placer l'image centrale de la pièce
		this.imagePiece = new JLabel();
		this.imagePiece.setHorizontalAlignment(JLabel.CENTER);
        add(this.imagePiece, BorderLayout.CENTER);
        
        
        //on crée la zone des boutons, on l'ajoute au sud de la fenêtre (espace dédié)
        JPanel panneauSud = new JPanel(new BorderLayout());
        panneauSud.setPreferredSize(new Dimension(0, 300));
        
        //on ajoute les boutons en bas
        this.zoneBoutonsChoix = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        this.zoneBoutonsChoix.setPreferredSize(new Dimension(0, 100));
        panneauSud.add(this.zoneBoutonsChoix, BorderLayout.SOUTH);
       
        //on ajoute la zone de texte défilante
        this.zoneTexte = new JTextArea();
        this.zoneTexte.setEditable(false);
        this.zoneTexte.setLineWrap(true);
        this.zoneTexte.setWrapStyleWord(true);
        this.zoneTexte.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        this.zoneTexte.setMargin(new Insets(10, 15, 10, 15));
        
        JScrollPane scrollTexte = new JScrollPane(this.zoneTexte);
        scrollTexte.setBorder(BorderFactory.createTitledBorder("Journal d'aventure"));
        panneauSud.add(scrollTexte, BorderLayout.CENTER);
        add(panneauSud, BorderLayout.SOUTH);
        
        
        //on crée le panneau EST qui contiendra la barre de vie et l'inventaire
        JPanel panneauEst = new JPanel();
        panneauEst.setLayout(new BoxLayout(panneauEst, BoxLayout.Y_AXIS));
        panneauEst.setPreferredSize(new Dimension(220, 0));
        // On ajoute des marges internes 
        panneauEst.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); 

        // l'énergie
        JLabel lblVie = new JLabel("Énergie de Cédric :");
        lblVie.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblVie.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauEst.add(lblVie);
        panneauEst.add(Box.createVerticalStrut(10)); // Petit espace

        this.barreVie = new JProgressBar(0, 100);
        this.barreVie.setValue(100);
        this.barreVie.setForeground(new Color(46, 204, 113)); 
        this.barreVie.setStringPainted(true);
        this.barreVie.setMaximumSize(new Dimension(180, 25));
        panneauEst.add(this.barreVie);


        // the sac à dos 
        JPanel panneauInventaire = new JPanel();
        panneauInventaire.setLayout(new BoxLayout(panneauInventaire, BoxLayout.Y_AXIS));
        // Une belle bordure titrée pour englober les objets
        panneauInventaire.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Sac à dos", 
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));
        panneauInventaire.setMaximumSize(new Dimension(200, 220)); 

        //  Le nouveau slot : Objet en main 
        panneauInventaire.add(Box.createVerticalStrut(15));
        JLabel lblObjetMain = new JLabel("Objet usuel :");
        lblObjetMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(lblObjetMain);

        this.slotObjet = new JLabel("--- VIDE ---", SwingConstants.CENTER);
        this.slotObjet.setPreferredSize(new Dimension(140, 40));
        this.slotObjet.setMaximumSize(new Dimension(140, 40));
        this.slotObjet.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.slotObjet.setBackground(new Color(230, 230, 230)); // Fond légèrement grisé
        this.slotObjet.setOpaque(true); // Obligatoire pour voir la couleur de fond
        this.slotObjet.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(this.slotObjet);

        //  L'ancien slot : Objet de Quête 
        panneauInventaire.add(Box.createVerticalStrut(15));
        JLabel lblObjetQuete = new JLabel("Objet de quête :");
        lblObjetQuete.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(lblObjetQuete);

        this.slotItemQuete = new JLabel("--- VIDE ---", SwingConstants.CENTER);
        this.slotItemQuete.setPreferredSize(new Dimension(140, 40));
        this.slotItemQuete.setMaximumSize(new Dimension(140, 40));
        // Une bordure dorée/orange pour marquer l'importance de la quête
        this.slotItemQuete.setBorder(BorderFactory.createLineBorder(new Color(200, 150, 50), 2)); 
        this.slotItemQuete.setBackground(new Color(230, 230, 230));
        this.slotItemQuete.setOpaque(true);
        this.slotItemQuete.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(this.slotItemQuete);
        panneauInventaire.add(Box.createVerticalStrut(15)); // Marge du bas

        // On ajoute le groupe inventaire au panneau Est
        panneauEst.add(panneauInventaire);

        // On ajoute tout ça à la fenêtre
        add(panneauEst, BorderLayout.EAST);

        
        // 4. OUEST : le PNJ qui parle
        this.portraitPNJ = new JLabel();
        this.portraitPNJ.setPreferredSize(new Dimension(220, 0));
        this.portraitPNJ.setVerticalAlignment(JLabel.CENTER); 
        this.portraitPNJ.setHorizontalAlignment(JLabel.CENTER);
        this.portraitPNJ.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.TOP));
        this.portraitPNJ.setBackground(new Color(245, 240, 230));
        this.portraitPNJ.setOpaque(true);
        add(this.portraitPNJ, BorderLayout.WEST);
        
	}
	
	
	private void prepareCache(String nomDossier, int largeur, int hauteur) {
		//on vient ici remplir le tableau associatif on créer un fichier qui correspond au dossier source des images
		File dossier = new File("ressources/" + nomDossier);
		
		//on vérifie qu'il existe et qu'il est bien considéré comme ressources
		if(dossier.exists() && dossier.isDirectory()) {
			
			//on crée un tableau de fichiers qui contiendra toutes nos images
			File[] images = dossier.listFiles();
			
			if(images != null) {
				
				//on parcourt toutes nos images
				for(File i : images) {
					
					//on vérifie que l'image est bien une image
					if(i.isFile() && (i.getName().endsWith(".png") || i.getName().endsWith(".jpg"))) {
						
						//on créer une ImageIcon de notre image
						ImageIcon monImage = new ImageIcon(i.getPath());
						
						
						//on la redimensionne
						Image img = monImage.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
						monImage = new ImageIcon(img);
						
						
						//on l'ajoute au tableau associatif cacheImages
						this.cacheImages.put(i.getName().substring(0,i.getName().lastIndexOf(".")), monImage);
					}
				}
			}
		}
	}
	
	
	public void setSalle(String nomSalle) {
		
	    if (this.cacheImages.containsKey(nomSalle)) {
	        ImageIcon laSalle = this.cacheImages.get(nomSalle);
	        this.imagePiece.setIcon(laSalle);
	        
	        // On force le rafraichissement on sait jamais
	        this.imagePiece.revalidate();
	        this.imagePiece.repaint();
	    }
	}
	
	
	public void ajouterAction(JButton bouton) {
		
		this.zoneBoutonsChoix.add(bouton);
		this.zoneBoutonsChoix.revalidate();
	}
	
	
	public void viderActions() {
		
        this.zoneBoutonsChoix.removeAll();
        this.zoneBoutonsChoix.revalidate();
        this.zoneBoutonsChoix.repaint();
    }
	

    public void ajouterBoutonAction(JButton btn) {
    	
        this.zoneBoutonsChoix.add(btn);
        this.zoneBoutonsChoix.revalidate();
    }
    

    public void setVie(int valeur) {
    	
        this.barreVie.setValue(valeur);
        if (valeur < 30) this.barreVie.setForeground(Color.RED);
    }
    

    public void setPortrait(String nomImage) {
    	
        if(this.cacheImages.containsKey(nomImage)) {
            this.portraitPNJ.setIcon(this.cacheImages.get(nomImage));
            if (nomImage.startsWith("miniMap")) {
                this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Localisation"));
            } else {
                this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Interlocuteur"));
            }
            
        } else {
            
            this.portraitPNJ.setIcon(null);
            this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Probleme"));
        }
        
        this.portraitPNJ.revalidate();
        this.portraitPNJ.repaint();
    }
    
    
    public void afficherTexte(String leTexte) {
    	
    	this.zoneTexte.append("> " + leTexte + "\n");
        this.zoneTexte.setCaretPosition(this.zoneTexte.getDocument().getLength());
        this.zoneTexte.revalidate();
    	this.zoneTexte.repaint();
    }
    
    
    public void dialoguePnj(String leNomPnj, String leTexte) {
    	
    	this.zoneTexte.append("> " + leTexte + "\n");
        this.zoneTexte.setCaretPosition(this.zoneTexte.getDocument().getLength());
        this.setPortrait(leNomPnj);
        this.zoneTexte.revalidate();
    	this.zoneTexte.repaint();
    }
    
    
    public void viderZoneTexte() {
    	
    	this.zoneTexte.setText("");
    	this.zoneTexte.revalidate();
    	this.zoneTexte.repaint();
    }
    
    
    public void lancerTimerRetourMinimap(String nomMiniMap) {
      
        Timer timer = new Timer(5000, e -> {
            
            this.portraitPNJ.setBorder(BorderFactory.createTitledBorder(
                null, "Localisation", TitledBorder.CENTER, TitledBorder.TOP));
            
            this.setPortrait(nomMiniMap);
        });
        
        timer.setRepeats(false);
        timer.start();
    }
    
    
    public void afficherDescription(String leTexte) {
    	
    	this.zoneTexte.append("*" + leTexte + "*\n\n"); 
    	this.zoneTexte.setCaretPosition(this.zoneTexte.getDocument().getLength());
    	this.zoneTexte.revalidate();
    	this.zoneTexte.repaint();
    }
    
    
    public Component[] getBoutons() {
    	
    	return this.zoneBoutonsChoix.getComponents();
    }
}
