package entite;

import java.util.HashSet;
import java.util.Set;

public class Roc extends Entite implements Deplacable, Disparaitre {

	private boolean tombe = false;
	
	public Roc() {
		this.apparence = 'r';
		traversable = false;
	}
	
	public Roc(Set<PositionTombe> position) {
		this();
		this.positionRoc = new HashSet<PositionTombe>(positionRoc);
	}
	
	public Roc copy(){
		return new Roc(positionRoc);
	}
	
	public void tomber(){
		
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return String texte
	 */
	@Override
	public String toString() {
		return "Roc [positionRoc=" + positionRoc + ", traversable=" + traversable +"]";
	}

	@Override
	public boolean deplacer(Entite[][] carte) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
