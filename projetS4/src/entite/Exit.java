package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant la sortie
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */

public class Exit extends Entite {

	/**
	 * Constructeur de la classe Exit
	 */
	public Exit() {
		this.apparence = 'X';
		traversable = true;
	}

	public Exit(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Exit copy(){
		return new Exit(position);
	}
}
