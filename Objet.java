package projetS4;

public abstract class Objet extends Entite{
	protected boolean ramassable;

	public Objet(int posX, int posY) {
		super(posX, posY);
	}
	/*
	public Entite buildEntity(char apparence,
			int posX, int posY){
		return new Objet(true, apparence, posX, posY, ramassable);
	}*/
	
	
}
