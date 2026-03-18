package modele;

public class Quetes {
	    private String titre;
	    private String description;
	    private boolean estTerminee;
	    private Item recompense;

	    public Quetes(String titre, String description, Item recompense) {
	        this.titre = titre;
	        this.description = description;
	        this.estTerminee = false;
	        this.recompense = recompense;
	    }

	    public String getNom() {
	        return titre;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public boolean isEstTerminee() {
	        return estTerminee;
	    }

	    public Item getRecompense() {
	        return recompense;
	    }

	    public void setTitre(String titre) {
	        this.titre = titre;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public void terminerQuete() {
	        this.estTerminee = true;
	        System.out.println("Quête '" + titre + "' terminée !");
	        if (recompense != null) {
	            System.out.println("Récompense obtenue : " + recompense.getNom());
	        }
	    }

	    public void lancerQuete() {
	        System.out.println("Quête lancée : " + titre );
	        System.out.println(description);
	    }
	    @Override
	    public String toString() {
	        return "Quete{titre='" + titre + "', estTerminee=" + estTerminee + "}";
	    }
/*Quête d'Eroll (QueteExploration) :
Quête 1 : Trouver le badge sous l'haltère
Quête 2 : Récupérer la casquette d'Eroll chez Mme Michu
Quête 3 : Trouver le code du distributeur dans la poubelle

Quête de Rémi (MiniJeu) :
MiniJeu 1 : Répondre à 3 questions sur Réseau
MiniJeu 2 : Trouver la bonne combinaison de câbles (15 sec)
MiniJeu 3 : Pierre-feuille-ciseaux (3 manches)
MiniJeu 4 : Les jauges (moral, masse, sèche)

Architecture de l'heritage:
Classe fille QueteExploration hérite de Quetes et ajoute itemATrouver. 
Les 3 quêtes d'Eroll :
Q1 : Badge sous l'haltère → récompense : casquette
Q2 : Casquette chez Mme Michu → récompense : code distributeur
Q3 : Code dans la poubelle → récompense : clé informatique

Classe fille MiniJeu hérite de Quetes et ajoute regle.
Les 4 mini-jeux de Rémi :
MJ1 : 3 questions réseau → récompense : accès vidéoprojecteur
MJ2 : Câbles en 15 sec → récompense : accès jauges
MJ3 : Jauges → récompense : bonus PV
MJ4 : Pierre-feuille-ciseaux → récompense : badge de sortie

*/
	    
	}


