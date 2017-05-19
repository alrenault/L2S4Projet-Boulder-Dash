package entite;

/**
 * Classe construisant une position pour les entites tombant (Roc ou Diamant)
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class PositionTombe extends Position{
	
	/**
	 * Booleen montrant si l'entite est en train de tomber ou non
	 */
	boolean tombe = false;
	
	/**
	 * COnstructeur de la classe PositionTombe
	 * @param x Coordonnee en x de la position
	 * @param y Coordonnee en y de la position
	 */
	public PositionTombe(int x, int y){
		super(x,y);	
	}

	/**
	 * Getter de tombe
	 * @return Retourne true si l'entite tombe, false sinon
	 */
	public boolean isTombe() {
		return tombe;
	}

	/**
	 * Setter de tombe
	 * @param tombe La nouvelle valeur de tombe
	 */
	public void setTombe(boolean tombe) {
		this.tombe = tombe;
	}
	
	public String toString() {
		return super.toString()+ "Tombe : "+ tombe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (tombe ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionTombe other = (PositionTombe) obj;
		if (tombe != other.tombe)
			return false;
		return true;
	}


	
	
}
