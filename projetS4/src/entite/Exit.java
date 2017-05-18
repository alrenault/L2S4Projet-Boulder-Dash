package entite;

import java.util.HashSet;
import java.util.Set;

public class Exit extends Entite {

	public Exit() {
		this.apparence = 'X';
		traversable = true;
	}

	public Exit(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Exit copy(){
		return new Exit(position);
	}
}
