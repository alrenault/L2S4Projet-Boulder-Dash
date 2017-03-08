package projetS4;

public class Roc extends Objet{

	public Roc(int posX, int posY) {
		super(posX, posY);
		this.ramassable=false;
		this.traversable=false;
		//false - Cette entite n'est pas traversable
		
	}

	@Override
	Entite buildEntity(char apparence, int posX, int posY) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
