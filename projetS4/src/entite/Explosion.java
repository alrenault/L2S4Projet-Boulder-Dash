package entite;

import moteurJeu.MoteurJeu;

/**
 * Classe construisant une explosion
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */

public class Explosion extends Entite implements Ennemi{

	/**
	 * Reference vers le moteur de jeu
	 */
	private MoteurJeu moteur;
	
	/**
	 * Le constructeur de la classe Explosition
	 * @param moteur La reference vers le moteur de jeu.
	 */
	public Explosion(MoteurJeu moteur) {
		this.moteur = moteur;
		this.apparence = 'E';
		traversable = false;
	}
	
	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		System.out.println("L'explosion a carbonise le joueur");
		moteur.perdu();
	}

}
