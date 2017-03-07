package projetS4;

public class Amibe extends Ennemi{
	
	public Amibe(int posX, int posY) {
		super(posX, posY);
		this.apparence=' ';
	}

	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Entite buildEntity (char apparence, int posX, int posY){
		return new Amibe(posX,posY);
		//Amibe j = new Amibe(apparence,posX,posY);
		//return j;
	}
}
