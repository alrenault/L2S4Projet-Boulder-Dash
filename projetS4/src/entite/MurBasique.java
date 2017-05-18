package entite;

import java.util.HashSet;
import java.util.Set;

public class MurBasique extends Entite implements Disparaitre {

	public MurBasique() {
		this.apparence = 'w';
		traversable = false;
	}

	public MurBasique(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public MurBasique copy(){
		return new MurBasique(position);
	}
	
	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

}
