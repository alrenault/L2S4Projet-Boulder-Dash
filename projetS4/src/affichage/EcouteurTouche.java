package affichage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import entite.Position;
import moteurJeu.MoteurJeu;
import moteurJeu.MoteurJeu.Intelligence;
import moteurJeu.MoteurJeu.Touche;


public class EcouteurTouche implements KeyListener{
	
	private FenetreBoulder fenetre;
	
	EcouteurTouche(FenetreBoulder fenetre){
		this.fenetre=fenetre;
	}
	
	private Touche convertirTouche(char touche){
		switch(touche){
		case KeyEvent.VK_LEFT:return Touche.TOUCHE_GAUCHE;
		case KeyEvent.VK_RIGHT:return Touche.TOUCHE_DROITE;
		case KeyEvent.VK_DOWN:return Touche.TOUCHE_BAS;
		case KeyEvent.VK_UP:return Touche.TOUCHE_HAUT;
		default:return null;
		}
	}
	
	/**
	 * Ecoute le clavier et reagit aux entrees si l'etat du jeu est correct
	 * */
	@Override
	public void keyPressed(KeyEvent evt) {
		char touche=(char) evt.getKeyCode();
		System.out.println(touche);
		
		//if(fenetre.getMoteur().enJeu=true && fenetre.getMoteur().estIA(Intelligence.ME)){
			//joue un tour.
			fenetre.getMoteur().touche=touche;
			synchronized(fenetre.getMoteur().thread) {
				fenetre.getMoteur().thread.notify();
			}
			fenetre.repaint();
		//}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
}
