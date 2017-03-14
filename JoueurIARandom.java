package projetS4;

import java.util.ArrayList;
import java.util.List;

public class JoueurIARandom extends Joueur {
		
	public JoueurIARandom(boolean traversable, boolean enJeu, char apparence, int posX, int posY) {
		super(traversable, enJeu, apparence, posX, posY);
	}
	
	public List<Character> voisins(){
		//int[] voisin = new int[5];
		List<Character> voisin = new ArrayList<>();
		if(deplacementPossible(this,TOUCHE_DROITE){
			voisin.add(TOUCHE_DROITE);
		}
		if(deplacementPossible(this,TOUCHE_GAUCHE){
			voisin.add(TOUCHE_GAUCHE);
		}
		if(deplacementPossible(this,TOUCHE_HAUT){
			voisin.add(TOUCHE_HAUT);
		}
		if(deplacementPossible(this,TOUCHE_BAS){
			voisin.add(TOUCHE_BAS);
		}
		if(voisin.isEmpty())
			return null;
		return voisin;
	}
	
	public char voisinRandom(){
		int[] v = voisins();
		if(v == null){
			System.out.println("Aucun voisin libre");
			System.exit(0); //TEMPORAIRE !!!
		}
		v[4] = TOUCHE_IMMOBILE;
		 (int)Math.random()*v.length;
	}
	
}
