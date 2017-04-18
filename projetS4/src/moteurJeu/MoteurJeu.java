package moteurJeu;


//POSITION DU JOUEUR FAIL !! ESSAYER JUSTE DE LA MODIF

import ia.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Scanner;

import entite.*;
import map.Map;
import affichage.FenetreBoulder;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	public char touche;
	public Thread thread=Thread.currentThread();
	
	//IA
	
	
	public int tabia = 0;
	IA_Random random = new IA_Random();
	char[] directions = random.getDirections();
	
	
	/*public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';*/
	
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
	
	private int intelligence;
	private enum Intelligence {
		NO(-1),
		ME(0),
		RANDOM(1),
		GENETIQUE(2);
		
		private int intelligence;
		
		Intelligence(int j){
			intelligence=j;
		}
		
		public int get(){
			return intelligence;
		}
		
	}
	
	private Entite[][] entite;
	private FenetreBoulder fenetre;
	private int numMap = 1;
	private Map map;
	private String chemin = "src/BD01plus.bd";
	
	private BuildEntity builder = new BuildEntity();
	public Joueur joueur;
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
	
	private Position gagne;
	private int score=0; //score min � avoir pour franchir la porte
	private int nbDiamantRecolte = 0;
	private boolean enJeu;
	
	public MoteurJeu(){
		enJeu=true;
		System.out.println("coucou\n");
		map = new Map(numMap,chemin);
		entite = new Entite[map.getHauteur()][map.getLargeur()];
		
		intelligence=Intelligence.RANDOM.get();
		
		
		
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

		fenetre=new FenetreBoulder(this);
		construireMapEntite();
		
		Iterator<Position> it = exit.getPosition().iterator();
		if(it.hasNext())
			gagne = it.next();
		jeu();
	}
	
	/*public void jeu(char touche){
		System.out.println("Le joueur : "+joueur.getLaPosition().getX()+","+joueur.getLaPosition().getY());
		while(true){
			System.out.println("tour de boucle");
			//recupere la touche et tente un deplacement du joueur
			if(deplacementPossible(touche)){
				joueur.deplacer(touche);
				System.out.println("moteur : posX="+joueur.posX+" posY="+joueur.posY);
			}
			//joueur.deplacer(touche);
			perdu(OBSTACLE_MORTEL);
			joueur.gagne();
			joueur.prendObjets();
			//joueur.sortie();
			//deplace ensuite les rochers ( attention en dessous ! )
			//deplacerRochers(tabRochers);
			perdu(TOUCHER_MORTEL);
			ennemisMorts(TOUCHER_MORTEL);
			//deplace finalement les ennemis
			//deplacerEnnemis(tabEnnemis);
			ennemisMorts(OBSTACLE_MORTEL);
			perdu(TOUCHER_MORTEL);
			//afficher le jeu
			afficherJeu(map);
		}
	}*/
	
	public void affichage(){
	
		if(score>= (map.getDiamondRec()*map.getDiamondVal())){
				afficherPorte();
			}
		System.out.println("IA : "+intelligence);
		
		/*
		try {
			thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		fenetre.repaint();
		
	}
	
	public Position processPosition(){
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = joueur.getPosition().iterator();
		return it.next();
	}
	
	public void processEndOfTurn(){
		//joueur.deplacer(touche);
		tomber(diamant);
		tomber(roc);
		//perdu(OBSTACLE_MORTEL);
		joueur.gagne();
		joueur.prendObjets();
		//joueur.sortie();
		//deplace ensuite les rochers ( attention en dessous ! )
		//deplacerRochers(tabRochers);
		//perdu(TOUCHER_MORTEL);
		ennemisMorts(TOUCHER_MORTEL);
		//deplace finalement les ennemis
		//deplacerEnnemis(tabEnnemis);
		ennemisMorts(OBSTACLE_MORTEL);
		//perdu(TOUCHER_MORTEL);
		//afficher le jeu
		//afficherJeu(map);
		System.out.println(afficherMapEntite());
	}
	
	public void jeu(){
		
		char deplacement = KeyEvent.VK_0 ;
		
		switch(intelligence){
		
		case -1 : //NO --- Rockford reste immobile
			
					while(enJeu){
						affichage();
						
								deplacement = KeyEvent.VK_0;
								
						tour(deplacement,processPosition());
						processEndOfTurn();
					}
					//gameOver();
					
		break; //!NO
		
					
		case 0 : //ME --- Vous pouvez diriger Rockford
			
					while(enJeu){
						affichage();
						
								deplacement = recupererTouche();
								
						tour(deplacement,processPosition());
						processEndOfTurn();
					}
					//gameOver();
					
		break; //!ME
		
		case 1 : //RANDOM --- Joue Rockford al�atoirement
					while(enJeu){
						affichage();
						
								deplacement = directions[tabia]; tabia++;
								if (tabia==1000) intelligence=Intelligence.ME.get();
								
						tour(deplacement,processPosition());
						processEndOfTurn();
					}
					//gameOver();
					
		break; //!RANDOM
		
		}
	}
	
	
	
	
		/*
		//System.out.println("Le joueur : "+joueur.getLaPosition().getX()+","+joueur.getLaPosition().getY());
		Touche t = Touche.MAUVAISE_TOUCHE;

		while(true){
			System.out.println("tour de boucle");
			if(score>= (map.getDiamondRec()*map.getDiamondVal())){
				afficherPorte();
			}
			//recupere la touche et tente un deplacement du joueur
			char touche=recupererTouche();
			System.out.println("lol1 "+joueur.getPosition().size());
			if(joueur.getPosition().size()!=1)
				throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
			Iterator<Position> it = joueur.getPosition().iterator();
			Position p = it.next();
			
			int x = p.getX();
			int y = p.getY();
			switch(touche){
			case KeyEvent.VK_RIGHT: t = Touche.TOUCHE_DROITE; y+=1;break;
			case KeyEvent.VK_LEFT: t = Touche.TOUCHE_GAUCHE; y-=1;break;
			case KeyEvent.VK_UP: t = Touche.TOUCHE_HAUT; x-=1;break;
			case KeyEvent.VK_DOWN: t = Touche.TOUCHE_BAS; x+=1;break;
			case KeyEvent.VK_0 : t = Touche.TOUCHE_IMMOBILE;break;
			}
			if(entite[x][y] == roc){
				pousserRocher(new PositionTombe(x,y));
			}
			else if(deplacementPossible(t)){
				deplacerJoueur(x,y);
				System.out.println("moteur : posX="+x+" posY="+y);
			}
			//joueur.deplacer(touche);
			tomber(diamant);
			tomber(roc);
			//perdu(OBSTACLE_MORTEL);
			joueur.gagne();
			joueur.prendObjets();
			//joueur.sortie();
			//deplace ensuite les rochers ( attention en dessous ! )
			//deplacerRochers(tabRochers);
		//	perdu(TOUCHER_MORTEL);
			ennemisMorts(TOUCHER_MORTEL);
			//deplace finalement les ennemis
			//deplacerEnnemis(tabEnnemis);
			ennemisMorts(OBSTACLE_MORTEL);
		//	perdu(TOUCHER_MORTEL);
			//afficher le jeu
			//afficherJeu(map);
			System.out.println(afficherMapEntite());
		}
	}*/
	
	public void tour(char touche, Position position){
		
		Touche t = Touche.MAUVAISE_TOUCHE;
		
		
		int x = position.getX();
		int y = position.getY();
		
		System.out.println("IA DEPLACEMENT :" +touche+" Tour : " + tabia);
		switch(touche){
			case KeyEvent.VK_RIGHT: t = Touche.TOUCHE_DROITE; y+=1;break;
			case KeyEvent.VK_LEFT: t = Touche.TOUCHE_GAUCHE; y-=1;break;
			case KeyEvent.VK_UP: t = Touche.TOUCHE_HAUT; x-=1;break;
			case KeyEvent.VK_DOWN: t = Touche.TOUCHE_BAS; x+=1;break;
			case KeyEvent.VK_0 : t = Touche.TOUCHE_IMMOBILE;break;
		}
		
		if(entite[x][y] == roc){
			pousserRocher(new PositionTombe(x,y));
		}
		else if (deplacementPossible(t)){
			deplacerJoueur(x,y);
		}
		
		
		/*
		if(entite[x][y] == roc){
			System.out.println("COUCOU");
			int x1 = x;
			int y1 = y;
			switch(touche){
			case KeyEvent.VK_RIGHT: y1+=1;break;
			case KeyEvent.VK_LEFT: y1-=1;break;
			case KeyEvent.VK_UP: x1-=1;break;
			case KeyEvent.VK_DOWN: x1+=1;break;
			default : break;
			}
			if(entite[x][y] == roc){
				pousserRocher(new PositionTombe(x,y));
			}
			else if(deplacementPossible(t)){
				deplacerJoueur(x,y);
				System.out.println("moteur : posX="+x+" posY="+y);
			}
		}*/
		
		
	}
	
	//A TROUVER L'ERREUR
	public boolean pousserRocher(Position p){
		System.out.println("COUCOU");
		int x1 = p.getX();
		int y1 = p.getY();
		switch(touche){
		case KeyEvent.VK_RIGHT: y1+=1;break;
		case KeyEvent.VK_LEFT: y1-=1;break;
		case KeyEvent.VK_UP: return false;
		case KeyEvent.VK_DOWN: return false;
		default : return false;
		}
		/*if(entite[x1+1][y1] == espace)
			return false;*/
		if(entite[x1][y1] == espace){
			PositionTombe p1= new PositionTombe(x1,y1);
			Position p2 = new Position(x1,y1);
			entite[p.getX()][p.getY()] = espace; 
			espace.getPosition().add(p); //rajoute l'emplacement du joueur dans l'ens de pos d'espace
			
			roc.getPositionTombe().remove(p1); //enleve la pos actuelle du joueur
			entite[x1][y1] = roc; //fait pointer sur la nouvelle pos
			entite[x1][y1].getPositionTombe().add(p1); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
			deplacerJoueur(p.getX(),p.getY());
		}
		return true;
	}
	
	public void tomber(Entite e){
		if(!(e instanceof Roc) && !(e instanceof Diamant))
			throw new IllegalArgumentException();
		PositionTombe pos;
		Set<PositionTombe> aTomber = new HashSet<PositionTombe>();
		Iterator<PositionTombe> it = e.getPositionTombe().iterator();
		while(it.hasNext()){
			pos = it.next();
			//if(entite[pos.getX()+1][pos.getY()] == espace || entite[pos.getX()+1][pos.getY()] == joueur )
				aTomber.add(pos);
		}
		
		Iterator<PositionTombe> it1 = aTomber.iterator();
		PositionTombe doitTomber;
		while(it1.hasNext()){
			doitTomber = it1.next();
			if(entite[doitTomber.getX()+1][doitTomber.getY()] == espace){
				entite[doitTomber.getX()][doitTomber.getY()] = espace;
				espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
				
				e.getPositionTombe().remove(doitTomber);
				Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY());
				espace.getPosition().remove(pEspace);
				PositionTombe aAjouter = new PositionTombe(doitTomber.getX()+1,doitTomber.getY());
				aAjouter.setTombe(true);
				e.getPositionTombe().add(aAjouter);
				entite[doitTomber.getX()+1][doitTomber.getY()] = e;
			//	System.out.println("Position tombe si vide : \n"+roc.toStringPosition());
			}
			else{
				if(entite[doitTomber.getX()+1][doitTomber.getY()] == joueur && doitTomber.isTombe()){
					entite[doitTomber.getX()][doitTomber.getY()] = espace;
					espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
					e.getPositionTombe().remove(doitTomber);
					Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY());
					joueur.getPosition().remove(pEspace);
					e.getPositionTombe().add(new PositionTombe(doitTomber.getX()+1,doitTomber.getY()));
					entite[doitTomber.getX()+1][doitTomber.getY()] = e;
					//System.out.println("Position tombe si joueur : \n"+roc.toStringPosition());
					perdu();
				}
				else{
					if(entite[doitTomber.getX()+1][doitTomber.getY()] == murMagique){
						entite[doitTomber.getX()][doitTomber.getY()] = espace;
						espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
						
						e.getPositionTombe().remove(doitTomber);
						Position pAModif = new Position(doitTomber.getX()+1,doitTomber.getY());
						espace.getPosition().remove(pAModif);
						PositionTombe aAjouter = new PositionTombe(doitTomber.getX()+1,doitTomber.getY());
						aAjouter.setTombe(true);
						
						if(e instanceof Roc){
							diamant.getPositionTombe().add(aAjouter);
							entite[doitTomber.getX()+1][doitTomber.getY()] = diamant;
						}
						else{
							roc.getPositionTombe().add(aAjouter);
							entite[doitTomber.getX()+1][doitTomber.getY()] = roc;
						}
					}
					else{
					e.getPositionTombe().remove(doitTomber);
					PositionTombe aModif = new PositionTombe(doitTomber.getX(),doitTomber.getY());
					aModif.setTombe(false);
					e.getPositionTombe().add(aModif);
					//System.out.println("Position tombe pas : \n"+roc.toStringPosition());
					}
				}
			}
			
		}
		/*PositionRoc p;
		Set<PositionRoc> lesPos = new HashSet<PositionRoc>();
		Iterator<PositionRoc> it = roc.getPositionRoc().iterator();
		Position pp;
		while(it.hasNext()){
			pp = it.next();
			lesPos.add(new PositionRoc(pp.getX(),pp.getY()));
		}
		
		Iterator<PositionRoc> it1 = lesPos.iterator();
		while(it1.hasNext()){
			p = it1.next();
			if(entite[p.getX()+1][p.getY()] == espace){

				
			entite[p.getX()][p.getY()] = espace; 
			espace.getPosition().add(p); //rajoute l'emplacement du roc dans l'ens de pos d'espace
			
			PositionRoc p1 = new PositionRoc(p.getX()+1,p.getY());
			roc.getPositionRoc().remove(p); //enl�ve la pos actuelle du roc
			espace.getPosition().remove(p1);
			entite[p.getX()+1][p.getY()] = roc; //fait pointer sur la nouvelle pos
			entite[p.getX()+1][p.getY()].getPositionRoc().add(p1); //rajoute l'emplacement du roc dans l'ens de pos du roc
			p.setTombe(true);
			System.out.println(roc.toStringPosition());
			}
			else if(entite[p.getX()+1][p.getY()] == joueur && p.isTombe()==true){
				entite[p.getX()][p.getY()] = espace; 
				espace.getPosition().add(p); //rajoute l'emplacement du roc dans l'ens de pos d'espace
				
				PositionRoc p1 = new PositionRoc(p.getX()+1,p.getY());
				roc.getPositionRoc().remove(p); //enl�ve la pos actuelle du roc
				entite[p.getX()+1][p.getY()] = roc; //fait pointer sur la nouvelle pos
				entite[p.getX()+1][p.getY()].getPositionRoc().add(p1); //rajoute l'emplacement du roc dans l'ens de pos du roc
				joueur.getPosition().remove(p1);
				perdu();
				System.out.println("COUCOU TOMBE");
			}
			else 
				{
				p.setTombe(false);
				//System.out.println("PROBLEME ???");
				}
		}*/
			
	}
	
	public void construireMapEntite(){
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){
				
				switch(map.getTab(i,j)){
				case ' ': entite[i][j] = espace; break;
				case 'P': entite[i][j] = joueur; break;
				case '.': entite[i][j] = poussiere; break;
				case 'r': entite[i][j] = roc; break;
				case 'd': entite[i][j] = diamant; break;
				case 'w': entite[i][j] = mur; break;
				case 'W': entite[i][j] = murTitane; break;
				case 'X': entite[i][j] = espace; break;
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
				if(entite[i][j] == roc || entite[i][j] == diamant)
					entite[i][j].getPositionTombe().add(new PositionTombe(i,j));
				else
					entite[i][j].getPosition().add(new Position(i,j));
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
	
	public void afficherPorte(){
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){
				if(map.getTab(i, j) == 'X')
					entite[i][j] = exit;
			}
		}
	}
	
	public void changerMap(int n){
		map = new Map(n,chemin);
		joueur.viderPosition();
		espace.viderPosition();
		poussiere.viderPosition();
		roc.viderPositionTombe();
		diamant.viderPositionTombe();
		mur.viderPosition();
		murTitane.viderPosition();
		murMagique.viderPosition();
		exit.viderPosition();
		amibe.viderPosition();
		luciole.viderPosition();
		libellule.viderPosition();
		
		construireMapEntite();
		jeu();
		
	}
	public void deplacerJoueur(int x, int y){
		System.out.println("lollol "+joueur.getPosition().size());
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		if(entite[x][y] == exit){
			gagner();
		}
		if(entite[x][y] == diamant){
			gagnerPoints();
		}
		Iterator<Position> it = joueur.getPosition().iterator();
		Position p = it.next();
		//Position p = joueur.getLaPosition(); //pos actuelle du joueur
		System.out.println("ancienne position : posX="+p.getX()+" posY="+p.getY()+"\nnouvelle position : x="+x+" y="+y);
		//getPosX()   getPosY()
		System.out.println("entite d'avant : "+entite[x][y].getApparence());
		entite[p.getX()][p.getY()] = espace; 
		espace.getPosition().add(p); //rajoute l'emplacement du joueur dans l'ens de pos d'espace
		
		Position p1 = new Position(x,y);
		PositionTombe pT = new PositionTombe(x,y);
		joueur.getPosition().remove(p); //enl�ve la pos actuelle du joueur
		if(entite[x][y] == diamant){
			System.out.println(pT.toString());
			System.out.println("Position diamant :\n"+diamant.toStringPosition());
			entite[x][y].getPositionTombe().remove(pT);
			System.out.println("Position diamant :\n"+diamant.toStringPosition());
		}
		else
			entite[x][y].getPosition().remove(p1);
		entite[x][y] = joueur; //fait pointer sur la nouvelle pos
		entite[x][y].getPosition().add(p1); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
		System.out.println("changement : "+joueur.getPosition().size());
		//System.out.println(joueur.toStringPosition());
		
	}
	
	public boolean deplacementPossible(Touche t1) {
		System.out.println("lol "+joueur.getPosition().size());
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = joueur.getPosition().iterator();
		Position p = it.next();
		//Position p = Joueur.getLaPosition();
		
		switch(t1){
		case TOUCHE_BAS:return caseLibre(p.getX()+1,p.getY());
		case TOUCHE_HAUT:if(entite[p.getX()-1][p.getY()] == diamant){
			Iterator<PositionTombe> it1 = diamant.getPositionTombe().iterator();
			PositionTombe pT;
			while(it1.hasNext()){
				pT = it1.next();
				if((pT.getX() == p.getX()-1) && (pT.getY() == p.getY()) && pT.isTombe()){
					return false;
				} else {
					return true ;
				}
			}
		}
		else
			return caseLibre(p.getX()-1,p.getY());
		case TOUCHE_GAUCHE:return caseLibre(p.getX(),p.getY()-1);
		case TOUCHE_DROITE:return caseLibre(p.getX(),p.getY()+1);
		case TOUCHE_IMMOBILE:return true;
		}
		return false;
	}
	
	public boolean caseLibre(int posX, int posY){
		System.out.println("Emplacement : "+posX+" "+posY+" longueur, largeur :"+entite.length);
		/*//verifie les murs
		if(posX>largeur||posX<0||posY>hauteur||posY<0){
			System.out.println("Case non libre mur");
			return false;
		}*/
		//verifie le contenu de la case
		if(entite[posX][posY].isTraversable()){
			System.out.println("Case libre");
			return true;
		}
		System.out.println("Case non libre");
		return false;
	}
	
	public class ActionClavier implements KeyListener{
		//inutiles
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		//recupère la touche
		public void keyPressed(KeyEvent evt) {
				
		}
	}

	private char recupererTouche() {
		//Thread.currentThread().interrupt();
		synchronized(fenetre.getMoteur().thread){
		try {
			thread.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("Thread reprise");
		return touche;
	}
	
	public void gagnerPoints(){
		if(nbDiamantRecolte > map.getDiamondRec()){
			score += map.getDiamondVal() + map.getDiamondBonus();
		}
		else
			score += map.getDiamondVal();
	}
	
	private void gagner(){
		System.exit(0);
	}

	private void perdu() {
		//System.exit(0);//A MODIF UNE FOIS QU'ON SAURA QUOI FAIRE !!!
		enJeu=false;
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
	
	public Entite[][] getMap(){
		/*Entite[][] mapRetour = new Entite[entite.length][entite[0].length];
		for(int i=0;i<mapRetour.length;i++){
			for(int j=0;j<mapRetour.length;j++){
				mapRetour[i][j]=(Entite) entite[i][j].clone();
			}
		}*/
		return entite;
	}

}
