package ia;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

//import moteurJeu.MoteurJeu.Touche;

public class Rockford implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -488567652550232628L;

	Random random;
	
	private int score;
	private char directions[];
	private ArrayList<Character> directionList = new ArrayList<Character>();
	protected char oneDirection[] = new char[]
			{
				KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT,
				KeyEvent.VK_DOWN,
				KeyEvent.VK_UP,
				KeyEvent.VK_0
			};
	

	public Rockford(int n) {
		// TODO Auto-generated constructor stub
		random = new Random();
		
		// On met le score au max pour dire que RockFord n'a pas 
		// réussi à se frayer un chemin vers la sortie
		
		score = n+1;
		
		
		//On charge le tableau de position
		
		directions = new char[n];
		for (int i = 0 ; i < n ; i++){
			directions[i] = randomTouche();
			directionList.add(randomTouche());
		}
		
		
		//score = 2n --- n pas
		//directions
	}
	
	private char randomTouche(){
		
		// Generate un int borné par les 5 choix possibles aléatoirement
		int i = random.nextInt(5);
		
		// Cet int correspond à un des indices du tableau contenant les directions
		return oneDirection[i];
	}

	
	
	
	
	public int getScore() {
		return score;
	}

	public char[] getDirections() {
		return directions;
	}
	
}
