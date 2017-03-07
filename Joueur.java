package projetS4;

public abstract class Joueur extends Entite implements Vivant{
	private int nbreDiamants;

	Joueur(char apparence, int posX, int posY) {
		super(posX, posY);
		this.traversable = true ;
		this.enJeu = true ;
		this.nbreDiamants=0;
	}

	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean deplacementPossible(char touche){
		return true;
	}

	public void gagne() {
		
	}

	public void prendObjets() {
		
	}
	
}
