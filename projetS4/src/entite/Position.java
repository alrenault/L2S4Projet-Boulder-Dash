package entite;

import moteurJeu.MoteurJeu.Touche;

public class Position {
	private int x;
	private int y;
	private Touche direction=Touche.TOUCHE_DROITE;
	
	public Position(int abscisse, int ordonnee){
		this.x = abscisse;
		this.y = ordonnee;
	}
	public Position(int abscisse, int ordonnee, Touche direction){
		this.x = abscisse;
		this.y = ordonnee;
		this.direction=direction;
	}
	public Touche getDirection(){
		return direction;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public void setDirection(Touche direction) { 
		this.direction=direction;
	}
	
	
	
}
