package projetS4.moteurJeu;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import projetS4.entite.*;
import projetS4.map.Map;
import projetS4.affichage.FenetreBoulder;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	
	public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';
	
	private Entite[][] entite;
	private FenetreBoulder fenetre;
	private Map map;
	
	private BuildEntity builder = new BuildEntity();
	private Joueur joueur;
	private Espace espace;
	private Poussiere poussiere;
	private Roc roc;
	private Diamant diamant;
	private Mur mur;
	private MurTitane murTitane;
	private MurMagique murMagique;
	private Exit exit;
	private Amibe amibe;
	private Luciole luciole;
	private Libellule libellule;
	
	public MoteurJeu(int numMap, String chemin){
		System.out.println("coucou\n");
		map = new Map(numMap,chemin);
		fenetre=new FenetreBoulder(this);
		
		joueur = builder.buildEntity('P');
		espace = builder.buildEntity(' ');
		poussiere = builder.buildEntity('.');
		roc = builder.buildEntity('r');
		diamant = builder.buildEntity('d');
		mur = builder.buildEntity('w');
		murTitane = builder.buildEntity('W');
		murMagique = builder.buildEntity('M');
		exit = builder.buildEntity('X');
		amibe = builder.builEntity('a');
		luciole = builder.builEntity('F');
		libellule = builder.buildEntity('B');
	}
	
	public void jeu(char touche){
		System.out.println("Le joueur : "+joueur.getPosX()+","+joueur.getPosY());
		while(true){
			System.out.println("tour de boucle");
			//recupere la touche et tente un deplacement du joueur
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
	
	public void construireMapEntite(){
		for(int i=0;i<map.getLargeur();i++){
			for(int j=0; j<map.getHauteur();j++){
				switch(map.getTab(i,j)){
				case 'P':{
					entite[i][j] = joueur;
					map.setTab(i, j, 'R');  //A ENLEVER SI ON VEUT UTILISER APPARENCE DES ENTITES !!
				}	
				case '.': entite[i][j] = poussiere;
				case 'r': entite[i][j] = roc;
				case 'd': entite[i][j] = diamant;
				case 'w': entite[i][j] = mur;
				case 'W': entite[i][j] = murTitane;
				case 'X': entite[i][j] = exit;
				case 'M': entite[i][j] = murMagique;
				case 'a': entite[i][j] = amibe;
				default:
					if(map.getTab(i,j) == 'F' || map.getTab(i,j) == 'o' || map.getTab(i,j) == 'O' || map.getTab(i,j) == 'q' || map.getTab(i,j) == 'Q')
						entite[i][j] = luciole;
					if( map.getTab(i,j) == 'b' || map.getTab(i,j) == 'B' || map.getTab(i,j) == 'c' || map.getTab(i,j) == 'C')
						entite[i][j] = libellule;
					else
						entite[i][j] = espace;
				}
			}
		}
	}
	
	public String afficherMappObjet(){ //Temporaire avant l'affichage propre. sert aussi au test pour voir si tout se passe bien
		String s = "";
		for(int i=0;i<map.getLargeur();i++){
			for(int j=0;i<map.getHauteur();j++){
				s+=entite[i][j].apparence;
			}
			s+="\n";
		}
		return s;
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
	

}
