package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant une poussiere
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Poussiere extends Entite {

	/**
	 * Constructeur de la classe Poussiere
	 */
	public Poussiere() {
		this.apparence = '.';
		traversable = true;
	}

	public Poussiere(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Poussiere copy(){
		return new Poussiere(position);
	}


}
