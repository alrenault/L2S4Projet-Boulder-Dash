package projetS4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Joueur extends Entite implements Vivant{
	private int nbreDiamants;
	public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';

	Joueur(boolean traversable, boolean enJeu, char apparence, int posX, int posY) {
		super(traversable, enJeu, apparence, posX, posY);
		this.nbreDiamants=0;
	}
/*

	public boolean deplacer(char touche) {

		
		return false;
	}

	
	public boolean deplacementPossible(char touche){
		return true;
	}
*/

	public void gagne() {
		
	}

	public void prendObjets() {
		
	}
	private char recupererTouche() {
		char touche='_';
		Scanner sc=new Scanner(System.in);
		do{
			System.out.println("Saisissez une touche");
			touche=sc.nextLine().charAt(0);
		}while(touche!=TOUCHE_BAS&&touche!=TOUCHE_HAUT&&touche!=TOUCHE_DROITE&&
				touche!=TOUCHE_GAUCHE&&touche!=TOUCHE_IMMOBILE);
		//.addKeyListener(new ActionClavier());
		sc.close();
		return touche;
	}
	
	public boolean deplacer() { return true ;/*
		char direction=recupererTouche();
		if(deplacementPossible(direction)){
			switch(direction){
			case TOUCHE_BAS:map.deplacer(this,posX,posY+1);posY++;break;
			case TOUCHE_HAUT:map.deplacer(this,posX,posY-1);posY--;break;
			case TOUCHE_GAUCHE:map.deplacer(this,posX+1,posY);posX++;break;
			case TOUCHE_DROITE:map.deplacer(this,posX-1,posY);posX--;break;
			case TOUCHE_IMMOBILE:break;
			}
		}
		return false;*/
	}
	
	//sert pour addKeyListener()
	public class ActionClavier implements KeyListener{
		//inutiles
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		//recupère la touche
		public void keyPressed(KeyEvent evt) {
				
		}
	}
	
}
