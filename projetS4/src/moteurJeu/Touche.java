package moteurJeu;

import java.awt.event.KeyEvent;

public enum Touche {
	TOUCHE_DROITE ((char)KeyEvent.VK_RIGHT),
	TOUCHE_GAUCHE ((char)KeyEvent.VK_LEFT),
	TOUCHE_HAUT ((char)KeyEvent.VK_UP),
	TOUCHE_BAS ((char)KeyEvent.VK_DOWN),
	TOUCHE_IMMOBILE ((char)KeyEvent.VK_0),
	MAUVAISE_TOUCHE ('_');

	private char touche;

	Touche(char t){
		touche = t;
	}

	public char toChar(){
		return touche;
	}
}