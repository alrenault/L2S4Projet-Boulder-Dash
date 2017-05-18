package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant un espace
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */

public class Espace extends Entite {
	
	/**
	 * Constructeur de la classe Espace
	 */
	public Espace() {
		this.apparence = ' ';
		traversable = true;
	}
	
	public Espace(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Espace copy(){
		return new Espace(position);
	}
}
