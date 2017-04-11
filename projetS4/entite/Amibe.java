package projetS4.entite;

public class Amibe extends Entite implements Deplacable, Disparaitre {
	
	public Amibe() {
		this.apparence = 'a';
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
