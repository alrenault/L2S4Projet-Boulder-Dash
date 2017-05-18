package entite;

import java.util.HashSet;
import java.util.Set;

public class Espace extends Entite {
	
	public Espace() {
		this.apparence = ' ';
		traversable = true;
	}
	
	public Espace(Set<Position> position) {
		this();
		this.position = new HashSet<Position>(position);
	}
	
	public Espace copy(){
		return new Espace(position);
	}
}
