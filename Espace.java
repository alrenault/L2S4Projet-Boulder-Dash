package projetS4;

public class Espace extends Entite {
	
	Espace(int posX,int posY) {
		super(posX, posY);
		this.apparence=' ';
		this.traversable = true ;
		this.enJeu = true ;
	}
	public Entite buildEntity(char apparence, int posX, int posY){
		return new Espace(posX,posY);
	}
	
	

}
