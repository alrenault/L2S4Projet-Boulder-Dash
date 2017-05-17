package ia;
import moteurJeu.MoteurJeu.Touche;

/**
 * Classe construisant une IA random
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class IA_Random {
	
	private Rockford j ;

	public IA_Random() {
		// TODO Auto-generated constructor stub
		j = new Rockford(10000);
	}
	
	public char[] getDirections(){
		return j.getDirections();
	}

}
