package entite;

public class Libellule extends Entite implements Deplacable, Disparaitre {
	
	public Libellule() {
		this.apparence = 'B';
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
