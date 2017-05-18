package entite;

import java.util.HashSet;
import java.util.Set;

public class Poussiere extends Entite implements Disparaitre {

	public Poussiere() {
		this.apparence = '.';
		traversable = true;
	}
	
	public Poussiere(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Poussiere copy(){
		return new Poussiere(position);
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

}
