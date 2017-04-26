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
	
	public String toString(){
		return "("+x+","+y+")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (direction != other.direction)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
	
}
