package prototypeProjetS4;

public class JoueurHumain extends Joueur{
	
	public JoueurHumain(boolean traversable, boolean enJeu, char apparence, int posX, int posY){
		super(apparence, posX, posY);
	}
	
	public boolean deplacer() {
		/*	switch(touche){
		case  '6':{ 
			
		}
			
		case '4' :{
			
		}
			
		case '8' :{
			
		}
			
		case '2' :{
			
		}
		
		case '5' :{
			
		}
		}*/
		return false;
	}

	/*public boolean deplacementPossible(char touche){
	
	return true;
}*/

	public void gagne() {
		
	}

	public void prendObjets() {
		
	}

	@Override
	Entite buildEntity(char apparence, int posX, int posY) {
		// TODO Auto-generated method stub
		return null;
	}

}