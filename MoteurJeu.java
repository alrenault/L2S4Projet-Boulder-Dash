package projetS4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	
	public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';
	
	Objet[] objets;
	Ennemi[] tabEnnemis;
	Joueur joueur;
	Roc[] tabRochers;
	
	public MoteurJeu(Map map){
		System.out.println("coucou\n");
		joueur=map.placerJoueur();
		//System.out.println("Le joueur : "+joueur.getPosX()+","+joueur.getPosY());
		tabEnnemis=new Ennemi[5];
	}
	
	public void jeu(Map map){
		System.out.println("Le joueur : "+joueur.getPosX()+","+joueur.getPosY());
		while(true){
			System.out.println("tour de boucle");
			//recupere la touche et tente un deplacement du joueur
			char touche=recupererTouche();
			if(deplacementPossible(joueur,touche)){
				joueur.deplacer(touche);
				System.out.println("moteur : posX="+joueur.posX+" posY="+joueur.posY);
			}
			//joueur.deplacer(touche);
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
	
	/*public boolean deplacer() { 
	char direction=recupererTouche();
	if(deplacementPossible(joueur,direction)){
		switch(direction){
		case TOUCHE_BAS:joueur.getMap().deplacerJoueur(joueur,joueur.getPosX(),joueur.getPosY()+1);break;
		case TOUCHE_HAUT:joueur.getMap().deplacerJoueur(joueur,joueur.getPosX(),joueur.getPosY()-1);break;
		case TOUCHE_GAUCHE:joueur.getMap().deplacerJoueur(joueur,joueur.getPosX()+1,joueur.getPosY());break;
		case TOUCHE_DROITE:joueur.getMap().deplacerJoueur(joueur,joueur.getPosX()-1,joueur.getPosY());break;
		case TOUCHE_IMMOBILE:break;
		}
		return true ;
	}
	return false;
}*/

	public boolean deplacementPossible(Joueur joueur, char touche ){
		System.out.println("deplacement possible ? "+touche);
		switch(touche){
		case TOUCHE_BAS:return joueur.getMap().caseLibre(joueur.getPosX()+1,joueur.getPosY());
		case TOUCHE_HAUT:return joueur.getMap().caseLibre(joueur.getPosX()-1,joueur.getPosY());
		case TOUCHE_GAUCHE:return joueur.getMap().caseLibre(joueur.getPosX(),joueur.getPosY()-1);
		case TOUCHE_DROITE:return joueur.getMap().caseLibre(joueur.getPosX(),joueur.getPosY()+1);
		case TOUCHE_IMMOBILE:break;
		}
		return false;
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

private char recupererTouche() {
	char touche='_';
	Scanner sc=new Scanner(System.in);
	do{
		System.out.println("Saisissez une touche");
		if(sc.hasNextLine()){
			touche=sc.nextLine().charAt(0);
		}
	}while(touche!=TOUCHE_BAS&&touche!=TOUCHE_HAUT&&touche!=TOUCHE_DROITE&&
			touche!=TOUCHE_GAUCHE&&touche!=TOUCHE_IMMOBILE);
	//.addKeyListener(new ActionClavier());
	//sc.close();
	return touche;
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
