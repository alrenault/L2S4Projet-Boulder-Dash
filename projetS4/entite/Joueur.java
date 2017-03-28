package projetS4.entite;

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
	
	public Joueur() {
		
	}
	
	

	public void gagne() {
		
	}

	public void prendObjets() {
		
	}
	
	/*
	public boolean deplacer(char touche) {
		switch(touche){
			case TOUCHE_BAS:map.deplacerJoueur(this,posX+1,posY);return true;
			case TOUCHE_HAUT:map.deplacerJoueur(this,posX-1,posY);return true;
			case TOUCHE_GAUCHE:map.deplacerJoueur(this,posX,posY-1);return true;
			case TOUCHE_DROITE:map.deplacerJoueur(this,posX,posY+1);return true;
			case TOUCHE_IMMOBILE:return true;
		}
		return false;
	}
	*/
	
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