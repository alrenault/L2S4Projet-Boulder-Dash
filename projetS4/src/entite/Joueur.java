package entite;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe creant un joueur 
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Joueur extends Entite{
	
	private int nbreDiamants;
	/**
	 * Constructeur de la classe Joueur
	 */
	public Joueur() {
		this.apparence = 'R';
		traversable = true;
	}
	
	/**
	 * Constructeur de la copie du Joueur
	 * @param position La position du joueur
	 * @param nbreDiamants Le nombre de diamant qu'il a recupere
	 */
	public Joueur(Set<Position> position, int nbreDiamants) {
		this();
		this.position = new HashSet<Position>(position);
		this.nbreDiamants = nbreDiamants;
	}
	
	/**
	 * Cree la copie du joueur
	 * @return Retourne la copie du joueur
	 */
	public Joueur copy(){
		return new Joueur(position, nbreDiamants);
	}

	public String toString(){
		return "Joueur : nbreDiamants : "+nbreDiamants+" "+toStringPosition();
	}	
}
