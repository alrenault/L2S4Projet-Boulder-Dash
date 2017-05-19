package ia;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import moteurJeu.MoteurJeu;

//import moteurJeu.MoteurJeu.Touche;

public class RockfordEvolue extends Rockford implements Serializable {

	private MoteurJeu moteur;
	
	public RockfordEvolue(MoteurJeu moteur,int n) {
		super(n);
		this.moteur=moteur;
		// TODO Auto-generated constructor stub
	}
	
	
	/*private char randomTouche(){
		
		// Generate un int borné par les 5 choix possibles aléatoirement
		int i = choisirDirectionIntelligemment();
		
		
		// Cet int correspond à un des indices du tableau contenant les directions
		return super.oneDirection[i];
	}

	private int choisirDirectionIntelligemment() {
		ArrayList<Character> directionsPossibles = new ArrayList<Character>();
		
		for(int i=0;i<oneDirection.length;i++){
			//Position pos=positionDirection();
			//if(){
				
			//}
		}
		
		
		return 0;
	}*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
