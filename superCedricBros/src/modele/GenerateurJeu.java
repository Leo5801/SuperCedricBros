package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class GenerateurJeu{
	
	private Map<String, Vue> catalogueVues = new HashMap<>();
	private Map<String,Lieu> catalogueSalles = new HashMap<>();
	private Map<String,Pnj> cataloguePnj = new HashMap<>();
	private Map<String,Item> catalogueItems = new HashMap<>();
	
	
	public GenerateurJeu() {}
	
	
	public void creerLeMonde() {

		//on charge les maps dans notre cache.
		this.chargerSalleDepuisFichier();
		//fin remplissage map
		
		
		//on remplit les vues
		this.chargerVueDepuisFichier();
		//fin remplissage vues
	
		
		//on relie les 2 côtés des maps 
		this.relierMap(catalogueSalles.get("hallDevant"), catalogueSalles.get("hallDerriere"));
		this.relierMap(catalogueSalles.get("bureauBdeDevant"), catalogueSalles.get("bureauBdeDerriere"));
		this.relierMap(catalogueSalles.get("salleInfoDevant"), catalogueSalles.get("salleInfoDerriere"));
		this.relierMap(catalogueSalles.get("salleMichuDevant"), catalogueSalles.get("salleMichuDerriere"));
		this.relierMap(catalogueSalles.get("salleRemiDevant"), catalogueSalles.get("salleRemiDerriere"));
		//fin des liaisons
		
		
		
		//c'est parti pour remplir les salles voisines, le set ne pose pas problème car les deux côtés auront tout le temps les mêmes voisins
		this.chargerLesVoisinsDepuisFichier();
		//fin remplissage salles voisines
		
		
		//c'est parti pour remplir les actions Changement Map, vu qu'elles ne changent pas on les calcule une fois maintenant et on crée le bouton "se retourner" qui permet, ma foi, de se retourner
		for(String key : catalogueSalles.keySet()) {
			Lieu lieuCourant = getSalle(key);
			
			//pas besoin de if != null car on initialise la liste à 0, jamais null (youpi au moins 2 lignes de code en moins)
			for(Lieu l : lieuCourant.getLieuxVoisins()) {
					lieuCourant.ajouterAction(new ActionChangementMap(l.getLabelBouton(),l));
			}
			
			if(lieuCourant.getLieuOpposé() != null) {
				lieuCourant.ajouterAction(new ActionChangementMap("Se retourner", lieuCourant.getLieuOpposé()));
			}
		}
		//fin remplissage actions changement Map
		
		
		//on place les pnj à leur emplacement de base
		this.placerPnjDepuisFichier();
		//fin placement pnj
		
		
		//on place les items à leur emplacement de base
		this.placerItemDepuisFichier();
		//fin placement item
		
		
		//on remplit les actions dialogue de chaque map
		this.chargerActionsDialoguesDepuisfichier();
		//fin remplissage action dialogue
		
		//on remplit les actions changement de vue de chaque map
		this.chargerEmplacementVueDepuisFichier();
		//fin
	}
	
	
	


	public void chargerSalleDepuisFichier() {
		
		File f = new File("data/salles.txt");
		
		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue; //on l'a pas appris mais le continue est génial ! faut le dire aux prochains L3G miage !
				
				String[] infos = ligne.split("\\s*;\\s*");
				infos[1] = infos[1].replace("\\n", "\n"); // quel filou celui-là alors !
				
				if(infos.length == 4) {
					this.ajouterCatalogueSalles(new Lieu(infos[0], infos[1], infos[2], infos[3]));
				}
				
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void chargerVueDepuisFichier() {
		
		File f = new File("data/vues.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f))); //c'est toujours la même chose mais c'est pratique!
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				
				if(infos.length == 3) {
					this.ajouterCatalogueVues(new Vue(infos[0], infos[1], infos[2]));
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void chargerLesVoisinsDepuisFichier() {
		
		File f = new File("data/voisins.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				String mapCourante = infos[0];
				
				for(int i=1; i<infos.length; i++) {
					this.getSalle(mapCourante).ajouterLieuxVoisons(this.getSalle(infos[i]));
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void placerPnjDepuisFichier() {
		
		File f = new File("data/emplacementPnj.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				
				if(infos.length == 2) {
					this.getSalle(infos[0]).ajouterPnj(this.cataloguePnj.get(infos[1]));
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void placerItemDepuisFichier() {
		
		File f = new File("data/emplacementItem.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				
				if(infos.length == 2) {
					this.getSalle(infos[0]).ajouterObjet(this.catalogueItems.get(infos[1]));
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void chargerActionsDialoguesDepuisfichier() {
		
		File f = new File("data/actionsDialoguesMap.txt");
		
		try {
			ActionDialogue a;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				infos[2] = infos[2].replace("\\n", "\n");
				
				if(infos.length == 3) {
					a = new ActionDialogue(infos[1], infos[2]);
					this.getSalle(infos[0]).ajouterAction(a);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void chargerEmplacementVueDepuisFichier() {
		
		File f = new File("data/emplacementVue.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			String ligne;
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				
				if(infos.length==2) {
					Vue v = this.getVue(infos[1]);
					ActionChangementVue a = new ActionChangementVue(v.getLabelBouton(), v);
					this.getSalle(infos[0]).ajouterAction(a);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void creerLesPersos() {
		
		File f = new File("data/pnj.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String ligne;
			
			while((ligne = br.readLine()) != null) {
				
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				
				if(infos.length>=3) {
					Pnj nouveau = new Pnj(infos[0], infos[1]);
					
					String[] dialogue = new String[infos.length-2];
					
					for(int i=0; i<infos.length-2; i++) {
						dialogue[i] = infos[i+2];
						dialogue[i] = dialogue[i].replace("\\n", "\n");
					}
					nouveau.setDialogue(dialogue);
					this.ajouterCataloguePnj(nouveau);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void creerLesItems() {
		
		File f = new File("data/items.txt");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String ligne;
			
			while((ligne = br.readLine()) != null) {
				if(ligne.trim().isEmpty()) continue;
				
				String[] infos = ligne.split("\\s*;\\s*");
				if(infos.length == 5) {
					Item monObjet = new Item(infos[0], infos[1], infos[2],Integer.parseInt(infos[3].trim()), Boolean.parseBoolean(infos[4].trim()));
					this.ajouterCatalogueItems(monObjet);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	
	public void start() {
		this.creerLesItems();
		this.creerLesPersos();
		this.creerLeMonde();
	}
	
}
	
	
	

