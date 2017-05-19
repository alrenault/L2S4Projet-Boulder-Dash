package affichage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe permettant de prendre les touches entrees au clavier
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class EcouteurTouche implements KeyListener{
	
	/**
	 * Reference vers la fenetre graphique
	 */
	private FenetreBoulder fenetre;
	
	/**
	 * Constructeur de la classe EcouteurTouche
	 * @param fenetre Reference vers la fenetre graphique
	 */
	EcouteurTouche(FenetreBoulder fenetre){
		this.fenetre=fenetre;
	}
		
	/**
	 * Ecoute le clavier et reagit aux entrees si l'etat du jeu est correct
	 * @param evt La touche saisie
	 * */
	@Override
	public void keyPressed(KeyEvent evt) {
		char touche=(char) evt.getKeyCode();
		//System.out.println(touche);
		
		//joue un tour.
		if(!fenetre.getMoteur().enPause()){
			fenetre.getMoteur().touche=touche;
			//attention a ne faire de notify() que si le jeu n'est pas en pause
			synchronized(fenetre.getMoteur().thread) {
				fenetre.getMoteur().thread.notify();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
}
