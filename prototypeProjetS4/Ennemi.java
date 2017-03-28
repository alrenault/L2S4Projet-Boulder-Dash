package prototypeProjetS4;

public abstract class Ennemi extends Entite implements Vivant{
	
	public Ennemi(int posX, int posY) {
		super(posX, posY);
		this.traversable = true ;
	}
	public abstract Entite buildEntity(char apparence, int posX, int posY);
}
