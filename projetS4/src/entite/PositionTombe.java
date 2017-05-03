package entite;

public class PositionTombe extends Position{
	
	boolean tombe = false;
	
	public PositionTombe(int x, int y){
		super(x,y);	
	}

	public boolean isTombe() {
		return tombe;
	}

	public void setTombe(boolean tombe) {
		this.tombe = tombe;
	}
	
	public String toString() {
		return super.toString()+ "Tombe : "+ tombe;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (tombe ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionTombe other = (PositionTombe) obj;
		if (tombe != other.tombe)
			return false;
		return true;
	}


	
	
}
