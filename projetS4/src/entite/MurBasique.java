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

	/**
	 * Constructeur de la copie de la calsse MurBasique
	 * @param position L'ensemble des positions du mur basiqur
	 */
	public MurBasique(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree la copie du mur basique
	 * @return Retourne la copie du mur basique
	 */
	public MurBasique copy(){
		return new MurBasique(position);
	}

}
