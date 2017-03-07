package projetS4;

public abstract class Ennemi extends Entite implements Vivant{
	public Ennemi(boolean traversable, boolean enJeu,
			 char apparence, int posX, int posY) {
		super(traversable, enJeu, apparence, posX, posY);
	}
}
