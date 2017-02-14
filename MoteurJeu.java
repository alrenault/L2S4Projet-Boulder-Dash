package projetS4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	Objet[] objets;
	Ennemi[] tabEnnemis;
	Joueur joueur;
	Roc[] tabRochers;
	
	public MoteurJeu(){
		joueur=new Joueur(false,true,'X',5,5);
		tabEnnemis=new Ennemi[5];
	}
	
	public void jeu(Map map){
		System.out.println("execute");
		while(true){
			System.out.println("tour de boucle");
			//recupere la touche et tente un deplacement du joueur
			//char touche=recupererTouche();
			//if(joueur.deplacementPossible(touche)){
			joueur.deplacer();
			//}
			perdu(OBSTACLE_MORTEL);
			joueur.gagne();
			joueur.prendObjets();
			//joueur.sortie();
			//deplace ensuite les rochers ( attention en dessous ! )
			deplacerRochers(tabRochers);
			perdu(TOUCHER_MORTEL);
			ennemisMorts(TOUCHER_MORTEL);
			//deplace finalement les ennemis
			deplacerEnnemis(tabEnnemis);
			ennemisMorts(OBSTACLE_MORTEL);
			perdu(TOUCHER_MORTEL);
			//afficher le jeu
			afficherJeu(map);
		}
	}
	private void perdu(int cause) {
		switch(cause){
		case TOUCHER_MORTEL:break;
		case OBSTACLE_MORTEL:break;
		default:
		}
	}
	private void ennemisMorts(int toucherMortel) {
		// TODO Auto-generated method stub
		
	}
	private void afficherJeu(Map m) {
		System.out.println(m.toString());
	}
	private void deplacerEnnemis(Ennemi[] tabEnnemis2) {
		// TODO Auto-generated method stub
		
	}
	private void deplacerRochers(Roc[] tabRochers2) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
