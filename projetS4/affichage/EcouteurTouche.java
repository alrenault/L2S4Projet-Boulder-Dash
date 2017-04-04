package projetS4.affichage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import projetS4.moteurJeu.MoteurJeu;


public class EcouteurTouche implements KeyListener{
	
	private MoteurJeu moteur;
	
	EcouteurTouche(MoteurJeu moteur){
		this.moteur=moteur;
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		char touche=evt.getKeyChar();
		//moteur.jeu(touche);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
}
