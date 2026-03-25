package sauvegarde;

import java.io.*;
import modele.Partie;

public class GestionnaireSauvegarde {

    
    public static void sauvegarder(Partie partie, String nomJoueur) throws IOException {
        String filename = nomJoueur.toLowerCase() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(partie);
        }
    }

    
    public static Partie charger(String nomJoueur) throws IOException, ClassNotFoundException {
        String filename = nomJoueur.toLowerCase() + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Partie) ois.readObject();
        }
    }

    
    public static boolean existe(String nomJoueur) {
        File f = new File(nomJoueur.toLowerCase() + ".dat");
        return f.exists();
    }
    
    
    public static boolean supprimer(String nomJoueur) {
        
        String nom = nomJoueur.toLowerCase() + ".dat";
        File f = new File(nom);

        if (f.exists()) {
            return f.delete(); // Renvoie true si le fichier est supprimé avec succès
        }
        return false;
    }
}