package ia;
import moteurJeu.MoteurJeu.Touche;

public class IA_Random {
	private Rockford j ;

	public IA_Random() {
		// TODO Auto-generated constructor stub
		j = new Rockford(10000);
	}
	
	public char[] getDirections(){
		return j.getDirections();
	}

}
