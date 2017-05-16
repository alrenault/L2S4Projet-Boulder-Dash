package entite;

/**
 * Classe construisant un diamant
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Diamant extends Entite {
	
	/**
	 * Booleen representant si le diamant tombe ou non
	 */
	private boolean tombe = false;
	
	/**
	 * Constructeur de la classe Diamant
	 */
	public Diamant() {
		this.apparence = 'd';
		traversable = true;
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
