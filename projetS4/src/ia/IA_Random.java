package ia;

/**
 * Classe creant une IA aleatoire
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class IA_Random {
	
	/**
	 * Le Rockford utilisant l'IA
	 */
	private Rockford j ;

	/**
	 * Constructeur de la classe IA_Random
	 */
	public IA_Random() {
		// TODO Auto-generated constructor stub
		j = new Rockford();
	}
	
	/**
	 * Renvoie une touche aleatoire
	 * @return Retourne une touche aleatoire
	 */
	public char action(){
		return j.randomTouche();
	}
}
