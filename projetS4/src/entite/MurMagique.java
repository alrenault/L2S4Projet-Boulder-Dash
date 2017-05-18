package entite;

import java.util.HashSet;
import java.util.Set;

public class MurMagique extends Entite implements Disparaitre {

	public MurMagique() {
		this.apparence = 'M';
		traversable = false;
		// TODO Auto-generated constructor stub
	}
	
	public MurMagique(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public MurMagique copy(){
		return new MurMagique(position);
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

}
