package projetS4;

public abstract class Entite {
	protected boolean traversable;
	protected boolean enJeu;
	protected char apparence;
	protected int posX;
	protected int posY;
	
	public Entite(boolean traversable,boolean enJeu, char apparence,
			int posX, int posY){
		this.enJeu=enJeu;
		this.traversable=traversable;
		this.apparence=apparence;
		this.posX=posX;
		this.posY=posY;
	}

	public boolean isTraversable() {
		return traversable;
	}

	public boolean isEnJeu() {
		return enJeu;
	}

	public char getApparence() {
		return apparence;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	
}
