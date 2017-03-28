package projetS4.entite;

public abstract class Entite  {
	protected boolean traversable;
	protected boolean enJeu;
	protected char apparence;
	protected int posX;
	protected int posY;
	
	
	//public abstract Entite buildEntity(char display, int posX, int posY);
	/*
	Entite(boolean traversable,boolean enJeu, char apparence,
			int posX, int posY){
		this.enJeu=enJeu;
		this.traversable=traversable;
		this.apparence=apparence;
		this.posX=posX;
		this.posY=posY;
	}
	*/
	
	public Entite(int posX, int posY){
		this.enJeu=true;
		this.posX = posX ;
		this.posY = posY ;
	}
	
	public Entite(){
		
	}
	
	//abstract Entite buildEntity(char apparence, int posX, int posY);
		
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


	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
