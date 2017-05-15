package entite;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;


public abstract class Entite  {
	protected boolean traversable;
	protected char apparence;
	protected  Set<Position> position;
	protected Set<PositionTombe> positionRoc;
	
	
	
	public Entite(){
		if(this instanceof Roc || this instanceof Diamant){
			positionRoc = new HashSet<PositionTombe>();
		}
		else
			position = new HashSet<Position>();
	}
	
	public void viderPosition(){
		position.removeAll(position);
	}
	
	
	
	
	//abstract Entite buildEntity(char apparence, int posX, int posY);
		
	public boolean isTraversable() {
		return traversable;
	}

	public char getApparence() {
		return apparence;
	}


	public Set<Position> getPosition() {
		return position;
	}
	
	public Set<PositionTombe> getPositionTombe(){
		return positionRoc;
	}
	
	public void viderPositionTombe(){
		positionRoc.removeAll(positionRoc);
	}


	public String toStringPosition() {
		String s = "";

		if(this instanceof Roc || this instanceof Diamant){
			Iterator<PositionTombe> it = positionRoc.iterator();
			PositionTombe p;
			while(it.hasNext()){
				p = it.next();
				s+="Position : ("+p.getX()+","+p.getY()+")\n";
			}
			return s;
		}
		else {
			Iterator<Position> it = position.iterator();
			Position p;
			while(it.hasNext()){
				p = it.next();
				s+="Position : "+p.toString()+"\n";
		}
			return s;
	}
	}
	


	



	
	

}
