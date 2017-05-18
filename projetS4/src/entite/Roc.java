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
	
	public Roc(Set<PositionTombe> position) {
		this();
		this.positionTombe = new HashSet<PositionTombe>(positionTombe);
	}
	
	public Roc copy(){
		return new Roc(positionTombe);
	}
	
	public void tomber(){
		
	}

	@Override
	public String toString() {
		return "Roc [positionRoc=" + positionTombe + ", traversable=" + traversable +"]";
	}

}
