package entite;

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
	

	@Override
	public String toString() {
		return "Roc [positionRoc=" + positionTombe + ", traversable=" + traversable +"]";
	}

}
