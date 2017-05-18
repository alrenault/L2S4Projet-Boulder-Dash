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
	
	/**
	 * Constructeur de la copie d'Espace
	 * @param position L'ensemble des positions de l'Espace
	 */
	public Espace(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Copie l'Espace
	 * @return Retourne une copie de l'Espace
	 */
	public Espace copy(){
		return new Espace(position);
	}
}
