package projetS4.entite;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;


public abstract class Entite  {
	protected boolean traversable;
	protected char apparence;
	protected static Set<Position> position;
	
	
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
	
	public Entite(){
		position = new HashSet<Position>();
	}
	
	
	
	
	
	//abstract Entite buildEntity(char apparence, int posX, int posY);
		
	public boolean isTraversable() {
		return traversable;
	}

	public char getApparence() {
		return apparence;
	}





	
	

}
