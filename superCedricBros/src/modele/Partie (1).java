package modele;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;

public class Partie {
    private String dateCreation;
    private Compte compte;
    private int etapeQueteCourante;

    public Partie(String dateCreation, Compte compte) {
        this.dateCreation = dateCreation;
        this.compte = compte;
        this.etapeQueteCourante = 0;
    }

    // Sauvegarde la partie dans un fichier propre à chaque joueur
    // Ex : sauvegarde_Cedric.txt
    public void sauvegarder() {
    	 try {
             String nomFichier = "sauvegarde_" + compte.getPseudo() + ".txt";
             BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));
  
             writer.write(dateCreation);
             writer.newLine();
             writer.write(compte.getPseudo());
             writer.newLine();
             writer.write(compte.getMDP());
             writer.newLine();
             writer.write(String.valueOf(etapeQueteCourante));
  
             writer.close();
             System.out.println("Partie sauvegardée dans " + nomFichier);
  
         } catch (Exception e) {
             System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
         }
    }//on ecrit dans le fichier de sauvegarde la date de création, le pseudo, le mot de passe et l'étape de quête courante pour pouvoir reprendre la partie plus tard
       
    
    // Charge la partie depuis le fichier du joueur
    public void charger() {
        try {
            String nomFichier = "sauvegarde_" + compte.getPseudo() + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(nomFichier));

            this.dateCreation = reader.readLine();
            String pseudo = reader.readLine();
            String mdp = reader.readLine();
            this.etapeQueteCourante = Integer.parseInt(reader.readLine());

            reader.close();

            // On vérifie que c'est bien le bon compte
            if (compte.seConnecter(pseudo, mdp)) {
                System.out.println("Reprise de la partie à l'étape : " + etapeQueteCourante);
            } else {
                System.out.println("Ce n'est pas le bon compte !");
                this.etapeQueteCourante = 0;
            }

        } catch (Exception e) {
            System.out.println("Aucune sauvegarde trouvée pour " + compte.getPseudo());
            System.out.println("Nouvelle partie créée.");
            this.etapeQueteCourante = 0;
        }
    }

    // Vérifie si une sauvegarde existe deja pour ce joueur
    public boolean compteExiste() {
        String nomFichier = "sauvegarde_" + compte.getPseudo() + ".txt";
        return new File(nomFichier).exists();//predefini en java
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public Compte getCompte() {
        return compte;
    }

    public int getEtapeQueteCourante() {
        return etapeQueteCourante;
    }

    public void setEtapeQueteCourante(int etapeQueteCourante) {
        this.etapeQueteCourante = etapeQueteCourante;
    }
    //A voir avec JEAN et LEO l'agreation entre Partie et Lieu
    
}