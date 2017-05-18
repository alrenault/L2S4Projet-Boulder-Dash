package entite;

import java.util.HashSet;
import java.util.Set;

public class MurTitane extends Entite {

	public MurTitane() {
		this.apparence = 'W';
		traversable = false;
	}

	public MurTitane(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public MurTitane copy(){
		return new MurTitane(position);
	}
}
