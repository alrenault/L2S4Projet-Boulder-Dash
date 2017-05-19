package entite;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Interface montrant si une entite est un ennemi
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public abstract class Entite  {
	
	/**
	 * Booleen representant si l'entite est traversable ou non
	 */
	protected boolean traversable;
	
	/**
	 * Apparence de l'entite sur la map de char
	 */
	protected char apparence;
	
	/**
	 * Ensemble de positions de l'entite representant les differentes pointeurs sur l'entite sur la map d'entite
	 */
	protected  Set<Position> position;
	
	/**
	 * Ensemble de positions de l'entite (si c'est un rocher ou un diamant) 
	 * representant les differentes pointeurs sur l'entite sur la map d'entite
	 */
	protected Set<PositionTombe> positionTombe;
	
	
	/**
	 * Constructeur de la classe entite
	 * Si l'entite est un diamant ou un rocher, on lui construit un ensemble de 
	 * positionTombe pour qu'ils puissent tomber.
	 * Sinon on lui construit un ensemble de position
	 */
	public Entite(){
		if(this instanceof Roc || this instanceof Diamant){
			positionTombe = new HashSet<PositionTombe>();
		}
		else
			position = new HashSet<Position>();
	}
	
	/**
	 * Vide l'ensemble de position
	 * @throws IllegalArgumentException Methode pour des positionTombe pas des positions.
	 * @return Retourne true si la methode a reussi, false sinon
	 */
	public boolean viderPosition(){
		if(this instanceof Diamant || this instanceof Roc){
			throw new IllegalArgumentException("Methode pour des positions pas des positionTombe. Veuillez utiliser viderPositionTombe()");
		}
		return position.removeAll(position);
	}
	
	/**
	 * Vide l'ensemble de positionTombe
	 * @throws IllegalArgumentException Methode pour des positions pas des positionTombe.
	 * @return Retourne true si la methode a reussi, false sinon
	 */
	public boolean viderPositionTombe(){
		if(this instanceof Diamant || this instanceof Roc){
			return positionTombe.removeAll(positionTombe);
		}
		else
			throw new IllegalArgumentException("Methode pour des positionTombe pas des positions. Veuillez utiliser viderPositionTombe()");
		
	}
	
	/**
	 * Getter de traversable		
	 * @return Retourne true si l'entite est traversable, false sinon
	 */
	public boolean isTraversable() {
		return traversable;
	}

	/**
	 * Getter de l'apparence de l'entite
	 * @return Retourne le char representant l'apparence sur la map de char de l'entite
	 */
	public char getApparence() {
		return apparence;
	}


	/**
	 * Getter de l'ensemble de position de l'entite
	 * @throws IllegalArgumentException Methode pour des positions pas des positionTombe.
	 * @return Retourne l'ensemble de position de l'entite
	 */
	public Set<Position> getPosition() {
		if(this instanceof Diamant || this instanceof Roc){
			throw new IllegalArgumentException("Methode pour des positions pas des positionTombe. Veuillez utiliser viderPositionTombe()");
		}
		return position;
	}
	
	/**
	 * Getter de l'ensemble de positionTombe si l'entite est un rocher ou un diamant
	 * @throws IllegalArgumentException Methode pour des positionTombe pas des positions.
	 * @return Retourne l'ensemble de positionTombe de l'entite
	 */
	public Set<PositionTombe> getPositionTombe(){
		if(this instanceof Diamant || this instanceof Roc){
			return positionTombe;
		}
		else
			throw new IllegalArgumentException("Methode pour des positionTombe pas des positions. Veuillez utiliser viderPositionTombe()");
		
	}
	

	/**
	 * Methode premettant de renvoyer l'ensemble des positions sous forme de chaine de caractere
	 * @return Retourne sous la forme d'une chaine de caractere l'ensemble des positions de l'entite
	 */
	public String toStringPosition() {
		String s = "";

		if(this instanceof Roc || this instanceof Diamant){
			Iterator<PositionTombe> it = positionTombe.iterator();
			PositionTombe p;
			while(it.hasNext()){
				p = it.next();
				s+="Position : ("+p.getX()+","+p.getY()+")\n";
			}
			return s;
		}
		else {
			Iterator<Position> it = position.iterator();
			Position p;
			while(it.hasNext()){
				p = it.next();
				s+="Position : "+p.toString()+"\n";
			}
			return s;
		}
	}
	
	/**
	 * Permet la copie de l'entite
	 * @return Retourne la copie de l'entite
	 */
	public abstract Entite copy();
}
