package projetS4.entite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Scanner;

public class Joueur extends Entite implements Deplacable, Disparaitre {
	
	
	private int nbreDiamants;
	public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';
	
	public Joueur() {
		this.apparence = 'R';
		traversable = true;
	}
	

	public void gagne() {
	
	}

	public void prendObjets() {
		
	}
	
	
	/*public Position deplacer(char touche) {
		Position p = getLaPosition();
		int x = p.getX();
		int y = p.getY();
		
		switch(touche){
			case TOUCHE_BAS:p.setX(x+1);p.setY(y);return p;
			case TOUCHE_HAUT:p.setX(x-1);p.setY(y);return p;
			case TOUCHE_GAUCHE:p.setX(x);p.setY(y-1);return p;
			case TOUCHE_DROITE:p.setX(x);p.setY(y+1);return p;
			case TOUCHE_IMMOBILE:return p;
		}
		return p;
	}*/
	
	
	//sert pour addKeyListener()
	public class ActionClavier implements KeyListener{
		//inutiles
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		//recupère la touche
		public void keyPressed(KeyEvent evt) {
				
		}
	}

	@Override
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}
	

	/*public Position getLaPosition() {
		System.out.println("lol "+position.size());
		if(position.size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = position.iterator();
		Position p = it.next();
		
		return p;
	}*/







	
	
	
}