package projetS4;
import java.io.File;

public abstract class Entite {
	protected boolean traversable;
	protected boolean enJeu;
	protected char apparence;
	protected int posX;
	protected int posY;
	protected Map map;
	//public abstract Entite buildEntity(char display, int posX, int posY);
	
	public Entite(boolean traversable,boolean enJeu, char apparence,
			int posX, int posY){
		this.enJeu=enJeu;
		this.traversable=traversable;
		this.apparence=apparence;
		this.posX=posX;
		this.posY=posY;
		this.map=new Map(3,new File("BD01plus.bd"));
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
	
	

	public Map getMap() {
		return map;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	
}
