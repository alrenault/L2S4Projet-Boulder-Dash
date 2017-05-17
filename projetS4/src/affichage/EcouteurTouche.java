package affichage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcouteurTouche implements KeyListener{
	
	private FenetreBoulder fenetre;
	
	EcouteurTouche(FenetreBoulder fenetre){
		this.fenetre=fenetre;
	}
		
	/**
	 * Ecoute le clavier et reagit aux entrees si l'etat du jeu est correct
	 * */
	@Override
	public void keyPressed(KeyEvent evt) {
		char touche=(char) evt.getKeyCode();
		System.out.println(touche);
		
		//joue un tour.
		if(!fenetre.getMoteur().enPause()){
			fenetre.getMoteur().touche=touche;
			//attention a ne faire de notify() que si le jeu n'est pas en pause
			synchronized(fenetre.getMoteur().thread) {
				fenetre.getMoteur().thread.notify();
			}
		}
		//fenetre.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
}
