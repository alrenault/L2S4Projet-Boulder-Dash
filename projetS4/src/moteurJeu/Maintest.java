package moteurJeu;

import map.Map;

public class Maintest {
	
	public static void main(String[] args) {
		
		
		
		Map m = new Map(1,"src/BD01plus.bd");
		System.out.println(m.toString());
		
		MoteurJeu j = new MoteurJeu(1,"src/BD01plus.bd");
		j.construireMapEntite();
		j.jeu();
		System.out.println(m.toString());
	}
}
