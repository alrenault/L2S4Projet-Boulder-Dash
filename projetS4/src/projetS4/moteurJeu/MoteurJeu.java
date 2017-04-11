package projetS4.moteurJeu;


//POSITION DU JOUEUR FAIL !! ESSAYER JUSTE DE LA MODIF

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
	public boolean enVie=true;
	
	/*public static final char TOUCHE_DROITE='6';
	public static final char TOUCHE_GAUCHE='4';
	public static final char TOUCHE_HAUT='8';
	public static final char TOUCHE_BAS='2';
	public static final char TOUCHE_IMMOBILE='5';*/
	
	public enum Touche {
		TOUCHE_DROITE ('6'),
		TOUCHE_GAUCHE ('4'),
		TOUCHE_HAUT ('8'),
		TOUCHE_BAS ('2'),
		TOUCHE_IMMOBILE ('5'),
		MAUVAISE_TOUCHE ('_');
		
		private char touche;
		
		Touche(char t){
			touche = t;
		}
		
		public char toChar(){
			return touche;
		}
		
	}
	
	private Entite[][] entite;
	private FenetreBoulder fenetre;
	private Map map;
	
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
	
	public MoteurJeu(int numMap, String chemin){
		System.out.println("coucou\n");
		map = new Map(numMap,chemin);
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

		fenetre=new FenetreBoulder(this);
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
	
	public void jeu(char touche){
		//System.out.println("Le joueur : "+joueur.getLaPosition().getX()+","+joueur.getLaPosition().getY());
		Touche t = Touche.MAUVAISE_TOUCHE;
		if(enVie){
			System.out.println("tour de boucle");
			//recupere la touche et tente un deplacement du joueur
			//char touche=recupererTouche();
			System.out.println("enVie : Taille de position : "+joueur.getPosition().size());
			if(joueur.getPosition().size()!=1){
				throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
			}
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
			//teste si le deplacement du joueur est possible sur cette case.
			//(s'il n'y a pas de monstre, mur, rocher ou autre.)
			if(deplacementPossible(t)){
				enVie=deplacerJoueur(x,y);
				System.out.println("moteur : posX="+x+" posY="+y+" enVie="+enVie);
			}
			
			//verifie si le joueur est toujours en vie apres son deplacement
			//( s'il n' a pas heurte un monstre.)
			if(enVie){
				joueur.gagne();
				//joueur.prendObjets();
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
		}
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
	
	/**
	 * Deplace le joueur. Verifie aussi que le deplacement de ce dernier ne soit pas MORTEL.
	 * Auquel cas, false est renvoyee. Sinon, true est renvoyee.
	 * */
	public boolean deplacerJoueur(int x, int y){
		System.out.println("deplacerJoueur: nbreDePositions : "+joueur.getPosition().size());
		if(joueur.getPosition().size()!=1){
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		}
		
		Iterator<Position> it = joueur.getPosition().iterator();
		Position p = it.next();//position actuelle du joueur
		System.out.println("deplacerJoueur : map : posX="+p.getX()+" posY="+p.getY()+" \n   x="+x+" y="+y);
		
		//verifie si la case devant contient un rocher. 
		//Dans ce cas, deplace-le a la case d'a cote.
		if(entite[x][y].getClass().equals(roc.getClass())){
			System.out.println("Deplace le rocher");
			int xDiff=p.getX()-x;
			int yDiff=p.getY()-y;
			System.out.println("Le rocher se deplace de : x="+xDiff+" y="+yDiff);
			Roc rocher=(Roc) entite[x][y];
			/*Iterator<Position> iter=rocher.getPosition().iterator();
			Position pos=iter.next();
			pos.setX(x+xDiff);
			pos.setY(y+yDiff);*/
			rocher.deplacer(x+xDiff,y+yDiff);
		}
		
		//fait disparaitre le joueur
		entite[p.getX()][p.getY()] = espace;
		espace.getPosition().add(p); //rajoute l'emplacement du joueur dans l'ens de pos d'espace
		joueur.getPosition().remove(p); //enleve la pos actuelle du joueur
		
		//fait mourir le joueur en ne le faisant pas reaparaitre s'il heurte un monstre.
		if(entite[x][y].isMonstre()){
			System.out.println("monstre tue Robil");
			return false;
		}else if(entite[x][y].getClass().equals(exit.getClass())){
			System.out.println("_________________\nVictoire !\n______________");
			return true;
		}
		entite[x][y] = joueur; //fait pointer sur la nouvelle pos
		entite[x][y].getPosition().add(new Position(x,y)); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
		System.out.println("changement : "+joueur.getPosition().size());
		
		
		return true;
	}
	
	public boolean deplacementPossible(Touche t1) {
		System.out.println("moteur : lol : joueur position : "+joueur.getPosition().size());
		if(joueur.getPosition().size()!=1){
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		}
		Iterator<Position> it = joueur.getPosition().iterator();
		Position p = it.next();
		//Position p = Joueur.getLaPosition();
		
		switch(t1){
		case TOUCHE_BAS:return caseLibre(p.getX()+1,p.getY());
		case TOUCHE_HAUT:return caseLibre(p.getX()-1,p.getY());
		case TOUCHE_GAUCHE:return caseLibre(p.getX(),p.getY()-1);
		case TOUCHE_DROITE:return caseLibre(p.getX(),p.getY()+1);
		case TOUCHE_IMMOBILE:return true;
		default:return false;
		}
	}
	
	public boolean caseLibre(int posX, int posY){
		System.out.println("\nMoteur : Emplacement : "+posX+" "+posY+" longueur, largeur :"+entite.length);
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
	
	/*public class ActionClavier implements KeyListener{
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
			if(sc.hasNextInt()){	//Faire un test supplémentaire : si espace est appuyé avec chaine vide -> Exception RunTime
				touche=sc.nextLine().charAt(0);
			}
		}while(touche!=Touche.TOUCHE_BAS.toChar()&&touche!=Touche.TOUCHE_HAUT.toChar()&&touche!=Touche.TOUCHE_DROITE.toChar()&&
				touche!=Touche.TOUCHE_GAUCHE.toChar()&&touche!=Touche.TOUCHE_IMMOBILE.toChar());
		//.addKeyListener(new ActionClavier());
		//sc.close();
		return touche;
	}

	/*private boolean perdu(int cause) {
		switch(cause){
		case TOUCHER_MORTEL:
			System.out.println("-------------\nPerdu par toucher mortel !\n----------");
			return true;
		case OBSTACLE_MORTEL:
			System.out.println("-------------\nPerdu par toucher mortel !\n----------");
			return ennemiDevant();
		default:return false;
		}
	}
	private boolean ennemiDevant(){
		Iterator it=joueur.getPosition().iterator();
		Position pos=(Position) it.next();
		if(entite[pos.getX()][pos.getY()].equals(luciole)){
			
		}
		return false;
	}*/
	
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
