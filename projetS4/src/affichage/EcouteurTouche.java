package affichage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import entite.Position;
import moteurJeu.MoteurJeu;
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
	
	//-------------------------Un faux keyPressed qui fonctionnne-------------------------
	
	int posX=1;
	int posY=1;
	
	@Override
	public void keyPressed(KeyEvent evt) {
		char touche=(char) evt.getKeyCode();
		System.out.println(touche);
		
		//Iterator it=(Iterator) fenetre.getMoteur().joueur.getPosition().iterator();
		
		Touche toucheConv=convertirTouche(touche);
		
		if(fenetre.getMoteur().deplacementPossible(toucheConv)){
			switch(touche){
			case KeyEvent.VK_LEFT:posX--;break;
			case KeyEvent.VK_RIGHT:posX++;break;
			case KeyEvent.VK_DOWN:posY++;break;
			case KeyEvent.VK_UP:posY--;break;
			}
			fenetre.getMoteur().deplacerJoueur(posY, posX);
			fenetre.repaint();
			//moteur.jeu(touche);
		}
	}
	
	
	//------------------------------La triste realite------------------------------------
	
	/*@Override
	public void keyPressed(KeyEvent evt) {
		char touche=(char) evt.getKeyCode();
		System.out.println(touche);
		
		Iterator it=(Iterator) fenetre.getMoteur().joueur.getPosition().iterator();
		Position pos=(Position) it.next();
		
		Touche toucheConv=convertirTouche(touche);
		
		if(fenetre.getMoteur().deplacementPossible(toucheConv)){
			switch(touche){
			case KeyEvent.VK_LEFT:pos.setX(pos.getX()-1);break;
			case KeyEvent.VK_RIGHT:pos.setX(pos.getX()+1);break;
			case KeyEvent.VK_DOWN:pos.setY(pos.getY()+1);break;
			case KeyEvent.VK_UP:pos.setY(pos.getY()-1);break;
			}
			fenetre.getMoteur().deplacerJoueur(pos.getY(), pos.getX());
			fenetre.repaint();
			//moteur.jeu(touche);
		}
	}*/

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
}
