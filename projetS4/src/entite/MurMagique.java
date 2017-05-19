package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe centrale de boulder dash creant le moteur de jeu se chargeant de tout coordonner
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class MurMagique extends Entite {

	/**
	 * Constructeur de la classe MurMagique
	 */
	public MurMagique() {
		this.apparence = 'M';
		traversable = false;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructeur de la copie du MurMagique
	 * @param position L'ensemble de position du mur magique
	 */
	public MurMagique(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree la copie du mur magique
	 */
	public MurMagique copy(){
		return new MurMagique(position);
	}
}
