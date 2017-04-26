package moteurJeu;


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
	
	IA_Directive direct = new IA_Directive();
	char[] directionsDirect = direct.getDirections();
	
	private int intelligence;
	
	//reste
	
	private Entite[][] entite;
	private FenetreBoulder fenetre;
	private int numMap = 1;
	private Map map;
	private String chemin = "src/BD01plus.bd";
	
	private BuildEntity builder = new BuildEntity();
	public Joueur joueur;
	public static Espace espace;
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
	private int score=0; //score min a avoir pour franchir la porte
	private int nbDiamantRecolte = 0;
	private int nbTour = 0;
	public boolean enJeu;
	
	/**
	 * Designe les differents types de touches.
	 * */
	
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
	
	/**
	 * Designe les differents types d'IA.
	 * */
	public enum Intelligence {
		NO(-1),
		ME(0),
		RANDOM(1),
		GENETIQUE(2),
		DIRECTIVE(3);
		
		private int intelligence;
		
		Intelligence(int j){
			intelligence=j;
		}
		
		public int get(){
			return intelligence;
		}
	}
	
	/**
	 * Permet a la barre de menu de changer le mode d'IA sans pour autant donner acces
	 * a la variable.
	 * @param Intelligence ia
	 * */
	public void changerIA(Intelligence ia){
		this.intelligence=ia.get();
	}
	
	public MoteurJeu(){
		enJeu=true;
		System.out.println("coucou\n");
		map = new Map(numMap,chemin);
		entite = new Entite[map.getHauteur()][map.getLargeur()];
		
		//CHOIX DE L'IA AU DEBUT DU JEU
		intelligence=Intelligence.ME.get();
		
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
	
	
	public void affichage(){
		
		//System.out.println(System.getProperty("user.dir"));
	
		if(score>= (map.getDiamondRec()*map.getDiamondVal())){
				afficherPorte();
			}
		System.out.println("IA : "+intelligence);
		
		
		try {
			thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		fenetre.repaint();
		
	}
	
	public Position processPosition(){
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = joueur.getPosition().iterator();
		return it.next();
	}
	
	/**
	 * Les tests de victoire / defaite qui operent en fin de tour.
	 * */
	public void processEndOfTurn(){
		deplacerEnnemis();
		tomber(diamant);
		tomber(roc);
		//perdu(OBSTACLE_MORTEL);
		joueur.gagne();
		joueur.prendObjets();
		//joueur.sortie();
		//deplace ensuite les rochers ( attention en dessous ! )
		//deplacerRochers(tabRochers);
		//perdu(TOUCHER_MORTEL);
		//ennemisMorts(TOUCHER_MORTEL);
		//deplace finalement les ennemis
		//ennemisMorts(OBSTACLE_MORTEL);
		//perdu(TOUCHER_MORTEL);
		//afficher le jeu
		//afficherJeu(map);
		System.out.println(afficherMapEntite());
	}
	
	/**
	 * Force le deplacement du joueur dans la fenetre si l'IA est differente de ME. 
	 * */
	public void jeu(){
		
		char deplacement = KeyEvent.VK_0 ;
		
		while(enJeu){
			switch(intelligence){
			case -1 : //NO --- Rockford reste immobile
				affichage();	
				deplacement = KeyEvent.VK_0;
				
				tour(deplacement,processPosition());
				processEndOfTurn();
				//gameOver();	
				
			break; //!NO
			
			case 0 : //ME --- Vous pouvez diriger Rockford
				affichage();		
				deplacement = recupererTouche();
				
				tour(deplacement,processPosition());
				processEndOfTurn();
				//gameOver();
					
			break; //!ME
		
			case 1 : //RANDOM --- Joue Rockford al�atoirement
				affichage();
				deplacement = directions[tabia]; tabia++;
				
				if (tabia==1000) intelligence=Intelligence.ME.get();
				tour(deplacement,processPosition());
				processEndOfTurn();
				//gameOver();
					
			break; //!RANDOM
		
			case 2 : break;
		
			case 3 :
				affichage();
				deplacement = directions[tabia]; tabia++;
				if(tabia==1000) intelligence = Intelligence.ME.get();
			
			break;
			}
		}
	}
	
	
	
	public void tour(char touche, Position position){
		
		nbTour++;
		
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
		if(entite[x1][y1] == espace){
			PositionTombe p1= new PositionTombe(x1,y1);
			entite[p.getX()][p.getY()] = espace; 
			espace.getPosition().add(p); //rajoute l'emplacement du joueur dans l'ens de pos d'espace
			
			roc.getPositionTombe().remove(p1); //enleve la pos actuelle du joueur
			entite[x1][y1] = roc; //fait pointer sur la nouvelle pos
			entite[x1][y1].getPositionTombe().add(p1); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
			deplacerJoueur(p.getX(),p.getY());
		}
		return true;
	}
	
	/**
	 * Fait tomber le rocher 
	 * */
	public void tomber(Entite e){
		
		
		if(!(e instanceof Roc) && !(e instanceof Diamant))
			throw new IllegalArgumentException();
		
		
		PositionTombe pos;
		Set<PositionTombe> aTomber = new HashSet<PositionTombe>();
		Iterator<PositionTombe> it = e.getPositionTombe().iterator();
		
		while(it.hasNext()){
			pos = it.next();
				aTomber.add(pos);
		}
		
		
		Iterator<PositionTombe> it1 = aTomber.iterator();
		PositionTombe doitTomber;
		
		while(it1.hasNext()){
			doitTomber = it1.next();
			
			//si c'est un espace, tombe
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
				
			}else{
				Entite surLaCase=entite[doitTomber.getX()+1][doitTomber.getY()];
				
				//si c'est un joueur ou une luciole, tue-les.
				if((surLaCase == joueur || 
						surLaCase == luciole) 
						&& doitTomber.isTombe()){

					entite[doitTomber.getX()][doitTomber.getY()] = espace;
					espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
					e.getPositionTombe().remove(doitTomber);
					Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY());
					//change de joueur.getPosition().remove(pEspace)
					
					if(surLaCase.getPosition().remove(pEspace) == false){ //Oblige de faire cela car pour les lucioles renvoie toujours false mais fonctionne pour le joueur
						Iterator<Position> it3 = surLaCase.getPosition().iterator();
						Position pTest = null;
						
						while(it3.hasNext()){
							 pTest = it3.next();
							if(pTest.getX() ==  doitTomber.getX()+1 && pTest.getY() == doitTomber.getY()){
								break;
							}
						}
						surLaCase.getPosition().remove(pTest);
						
					}
					e.getPositionTombe().add(new PositionTombe(doitTomber.getX()+1,doitTomber.getY()));
					entite[doitTomber.getX()+1][doitTomber.getY()] = e;
					
					//si l'entite ecrasee etait le joueur, fais-le perdre
					if(surLaCase == joueur){
						perdu();
					}
					
				}else{
					//si c'est un mur magique, transforme le rocher en diamant
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
					}else{
						//Arrete de tomber
						e.getPositionTombe().remove(doitTomber);
						PositionTombe aModif = new PositionTombe(doitTomber.getX(),doitTomber.getY());
						aModif.setTombe(false);
						e.getPositionTombe().add(aModif);
					}
				}
			}
			
		}
					
	}
	
	/**
	 * Construit la carte du jeu avec tout ses objets a partir d' un tableau a 2 dimension
	 * obtenu en lisant le fichier contenant tout les niveaux.
	 * */
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
	
	public void resetMap(){
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
		
		score=0; 
		nbDiamantRecolte = 0;
		nbTour = 0;
		
		construireMapEntite();
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
	 * Ajoute la porte au jeu si le nombre de diamants est suffisant.
	 * */
	public void afficherPorte(){
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){
				if(map.getTab(i, j) == 'X')
					entite[i][j] = exit;
			}
		}
	}
	
	/**
	 * Fait changer de carte et relance une nouvelle partie.
	 * */
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
		
		score=0; 
		nbDiamantRecolte = 0;
		nbTour = 0;
		
		construireMapEntite();
		//jeu();
	}
	
	/**
	 * Deplace le joueur sur la case de coordonnees (x, y)
	 * @param int x, int y
	 * */
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
		Position p = it.next(); //pos actuelle du joueur
		entite[p.getX()][p.getY()] = espace; 
		espace.getPosition().add(p); //rajoute l'emplacement du joueur dans l'ens de pos d'espace
		
		Position p1 = new Position(x,y);
		PositionTombe pT = new PositionTombe(x,y);
		joueur.getPosition().remove(p); //enleve la pos actuelle du joueur
		if(entite[x][y] == diamant){
			diamant.getPositionTombe().remove(pT);
		}
		else
			entite[x][y].getPosition().remove(p1);
		entite[x][y] = joueur; //fait pointer sur la nouvelle pos
		entite[x][y].getPosition().add(p1); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
		
	}
	
	/**
	 * Teste si le deplacement est possible ou non.
	 * @return true si le deplacement est possible et false sinon.
	 * */
	public boolean deplacementPossible(Touche t1) {
		//System.out.println("lol "+joueur.getPosition().size());
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = joueur.getPosition().iterator();
		Position p = it.next();
		
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
	
	/**
	 * Verifie si la case (posX, posY) est libre ( si elle est traversable )
	 * @return true si la case est libre et false sinon.
	 * */
	public boolean caseLibre(int posX, int posY){
		//System.out.println("Emplacement : "+posX+" "+posY+" longueur, largeur :"+entite.length);

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
	
	public int getNombreDiamants(){
		return nbDiamantRecolte;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getNombreTour(){
		return nbTour;
	}
	
	
	public void gagnerPoints(){
		nbDiamantRecolte++;
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
	private void deplacerEnnemis(){
		luciole.deplacer(entite);
		libellule.deplacer(entite);
		//amibe.deplacer(entite);
	}
	private void deplacerRochers(Roc[] tabRochers2) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean estIA(Intelligence ia){
		return ia.get() == intelligence;
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