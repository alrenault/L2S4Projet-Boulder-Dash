package ia;
import moteurJeu.Touche;

public class IA_Random {
	private Rockford j ;

	public IA_Random() {
		// TODO Auto-generated constructor stub
		j = new Rockford();
	}
	

	public char action(){
		return j.randomTouche();
	}
}
