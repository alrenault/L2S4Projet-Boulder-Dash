package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant un mur basique
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class MurBasique extends Entite {

	/**
	 * Constructeur de la classe MurBasique
	 */
	public MurBasique() {
		this.apparence = 'w';
		traversable = false;
	}

	public MurBasique(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public MurBasique copy(){
		return new MurBasique(position);
	}

}
