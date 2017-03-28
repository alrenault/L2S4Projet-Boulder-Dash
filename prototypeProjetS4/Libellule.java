package prototypeProjetS4;

public class Libellule extends Ennemi{
	
	public Libellule(int posX, int posY) {
		super(posX, posY);
		this.apparence=' ';
		//true - Cette entite est traversable
		//true - Cette entite est en Jeu
	}
	
	public Entite buildEntity(char apparence, int posX, int posY){
		return new Libellule(posX,posY);
	}
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}
}
