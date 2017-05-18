package entite;

import java.util.HashSet;
import java.util.Set;

import moteurJeu.MoteurJeu;

public class Explosion extends Entite implements Ennemi{

	private MoteurJeu moteur;
	
	/**
	 * Le constructeur de la classe.
	 */
	public Explosion(MoteurJeu moteur) {
		this.moteur = moteur;
		this.apparence = 'E';
		traversable = false;
	}
	
	public Explosion(MoteurJeu moteur,Set<Position> position) {
		this(moteur);
		this.position = new HashSet<Position>(position);
	}
	
	public Explosion copy(){
		return new Explosion(moteur,position);
	}
	
	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		System.out.println("L'explosion a carbonise le joueur");
		//moteur.setEnJeu(false);
		moteur.perdu();
	}

}
