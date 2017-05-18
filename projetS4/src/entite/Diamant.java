package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe construisant un diamant
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Diamant extends Entite {
		
	/**
	 * Constructeur de la classe Diamant
	 */
	public Diamant() {
		this.apparence = 'd';
		traversable = true;
	}
	
	public Diamant(Set<PositionTombe> position) {
		this();
		this.positionTombe = new HashSet<PositionTombe>(position);
	}
	
	public Diamant copy(){
		return new Diamant(positionTombe);
	}


	/**
	 * Redefinition de la methode toString() pour Diamant
	 * @return Retourne via une chaine de caractere l'etat du diamant
	 */
	@Override
	public String toString() {
		return "Diamant [positionRoc= " + positionTombe + ", traversable=" + traversable + "]";
	}

}
