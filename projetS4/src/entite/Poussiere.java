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

	/**
	 * Constructeur de la copie de la Poussiere
	 * @param position L'ensemble des positions de la Poussiere
	 */
	public Poussiere(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree la copie de la Poussiere
	 * @return Retourne la copie de la Poussiere
	 */
	public Poussiere copy(){
		return new Poussiere(position);
	}


}
