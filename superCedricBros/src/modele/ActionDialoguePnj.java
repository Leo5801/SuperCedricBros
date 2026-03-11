package modele;

public class ActionDialoguePnj extends ActionDialogue {

	private String leNomDuPerso;
	
	
	public ActionDialoguePnj(String label, String texte, String leNomDuPerso) {
		
		super(label,texte);
		this.leNomDuPerso = leNomDuPerso;
	}
	
	
	public String getLeNomDuPerso() {
		
		return this.leNomDuPerso;
	}
}
