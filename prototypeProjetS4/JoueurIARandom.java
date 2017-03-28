package prototypeProjetS4;

import java.util.*;


public class JoueurIARandom extends Joueur {
		
	public JoueurIARandom(char apparence, int posX, int posY) {
		super(apparence, posX, posY);
	}
	
	public Set<Character> voisins(){
		Set<Character> voisin = new HashSet<>();
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
		Set<Character> v = voisins();
		if(v == null){
			System.out.println("Aucun voisin libre");
			System.exit(0); //TEMPORAIRE !!!
		}
		v.add(TOUCHE_IMMOBILE);
		int var = (int)Math.random()*v.size();
		Iterator<Character> c = v.iterator();
		int i =1;
		Character c1 = c.next();
		while (c.hasNext() && i<var)
		{
			c1 = c.next();
		}
		return (char)c1;
	}

	@Override
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	Entite buildEntity(char apparence, int posX, int posY) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
