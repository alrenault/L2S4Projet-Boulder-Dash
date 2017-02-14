package projetS4;

public abstract class Joueur extends Entite implements Vivant{
	private int nbreDiamants;

	Joueur(boolean traversable, boolean enJeu, char apparence, int posX, int posY) {
		super(traversable, enJeu, apparence, posX, posY);
		this.nbreDiamants=0;
	}

	public boolean deplacer(char touche) {

		
		return false;
	}


	public void gagne() {
		
	}

	public void prendObjets() {
		
	}
	
}
