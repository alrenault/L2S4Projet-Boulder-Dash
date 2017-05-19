package entite;

import moteurJeu.Touche;

/**
 * Classe construisant une position
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Position {
	
	/**
	 * Coordonnee x de la position
	 */
	private int x;
	
	/**
	 * Coordonnee y de la position
	 */
	private int y;
	
	/**
	 * Direction dans laquelle va l'entite relie a cette position
	 */
	private Touche direction=Touche.TOUCHE_DROITE;
	
	/**
	 * Constructeur de la classe Position pour les entites n'ayant pas besoin de la direction
	 * @param abscisse Coordonnee en x de la position
	 * @param ordonnee Coordonnee en y de la position
	 */
	public Position(int abscisse, int ordonnee){
		this.x = abscisse;
		this.y = ordonnee;
	}
	
	/**
	 * Constructeur de la classe Position pour les entites ayant besoin de la direction
	 * @param abscisse Coordonnee en x de la position
	 * @param ordonnee Coordonnee en y de la position
	 * @param direction Direction dans laquelle se dirige l'entite a cette position
	 */
	public Position(int abscisse, int ordonnee, Touche direction){
		this.x = abscisse;
		this.y = ordonnee;
		this.direction=direction;
	}
	
	/**
	 * Getter de la direction
	 * @return Retourne la direction
	 */
	public Touche getDirection(){
		return direction;
	}
	
	/**
	 * Setter de la direction
	 * @param t La nouvelle direction de l'entite
	 */
	public void setDirection(Touche t){
		direction = t;
	}
	
	/**
	 * Getter de la coordonnee en x de la position
	 * @return Retourne x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter de la coordonnee en y de la position
	 * @return Retourne y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter de la coordonnee en x de la position
	 * @param x La nouvelle coordonnee en x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter de la coordonnee en y de la position
	 * @param y La nouvelle coordonnee en y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString(){
		return "("+x+","+y+")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (direction != other.direction)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
	
}
