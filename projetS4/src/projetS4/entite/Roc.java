package projetS4.entite;

import java.util.Iterator;

public class Roc extends Entite implements Deplacable, Disparaitre {

	public Roc() {
		this.apparence = 'r';
		traversable = true;
	}
	
	public void tomber(){
		
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deplacer(int x,int y) {
		Iterator it=(Iterator) this.position.iterator();
		Position pos=(Position) it.next();
		pos.setX(x);
		pos.setY(y);
		
		return true;
	}

	@Override
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
