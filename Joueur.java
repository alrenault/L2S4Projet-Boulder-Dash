package projetS4;

public class Joueur extends Entite implements Vivant{
	private int nbreDiamants;
	

	Joueur(boolean traversable, boolean enJeu, char apparence, int posX, int posY) {
		super(traversable, enJeu, apparence, posX, posY);
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
