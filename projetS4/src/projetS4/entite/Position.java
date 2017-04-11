package projetS4.entite;

public class Position {
	private int x;
	private int y;
	
	public Position(int abscisse, int ordonnee){
		this.x = abscisse;
		this.y = ordonnee;
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
	
	/**
	 * Fait la difference entre deux positions.
	 * */
	public Position difference(Position pos){
		return new Position(x-pos.x,y-pos.y);
	}
	
	
}
