package affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import modele.Action;
import modele.EtatJeu;
import modele.GestionnaireJeu;
import modele.Item;
import modele.Pnj;
import modele.Vue;

public class FenetrePrincipale extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JLabel imagePiece;
	private JPanel zoneBoutonsChoix;
	private JProgressBar barreVie;       
    private JLabel slotItemQuete;        
    private JLabel portraitPNJ;
    private JTextArea zoneTexte;
    private JLabel[] slotObjet;
    private GestionnaireJeu controleur;
    private Timer timerMinimap;
    private javax.swing.JTextField champNom;
    private JLabel labelMessageErreur;
    private String regles="";
	
	private Map<String, ImageIcon> cacheImages; // petit tableau associatif qui contiendra les maps et les portraits 
	
	

	public FenetrePrincipale() {
		
		setTitle("SUPER CEDRIC BROS");//le petit titre, pas de problème de copyright j'espère
		setSize(1280,800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Espacement entre les zones
        this.slotObjet = new JLabel[3]; // l'inventaire
        
        this.cacheImages = new HashMap<>();//on crée le tableau associatif
        this.prepareCache("map",900,550); //on remplit ce dernier des images au bon format, pas besoin d'aller les rechercher à chaque changement
        this.prepareCache("miniMap", 220, 300);
        this.prepareCache("portrait", 220, 360);
        this.prepareCache("objet", 50, 50);
        this.chargerRegles();
	}
	
	
	public void chargerRegles() {
		
		File f = new File("règles.txt");
        try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader is = new InputStreamReader(fis);
			BufferedReader bis = new BufferedReader(is);
			String mot;
			
			while((mot = bis.readLine()) != null) {
				this.regles += mot;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void initialiserComposants() {
		
		//attention ça va faire mal à la tête
		//On commence par placer l'image centrale de la pièce et à nettoyer l'ancien affichage (on sait jamais?)
		this.getContentPane().removeAll();
		this.getContentPane().setBackground(new Color(250,250,250));
		
		this.setLayout(new BorderLayout(10, 10)); 
		this.initialiserMenu();//on affiche ce magnifique menu pour sauvegarder
		this.imagePiece = new JLabel();
		this.imagePiece.setHorizontalAlignment(JLabel.CENTER);
		this.imagePiece.setPreferredSize(new Dimension(900, 450));
        add(this.imagePiece, BorderLayout.CENTER);
        
        
        //on crée la zone des boutons, on l'ajoute au sud de la fenêtre (espace dédié)
        JPanel panneauSud = new JPanel(new BorderLayout());
        panneauSud.setPreferredSize(new Dimension(0, 250));
        
        //on ajoute les boutons en bas
        this.zoneBoutonsChoix = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        this.zoneBoutonsChoix.setPreferredSize(new Dimension(0, 60));
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
        panneauEst.setPreferredSize(new Dimension(160, 0));
        
        // On ajoute des marges internes 
        panneauEst.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); 

        // l'énergie
        JLabel lblVie = new JLabel("Énergie de Cédric :");
        lblVie.setFont(new Font("SansSerif", Font.BOLD, 13));
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
        // une belle bordure titrée pour englober les objets
        panneauInventaire.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Sac à dos", 
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));
        panneauInventaire.setMaximumSize(new Dimension(200, 300)); 

        for(int i=0; i<3; i++) {
        	
        	panneauInventaire.add(Box.createVerticalStrut(15));
            JLabel lblObjetMain = new JLabel("Objet " + (i+1));
            lblObjetMain.setAlignmentX(Component.CENTER_ALIGNMENT);
            panneauInventaire.add(lblObjetMain);
            this.slotObjet[i] = new JLabel("--- VIDE ---", SwingConstants.CENTER);
            this.slotObjet[i].setPreferredSize(new Dimension(140, 40));
            this.slotObjet[i].setMaximumSize(new Dimension(140, 40));
            this.slotObjet[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            this.slotObjet[i].setBackground(new Color(230, 230, 230)); // Fond légèrement grisé
            this.slotObjet[i].setOpaque(true); // Obligatoire pour voir la couleur de fond
            this.slotObjet[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            final int index = i;
            this.slotObjet[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    
                    if (controleur != null) {
                        controleur.clicSurSlot(index);
                    }
                }
            });
            panneauInventaire.add(this.slotObjet[i]);
        } // et oui les slot d'objet sont intéractifs !
        

        //  slot de quête, pas intéractif, moins drôle 
        panneauInventaire.add(Box.createVerticalStrut(15));
        JLabel lblObjetQuete = new JLabel("Objet de quête :");
        lblObjetQuete.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(lblObjetQuete);

        this.slotItemQuete = new JLabel("--- VIDE ---", SwingConstants.CENTER);
        this.slotItemQuete.setPreferredSize(new Dimension(140, 40));
        this.slotItemQuete.setMaximumSize(new Dimension(140, 40));
        // une bordure dorée/orange pour marquer l'importance de la quête
        this.slotItemQuete.setBorder(BorderFactory.createLineBorder(new Color(200, 150, 50), 2)); 
        this.slotItemQuete.setBackground(new Color(230, 230, 230));
        this.slotItemQuete.setOpaque(true);
        this.slotItemQuete.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauInventaire.add(this.slotItemQuete);
        panneauInventaire.add(Box.createVerticalStrut(15)); // Marge du bas

        // Oo ajoute le groupe inventaire au panneau Est
        panneauEst.add(panneauInventaire);

        // on ajoute tout ça à la fenêtre
        add(panneauEst, BorderLayout.EAST);

        
        // 4. ouest : le portrait du pnj
        this.portraitPNJ = new JLabel();
        this.portraitPNJ.setPreferredSize(new Dimension(220, 0));
        this.portraitPNJ.setVerticalAlignment(JLabel.CENTER); 
        this.portraitPNJ.setHorizontalAlignment(JLabel.CENTER);
        this.portraitPNJ.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.TOP));
        this.portraitPNJ.setBackground(new Color(245, 240, 230));
        this.portraitPNJ.setOpaque(true);
        add(this.portraitPNJ, BorderLayout.WEST);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        this.validate(); //pour s'assurer que tout s'affiche
        
	}
	
	
    public void afficherEcranAccueil() {
        // on nettoie
        this.getContentPane().removeAll();
        this.setLayout(new GridBagLayout()); // La fenêtre devient un centreur géant
        this.getContentPane().setBackground(new Color(20, 20, 20)); 

        // le bloc central
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setOpaque(false);

        // bon, le titre quoi...
        JLabel titre = new JLabel("SUPER CEDRIC BROS");
        titre.setFont(new Font("Arial", Font.BOLD, 46));
        titre.setForeground(new Color(200, 150, 50));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // les règles
        JTextArea regles = new JTextArea();
        regles.setText(this.regles);
        regles.setFont(new Font("Serif", Font.ITALIC, 18));
        regles.setForeground(new Color(180, 180, 180));
        regles.setOpaque(false);
        regles.setBackground(new Color(0,0,0,0));
        regles.setEditable(false);
        regles.setFocusable(false);
        regles.setLineWrap(true);
        regles.setWrapStyleWord(true);
        regles.setAlignmentX(Component.CENTER_ALIGNMENT);
        // centrage des règles dans le cadre
        regles.setMargin(new Insets(10, 50, 10, 50)); 
        regles.setMaximumSize(new Dimension(600, 250));

        // pour saisir le nom de la sauvegarde
        this.champNom = new JTextField(15);
        this.champNom.setMaximumSize(new Dimension(300, 45));
        this.champNom.setFont(new Font("SansSerif", Font.BOLD, 22));
        this.champNom.setHorizontalAlignment(JTextField.CENTER);
        this.champNom.setBackground(new Color(45, 45, 45));
        this.champNom.setForeground(Color.WHITE);
        this.champNom.setCaretColor(Color.WHITE);
        this.champNom.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

        // bouton
        JButton btn = new JButton("ENTRER DANS LA FAC");
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
                controleur.receptionnerConnexion(champNom.getText());
        });
        
        //on crée l'espace pour les erreurs
        this.labelMessageErreur = new JLabel(" "); // un espace pour garder la place
        this.labelMessageErreur.setFont(new Font("SansSerif", Font.BOLD, 14));
        this.labelMessageErreur.setForeground(new Color(255, 100, 100)); // Rouge "erreur"
        this.labelMessageErreur.setAlignmentX(Component.CENTER_ALIGNMENT);

        // hop tout dans la casserole
        contenu.add(titre);
        contenu.add(Box.createVerticalStrut(30));
        contenu.add(regles);
        contenu.add(Box.createVerticalStrut(20));
        contenu.add(this.labelMessageErreur); 
        contenu.add(Box.createVerticalStrut(10));
        contenu.add(this.champNom);
        contenu.add(Box.createVerticalStrut(15)); 
        contenu.add(btn);

        
        this.add(contenu); 

        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        this.validate(); // Force la validation 
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
	
	
    public void setVie(int valeur) {
    	
        this.barreVie.setValue(valeur);
        if (valeur < 30) this.barreVie.setForeground(Color.RED);
    }
	
    
	public void mettreAJourVie(int valeur) {
		
		this.barreVie.setValue(valeur);
		if (valeur < 30) {
	        this.barreVie.setForeground(Color.RED);//pour le stress !!
	    } else {
	        this.barreVie.setForeground(new Color(46, 204, 113)); 
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
        this.zoneBoutonsChoix.repaint();
    }
    
    
    public void genererBoutonPnj(Pnj p) {
    	
    	JButton btn = new JButton("Parler à " + p.getNom());
    	
    	btn.addActionListener(e -> {
    		controleur.afficherDialoguePnj(p.getNomPortrait(), p.getNom() + " : " + p.getDialogue()[0]);//le portrait pour l'immersion
    	});

        this.ajouterBoutonAction(btn);
    }
    
    
    public void genererBouton(Action a) {
    	
        JButton btn = new JButton(a.getLabel());
        
        
        btn.addActionListener(e -> {
            a.executer(this.controleur);//pour les Actions classiques
        });
        
        
        this.ajouterBoutonAction(btn);
    }
    
    
    public void genererBoutonItem(Item i) {
    	JButton btn = new JButton(i.getLabel());
    	
    	btn.addActionListener(e -> {
    		this.controleur.ramasserObjet(i);//pour ramasser les items
    	});
    	
    	this.ajouterAction(btn);
    }
    
    
	public void genererBoutonRestart() {
		
		JButton btn = new JButton("Recommencer");
		
		btn.addActionListener(e -> {
			this.controleur.reStart();
		});
		
		this.ajouterAction(btn);
	}



	public void genererBoutonSupprimer(String nom) {
		
		JButton btn = new JButton("Supprimer la sauvegarde courante");
		
		btn.addActionListener(e -> {
			if(sauvegarde.GestionnaireSauvegarde.supprimer(nom)) {//la sauvegarde est forcée en mourrant, le joueur peut ou non supprimer la sauvegarde de l'écran gameOver
				this.afficherTexte("C'est ciao");
				
			} else {
				this.afficherTexte("Problème dans la suppression");
			}
			
		});
		
		this.ajouterAction(btn);
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
    
    
    public void setPortrait(String nomImage) {
    	
    	//on récupère l'image dans le cache
        if(this.cacheImages.containsKey(nomImage)) {
            this.portraitPNJ.setIcon(this.cacheImages.get(nomImage));
            if (nomImage.startsWith("miniMap")) {
                this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Localisation"));
            } else {
                this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Interlocuteur"));
            }
            
            //si on trouve pas, pas de panique !
        } else {
            
            this.portraitPNJ.setIcon(null);
            this.portraitPNJ.setBorder(BorderFactory.createTitledBorder("Probleme"));
        }
        
        //on rafraichit le tout
        this.portraitPNJ.revalidate();
        this.portraitPNJ.repaint();
    }
    
    
    public void lancerTimerRetourMinimap(String nomMiniMap) {
        
    	//Quand le joueur parle à un PNJ son portrait s'affiche pour 5 secondes
    	if (this.timerMinimap != null && this.timerMinimap.isRunning()) {
            this.timerMinimap.stop();
        }

        this.timerMinimap = new Timer(5000, e -> {
            this.portraitPNJ.setBorder(BorderFactory.createTitledBorder(
                    null, "Localisation", TitledBorder.CENTER, TitledBorder.TOP));
            
            this.setPortrait(nomMiniMap);
        });

        this.timerMinimap.setRepeats(false);
        this.timerMinimap.start();
    }
    
    
    public void afficherDescription(String leTexte) {
    	
    	this.zoneTexte.append("*" + leTexte + "*\n\n"); 
    	this.zoneTexte.setCaretPosition(this.zoneTexte.getDocument().getLength());
    	this.zoneTexte.revalidate();
    	this.zoneTexte.repaint();
    }
    
    
	public void changerVue(Vue v) {
		
		//on supprime les boutons dans gestionnaire, on affiche juste s'éloigner pour hum... s'éloigner ?
		this.setSalle(v.getNom());
    	JButton btn = new JButton("s'éloigner");
    	
    	
    	this.viderActions();
    	
        
        btn.addActionListener(e -> {
        	this.controleur.rafraichirAffichage();
            
        });
        
        this.afficherTexte(v.getDescription());
        this.ajouterBoutonAction(btn);
	}
	
	
	
	public void afficherSaisieNom() {
		
	    this.viderActions();
	    this.champNom = new javax.swing.JTextField(15);
	    this.zoneBoutonsChoix.add(new JLabel("Nom de la sauvegarde : "));
	    this.zoneBoutonsChoix.add(champNom);
	    this.zoneBoutonsChoix.revalidate();
	    this.zoneBoutonsChoix.repaint();
	}

	public String getNomSaisi() {
		
	    return (champNom != null) ? champNom.getText() : "";
	}
	
	
	private void initialiserMenu() {
		
		//il sert juste pour cliquer sur sauvegarder
		if (this.getJMenuBar() != null) return;
		
	    JMenuBar menuBar = new JMenuBar();
	    JMenu menuFichier = new JMenu("Menu");
	    
	    JMenuItem itemSave = new JMenuItem("Sauvegarder");
	    itemSave.addActionListener(e -> controleur.sauvegarder());
	    
	    

	    menuFichier.add(itemSave);
	    menuBar.add(menuFichier);
	    
	    setJMenuBar(menuBar);
	    this.getJMenuBar().setVisible(true);
	}
    
    
    public void afficherMessageAccueil(String message) {
    	
        if (this.labelMessageErreur != null) {
            this.labelMessageErreur.setText(message);
            this.labelMessageErreur.revalidate();
        }
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
    
    
    public void mettreAJourSlot(JLabel slot, String nomImage) {
    	
        if (this.cacheImages.containsKey(nomImage)) {
            slot.setIcon(this.cacheImages.get(nomImage));
            slot.setText(""); // On enlève le texte "VIDE" pour laisser place à l'image
        } else {
            slot.setIcon(null);
            slot.setText("--- VIDE ---");
        }
        slot.revalidate();
        slot.repaint();
    }
    
    
    public void setObjetUsuel(Item objet, Integer index) {
        
        if (index != null && index >= 0 && index < slotObjet.length) {
            mettreAJourSlot(this.slotObjet[index], objet.getNom());
        }
    }
    
    
    public void setObjetQuete(Item objet) {
    	
    	this.mettreAJourSlot(slotItemQuete, objet.getNom());
    	this.afficherTexte(objet.getLabel());
    }
    
    public Integer premierSlotDispo() {
    	
    	Integer slot = null;
    	for(Integer i=0; i<3; i++) {
    		
    		if(this.slotObjet[i].getIcon() == null) {
    			slot = i;
    			break;
    		}
    	}
    	
    	return slot;
    }
    
    
    public Component[] getBoutons() {
    	
    	return this.zoneBoutonsChoix.getComponents();
    }


	public JProgressBar getBarreVie() {
		
		return barreVie;
	}



	public JLabel[] getSlotObjet() {
		
		return slotObjet;
	}


	public void setControleur(GestionnaireJeu controleur) {
		
        this.controleur = controleur;
    }
}
