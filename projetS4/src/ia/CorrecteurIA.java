package ia;

import java.util.Iterator;

import moteurJeu.MoteurJeu;
import entite.Entite;
import entite.Position;

public class CorrecteurIA {
	
	/**
	 * Le constructeur prive rend la classe ininstanciable
	 * */
	private CorrecteurIA(){}
	
	/*public static char corrigerIA(MoteurJeu moteur){
		System.out.println("______Marie !!!__________");
		
		Iterator it = moteur.joueur.getPosition().iterator();
		Position positionJoueur = (Position) it.next();
		
		if(isMortel(moteur.entite, positionJoueur)){
			char[] cases=testMortel(moteur.entite, positionJoueur);
			return cases[0];
		}else{
			return deplacement;
		}
	}*/

	private static boolean isMortel(char deplacement, Entite[][] entite,
			Position position) {
		Position pos = positionDeplacement(position, deplacement);
		
		/*if(entite[pos.getX()][pos.getY()]){
			;
		}*/
		
		
		return false;
	}

	private static Position positionDeplacement(Position position,
			char deplacement) {
		// TODO Auto-generated method stub
		return null;
	}
}
