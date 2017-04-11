package projetS4.entite;

public class Libellule extends Entite implements Deplacable, Disparaitre {
	
	public Libellule() {
		this.apparence = 'B';
		traversable = true;
		ennemi=true;
	}
	
	
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean deplacer(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
