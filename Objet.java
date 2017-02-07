package BoulderDash;

public class Objet extends Entite{
	protected boolean ramassable;

	public Objet(boolean traversable, boolean enJeu, char apparence,
			int posX, int posY, boolean ramassable) {
		super(traversable, enJeu, apparence, posX, posY);
		this.ramassable=ramassable;
	}
	
	
}
