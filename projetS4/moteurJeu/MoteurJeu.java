package projetS4.moteurJeu;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
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
	private MurBasique mur;
	private MurTitane murTitane;
	private MurMagique murMagique;
	private Exit exit;
	private Amibe amibe;
	private Luciole luciole;
	private Libellule libellule;
	
	public MoteurJeu(int numMap, String chemin){
		System.out.println("coucou\n");
		map = new Map(numMap,chemin);
		//fenetre=new FenetreBoulder(this);
		
		entite = new Entite[map.getHauteur()][map.getLargeur()];
		
		joueur = (Joueur) builder.buildEntity('P');
		espace = (Espace) builder.buildEntity(' ');
		poussiere = (Poussiere) builder.buildEntity('.');
		roc = (Roc) builder.buildEntity('r');
		diamant = (Diamant) builder.buildEntity('d');
		mur = (MurBasique) builder.buildEntity('w');
		murTitane = (MurTitane) builder.buildEntity('W');
		murMagique = (MurMagique)builder.buildEntity('M');
		exit = (Exit) builder.buildEntity('X');
		amibe = (Amibe) builder.buildEntity('a');
		luciole = (Luciole) builder.buildEntity('F');
		libellule = (Libellule) builder.buildEntity('B');
	}
	/*
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
	}*/
	
	public void construireMapEntite(){
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){
				
				
				
				switch(map.getTab(i,j)){
				case 'P': entite[i][j] = joueur; break;
				case '.': entite[i][j] = poussiere; break;
				case 'r': entite[i][j] = roc; break;
				case 'd': entite[i][j] = diamant; break;
				case 'w': entite[i][j] = mur; break;
				case 'W': entite[i][j] = murTitane; break;
				case 'X': entite[i][j] = exit; break;
				case 'M': entite[i][j] = murMagique; break;
				case 'a': entite[i][j] = amibe; break;
				default:
					if(map.getTab(i,j) == 'F' || map.getTab(i,j) == 'o' || map.getTab(i,j) == 'O' || map.getTab(i,j) == 'q' || map.getTab(i,j) == 'Q')
						{entite[i][j] = luciole; break;}
					if( map.getTab(i,j) == 'b' || map.getTab(i,j) == 'B' || map.getTab(i,j) == 'c' || map.getTab(i,j) == 'C')
						{entite[i][j] = libellule; break; }
					else
						{entite[i][j] = espace; break ; }
				}
			}
		}
	}
	
	public String afficherMapEntite(){ //Temporaire avant l'affichage propre. sert aussi au test pour voir si tout se passe bien
		String s = "";
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0;j<map.getLargeur();j++){
				s+=entite[i][j].getApparence();
			}
			s+="\n";
		}
		return s;
	}
	
	public Position deplacerJoueur(char touche) {
		Position p = Joueur.getPosition();
		
		switch(touche){
		case TOUCHE_BAS:return joueur.getMap().caseLibre(joueur.getPosX()+1,joueur.getPosY());
		case TOUCHE_HAUT:return joueur.getMap().caseLibre(joueur.getPosX()-1,joueur.getPosY());
		case TOUCHE_GAUCHE:return joueur.getMap().caseLibre(joueur.getPosX(),joueur.getPosY()-1);
		case TOUCHE_DROITE:return joueur.getMap().caseLibre(joueur.getPosX(),joueur.getPosY()+1);
		case TOUCHE_IMMOBILE:break;
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
	

}
