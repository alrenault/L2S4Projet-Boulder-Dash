package projetS4.entite;

public class Amibe extends Entite implements Deplacable, Disparaitre {
	
	public Amibe() {
		this.apparence = 'a';
		traversable = true;
	}

	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}
	
	
}
