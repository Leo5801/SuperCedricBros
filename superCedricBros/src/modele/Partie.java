package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Partie implements Serializable {
    
    
    private static final long serialVersionUID = 1L;

    // progession
    private String nomLieuActuel;
    private EtatJeu etatActuel;
    private List<String> objetsRamasses = new ArrayList<>();

    // joueur
    private int pvActuel;
    private int pvMax;
    private boolean[] progressionQuetes;
    
    // inventaire
    // On ne stocke que les NOMS (IDs) des items. 
    // Le Gestionnaire ira les rechercher dans le catalogue au chargement.
    private String[] nomsItemsInventaire;

    public Partie() {
        this.nomsItemsInventaire = new String[3];
    }


    public String getNomLieuActuel() {
    	
    	return nomLieuActuel; 
    }
    
    
    public void setNomLieuActuel(String nomLieuActuel) {
    	
    	this.nomLieuActuel = nomLieuActuel; 
    }

    
    public EtatJeu getEtatActuel() { 
    	
    	return etatActuel; 
    }
    
    
    public void setEtatActuel(EtatJeu etatActuel) { 
    	
    	this.etatActuel = etatActuel; 
    }
    

    public int getPvActuel() { 
    	return pvActuel; 
    }
    
    
    public void setPvActuel(int pvActuel) { 
    	
    	this.pvActuel = pvActuel; 
    }
    

    public int getPvMax() { 
    	
    	return pvMax; 
    }
    
    
    public void setPvMax(int pvMax) { 
    	
    	this.pvMax = pvMax; 
    }
    

    public String[] getNomsItemsInventaire() { 
    	
    	return nomsItemsInventaire; 
    }
    
    
    public void setNomsItemsInventaire(String[] noms) { 
    	
    	this.nomsItemsInventaire = noms; 
    }


	public void setProgressionQuetes(boolean[] quetes) {
		
		this.progressionQuetes = quetes;
	}
	
	
	public boolean[] getProgressionQuetes() {
		
		return this.progressionQuetes;
	}


	public List<String> getObjetsRamasses() {
		return objetsRamasses;
	}


	public void setObjetsRamasses(List<String> objetsRamasses) {
		this.objetsRamasses = objetsRamasses;
	}
	
	
	
}