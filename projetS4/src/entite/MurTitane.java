package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant un mur en titane
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class MurTitane extends Entite {

	/**
	 * Constructeur de la classe MurTitane
	 */
	public MurTitane() {
		this.apparence = 'W';
		traversable = false;
	}

	public MurTitane(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public MurTitane copy(){
		return new MurTitane(position);
	}
}
