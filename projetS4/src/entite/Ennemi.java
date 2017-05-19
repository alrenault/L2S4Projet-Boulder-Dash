package entite;

/**
 * Interface montrant si une entite est un ennemi
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public interface Ennemi {
	
	/**
	 * Methode simulant le fait que l'ennemi mange le joueur et le fait perdre
	 * @param map Map ou se trouve l'ennemi
	 * @param x Coordonnee en x ou l'ennemi mange le joueur
	 * @param y Coordonnee en y ou l'ennemi mange le joueur
	 */
	public void mangerJoueur(Entite[][] map, int x, int y);
}
