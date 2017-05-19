package entite;

/**
 * Interface montrant qu'une entite peut se deplacer
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public interface Deplacable {
	/**
	 * Methode de deplacement pour les entites
	 * @param carte La map d'entite ou se trouve l'entite a deplacer
	 * @return Retourne true si l'entite s'est deplacee, false sinon
	 */
	boolean deplacer(Entite[][] carte);
}
