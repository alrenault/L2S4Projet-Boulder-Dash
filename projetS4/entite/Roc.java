package projetS4.entite;

public class Roc extends Entite implements Deplacable, Disparaitre {

	public Roc() {
		this.apparence = 'r';
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