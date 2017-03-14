package prototypeProjetS4;

public class Luciole extends Ennemi{
	
	public Luciole(int posX, int posY) {
		super(posX, posY);
		this.apparence=' ';
	}

	public Entite buildEntity(char apparence, int posX, int posY){
		return new Luciole(posX,posY);
	}
	
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}
}
