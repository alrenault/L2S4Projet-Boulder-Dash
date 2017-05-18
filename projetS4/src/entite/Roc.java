package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant un rocher
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Roc extends Entite {

	/**
	 * Constructeur de la classe Roc
	 */
	public Roc() {
		this.apparence = 'r';
		traversable = false;
	}
	
	/**
	 * Constructeur de la copie de Roc
	 * @param position L'ensemble de position du Roc
	 */
	public Roc(Set<PositionTombe> position) {
		this();
		this.positionTombe = new HashSet<PositionTombe>(positionTombe);
	}
	
	/**
	 * Cree la copie du Roc
	 * @return Retourne la copie de Roc
	 */
	public Roc copy(){
		return new Roc(positionTombe);
	}

	@Override
	public String toString() {
		return "Roc [positionRoc=" + positionTombe + ", traversable=" + traversable +"]";
	}

}
