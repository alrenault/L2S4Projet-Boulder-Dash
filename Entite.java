package BoulderDash;

public abstract class Entite {
	protected boolean traversable;
	protected boolean enJeu;
	protected char apparence;
	protected int posX;
	protected int posY;
	
	Entite(boolean traversable,boolean enJeu, char apparence,
			int posX, int posY){
		this.enJeu=enJeu;
		this.traversable=traversable;
		this.apparence=apparence;
		this.posX=posX;
		this.posY=posY;
	}
}
