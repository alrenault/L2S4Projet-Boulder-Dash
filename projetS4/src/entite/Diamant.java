package entite;

public class Diamant extends Entite implements Deplacable, Disparaitre {
	
	private boolean tombe = false;
	
	public Diamant() {
		this.apparence = 'd';
		traversable = true;
	}

	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deplacer(Entite[][] carte) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return String texte
	 */
	@Override
	public String toString() {
		return "Diamant [positionRoc= " + positionRoc + ", traversable=" + traversable + "]";
	}

}
