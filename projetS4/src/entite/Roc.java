package entite;

import java.util.Set;

public class Roc extends Entite implements Deplacable, Disparaitre {

	private boolean tombe = false;
	
	public Roc() {
		this.apparence = 'r';
		traversable = false;
	}
	
	public void tomber(){
		
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}


	
}
