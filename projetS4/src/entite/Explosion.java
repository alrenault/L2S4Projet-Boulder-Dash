package entite;

import java.util.HashSet;
import java.util.Set;

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
	
	/**
	 * Constructeur de la copie de l'Explosion
	 * @param moteur Reference vers le moteur de jeu
	 * @param position L'ensemble des positions de l'explosion
	 */
	public Explosion(MoteurJeu moteur,Set<Position> position) {
		this(moteur);
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree une copie de l'explosion
	 * @return Retourne la copie de l'explosion
	 */
	public Explosion copy(){
		return new Explosion(moteur,position);
	}
	
	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		System.out.println("L'explosion a carbonise le joueur");
		moteur.perdu();
	}

}
