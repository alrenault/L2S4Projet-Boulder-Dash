package moteurJeu;
//quand on meurt, probleme avec les libellule et amibe qui font encore un mouvement et ne sont donc pas reset comme il faut

import ia.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

import entite.*;
import map.Map;
import affichage.FenetreBoulder;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	public char touche;
	public Thread thread=Thread.currentThread();
	private boolean enPause =false;
	public static boolean MODE_DEBUG_TOMBER=false;
	public static boolean MODE_DEBUG_LIBELLULE=false;
	public static boolean MODE_DEBUG_LUCIOLE=false;

	//IA

	public int tabia = 0;
	IA_Random random = new IA_Random();
	char[] directions = random.getDirections();
	private IA_Directive directive;
	private List<Character> chemin_direct;

	private int intelligence;

	//reste

	private Entite[][] entite;
	private FenetreBoulder fenetre;
	private int numMap;
	private Map map;
	private String chemin;
	private String nomFichier;

	private BuildEntity builder = new BuildEntity();
	public Joueur joueur;
	private static Espace espace;
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
	private Explosion explosion;

	//private Position gagne;
	private int score=0; //score min a avoir pour franchir la porte
	private int nbDiamantRecolte = 0;
	private int nbTour = 0;
	
	private boolean enJeu;
	private boolean aGagne;
	private boolean aPerdu;
	private boolean porteAffiche = false;
	private Position posPorte;

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
		DIRECTIVE(3),
		REJOUE(4);

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

	public MoteurJeu(int numMap, String fichier){
		enJeu=true;

		this.chemin = "src/"+fichier;
		this.nomFichier = fichier;
		this.numMap = numMap;
		map = new Map(numMap,chemin);
		entite = new Entite[map.getHauteur()][map.getLargeur()];

		//CHOIX DE L'IA AU DEBUT DU JEU
		intelligence=Intelligence.ME.get();

		joueur = (Joueur) builder.buildEntity(this,'P');
		espace = (Espace) builder.buildEntity(this,' ');
		poussiere = (Poussiere) builder.buildEntity(this,'.');
		roc = (Roc) builder.buildEntity(this,'r');
		diamant = (Diamant) builder.buildEntity(this,'d');
		mur = (MurBasique) builder.buildEntity(this,'w');
		murTitane = (MurTitane) builder.buildEntity(this,'W');
		murMagique = (MurMagique)builder.buildEntity(this,'M');
		exit = (Exit) builder.buildEntity(this,'X');
		amibe = (Amibe) builder.buildEntity(this,'a');
		luciole = (Luciole) builder.buildEntity(this,'F');
		libellule = (Libellule) builder.buildEntity(this,'B');
		explosion = (Explosion) builder.buildEntity(this,'E');

		fenetre = new FenetreBoulder(this);
		construireMapEntite();

		/*Iterator<Position> it = exit.getPosition().iterator();
		if(it.hasNext())
			gagne = it.next();*/
		/*IA_Directive monIA = new IA_Directive(this);
		monIA.plusCourtCheminDiamant();*/
		
		directive = new IA_Directive(this);
		chemin_direct = directive.actionList();
		jeu();
	}
	
	public MoteurJeu(String[] nomVar){
		for(int i=0;i<nomVar.length;i++){
			switch(nomVar[i]){
			case "tombe":MODE_DEBUG_TOMBER = true; System.out.println("DEB_TOM = "+MODE_DEBUG_TOMBER); break;
			case "libellule":MODE_DEBUG_LIBELLULE = true; break;
			case "luciole":MODE_DEBUG_LUCIOLE = true; break;
			default : System.out.println("FaitChier");
			}
		}
		new MoteurJeu();
	}
	
	//cas de base
	public MoteurJeu(){
		this(1,"BD01plus.bd");
	}


	public void affichage(){

		//System.out.println(System.getProperty("user.dir"));

		if(nbDiamantRecolte>= map.getDiamondRec()){
				afficherPorte();
			}
		//System.out.println("IA : "+intelligence);


		try {
			Thread.sleep(100);
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
		if(aGagne || aPerdu){
			//Réinitialisation des bool pour pouvoir recommencer correctement une partie
			aGagne = false;
			aPerdu = false;
		}
		//remet la touche dans un etat indefini pour eviter les bugs
		touche=KeyEvent.VK_0;
		deplacerEnnemis();
		tomber(diamant);
		tomber(roc);
		//perdu(OBSTACLE_MORTEL);
		joueur.gagne();
		joueur.prendObjets();
		
		//System.out.println("COUCOU "+libellule.getPosition());

		//System.out.println(afficherMapEntite());
		}
		

	private void effacerExplosions() {
		HashSet<Position> faux=new HashSet<Position>(explosion.getPosition());
		Iterator<Position> it=faux.iterator();
		
		while(it.hasNext()){
			Position p = it.next();
			ajouterUnEspace(p);
			explosion.getPosition().remove(p);
		}
	}

	/**
	 * Force le deplacement du joueur dans la fenetre si l'IA est differente de ME.
	 * */
	public void jeu(){

		char deplacement = KeyEvent.VK_0 ;

		while(enJeu){
			deplacement = KeyEvent.VK_0;
			
			//pour inserer une pause en cliquant sur la menuBar
			if(enPause){
				synchronized(thread){
					try {
						thread.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
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
				//pour les appels concurrents entre le click de la souris et la saisie d'une touche
				while(isIndefini(deplacement)){
					deplacement = recupererTouche();
				}
				repriseIA();

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
				//System.out.println("dans jeu : "+chemin_direct.toString());
				if(!chemin_direct.isEmpty()){
					deplacement = chemin_direct.get(0);
					chemin_direct.remove(chemin_direct.get(0));
					tour(deplacement,processPosition());
					processEndOfTurn();
					chemin_direct = directive.actionList();
				}
				else {
					if(directive.diamantAccessible().size() != 0){
						chemin_direct = directive.actionList();
						deplacement = chemin_direct.get(0);
						chemin_direct.remove(chemin_direct.get(0));
						tour(deplacement,processPosition());
						processEndOfTurn();
					}
					else{
						int cpt = 0;
						while(directive.diamantAccessible().size() == 0 && cpt<map.getHauteur()){
							tour((char)KeyEvent.VK_0,processPosition());
							processEndOfTurn();
						}
						if(cpt == map.getHauteur()){
							break;
						}
						
					}
					
				}
				break;
			case 4 : // rejoue
				affichage();
				
				deplacement = directions[tabia]; tabia++;
				if (tabia==directions.length) intelligence=Intelligence.ME.get();
				tour(deplacement,processPosition());
				processEndOfTurn();
				break;
			}//rejoue
		}
	}
	
	/**
	 * Fait passer en IA rejouer avec un tableau de deplacements predefinis passes en parametres
	 * @param tab
	 */
	public void rejouerPartie(char[] tab){
		directions=tab;
		resetMap();
		intelligence=Intelligence.REJOUE.get();
	}

	private boolean isIndefini(char touche){
		return touche == KeyEvent.VK_0;
	}

	public void tour(char touche, Position position){
		/*System.out.println("Perdu ? "+ aPerdu);
		System.out.println("Gagné ? "+ aGagne);
		System.out.println("Position joueur : "+joueur.getPosition());*/

		//fait disparaitre les explosions
		effacerExplosions();

		nbTour++;

		Touche t = Touche.MAUVAISE_TOUCHE;


		int x = position.getX();
		int y = position.getY();

		//System.out.println("IA DEPLACEMENT :" +touche+" Tour : " + tabia);
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
		//	System.out.println("COUCOU");
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
				
				//rajoute l'emplacement du rocher dans l'ens de pos d'espace
				ajouterUnEspace(p);
				
				//Obligé car sinon roc.getPositionTombe().remove(p1); renvoie false 
				Iterator<PositionTombe> it = roc.getPositionTombe().iterator();
				while(it.hasNext()){
					PositionTombe pT = it.next();
					if(pT.getX() == p.getX() && pT.getY() == p.getY()){
						roc.getPositionTombe().remove(pT);
						break;
					}
				}
				//roc.getPositionTombe().remove(p1); //enleve la pos actuelle du rocher
				entite[x1][y1] = roc; //fait pointer sur la nouvelle pos
				entite[x1][y1].getPositionTombe().add(p1); //rajoute l'emplacement du rocher dans l'ens de pos du rocher
				deplacerJoueur(p.getX(),p.getY());
			}
			return true;
		}
	
	/**
	 * Utile pour ameliorer la lisibilite de la methode tomber
	 * @param PositionTombe doitTomber, Entite e
	 * */
	private void tombeSiEspace(PositionTombe doitTomber, Entite e){
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		if(e == null){
			throw new NullPointerException("impossible de faire tomber un null");
		}
		
		ajouterUnEspace(doitTomber);

		//e.getPositionTombe().remove(doitTomber);
		supprimerPositionTombeEntite(doitTomber, e);
		
		Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY());
		//espace.getPosition().remove(pEspace);
		supprimerPositionEntite(pEspace,espace);
		
		PositionTombe aAjouter = new PositionTombe(doitTomber.getX()+1,doitTomber.getY());
		aAjouter.setTombe(true);
		
		entite[doitTomber.getX()+1][doitTomber.getY()] = e;
		/*if(entite[doitTomber.getX()+2][doitTomber.getY()] != espace && 
				entite[doitTomber.getX()+2][doitTomber.getY()] != joueur && 
				entite[doitTomber.getX()+2][doitTomber.getY()] != murMagique &&
				!(entite[doitTomber.getX()+2][doitTomber.getY()] instanceof Ennemi))
			aAjouter.setTombe(false);*/
		
		e.getPositionTombe().add(aAjouter);
	}
	
	/**
	 * Utile pour ameliorer la lisibilite de la methode tomber
	 * @param PositionTombe doitTomber, Entite e
	 * */
	private void tombeDeCote(PositionTombe doitTomber, Entite e) {
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		if(e == null){
			throw new NullPointerException("impossible de faire tomber un null");
		}
		
		//si le rocher est en mouvement
		if(doitTomber.isTombe()){
			doitTomber.setTombe(false);
				
			//s'il y a des espaces a droite et en bas a droite au moins.
			if(doitTomber.getY()<entite[0].length && 
				entite[doitTomber.getX()][doitTomber.getY()+1] == espace &&
				entite[doitTomber.getX()+1][doitTomber.getY()+1] == espace){
				//tombe en diagonale a droite
				//ajout des espaces
				entite[doitTomber.getX()][doitTomber.getY()] = espace;
				espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
				
				//e.getPositionTombe().remove(doitTomber);
				supprimerPositionTombeEntite(doitTomber, e);
					
				Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY()+1);
				//espace.getPosition().remove(pEspace);
				supprimerPositionEntite(pEspace, espace);
					
				entite[doitTomber.getX()+1][doitTomber.getY()+1] = e; //fait pointer sur la nouvelle pos
				PositionTombe pE = new PositionTombe(doitTomber.getX()+1,doitTomber.getY()+1);
				//pE.setTombe(true);
				e.getPositionTombe().add(pE);
				//System.out.println(e.toStringPosition());
			}else if(doitTomber.getY()>0 && 
				entite[doitTomber.getX()][doitTomber.getY()-1] == espace &&
				entite[doitTomber.getX()+1][doitTomber.getY()-1] == espace){
				//tombe en diagonale a gauche
				//ajout des espaces
				entite[doitTomber.getX()][doitTomber.getY()] = espace;
				espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
				
				//e.getPositionTombe().remove(doitTomber);	
				supprimerPositionTombeEntite(doitTomber, e);
					
				Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY()-1);
				//espace.getPosition().remove(pEspace);
				supprimerPositionEntite(pEspace, espace);
				//System.out.println("_____apres suppression____\n\n__ :"+espace.toStringPosition());	
					
				entite[doitTomber.getX()+1][doitTomber.getY()-1] = e; //fait pointer sur la nouvelle pos
				PositionTombe pE = new PositionTombe(doitTomber.getX()+1,doitTomber.getY()-1);
				//pE.setTombe(true);
				e.getPositionTombe().add(pE);
				//System.out.println(e.toStringPosition());
			}// if il y a des cases libres a droite ou a gauche
			
		}else{
			doitTomber.setTombe(true);
		}
	}
	
	private void reglerLaMisere(Entite surLaCase, Position pEspace, Position p){
		if(surLaCase.getPosition().remove(pEspace) == false){ //Oblige de faire cela car pour les lucioles renvoie toujours false mais fonctionne pour le joueur
			Iterator<Position> it3 = surLaCase.getPosition().iterator();
			Position pTest = null;

			while(it3.hasNext()){
				 pTest = it3.next();
				if(pTest.getX() ==  p.getX()+1 && pTest.getY() == p.getY()){
					break;
				}
			}
			surLaCase.getPosition().remove(pTest);
		}
	}

	/**
	 * Utile pour ameliorer la lisibilite de la methode tomber
	 * @param PositionTombe doitTomber, Entite e
	 * */
	private void tueLesVivants(PositionTombe doitTomber, Entite e, Entite surLaCase){
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		if(e == null){
			throw new NullPointerException("impossible de faire tomber un null");
		}
		if(surLaCase == null){
			throw new NullPointerException("surLaCase il y a un null");
		}
		
			entite[doitTomber.getX()][doitTomber.getY()] = espace;
			espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
			e.getPositionTombe().remove(doitTomber);
			Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY());
			//change de joueur.getPosition().remove(pEspace)

			reglerLaMisere(surLaCase,pEspace,doitTomber);
			
			e.getPositionTombe().add(new PositionTombe(doitTomber.getX()+1,doitTomber.getY()));
			entite[doitTomber.getX()+1][doitTomber.getY()] = e;

			//si l'entite ecrasee etait le joueur, fais-le perdre
			if(surLaCase == joueur){
				perdu();
			}
	}
	
	/**
	 * Utile pour ameliorer la lisibilite de la methode tomber
	 * @param PositionTombe doitTomber, Entite e
	 * */
	private void transformeLesRochersEnDiamants(PositionTombe doitTomber, Entite e){
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		if(e == null){
			throw new NullPointerException("impossible de faire tomber un null");
		}
		
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
	
	/**
	 * Teste si le rocher ou le diamant au dessus d'un rocher peut tomber ou non.
	 * @param PositionTombe doitTomber
	 * @return true si la chute est possible et false sinon
	 * */
	private boolean peutTomber(PositionTombe doitTomber){
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		
		return entite[doitTomber.getX()+1][doitTomber.getY()] == roc &&
				((entite[doitTomber.getX()][doitTomber.getY()+1] == espace &&
				entite[doitTomber.getX()+1][doitTomber.getY()+1] == espace) ||
				(entite[doitTomber.getX()][doitTomber.getY()-1] == espace &&
				entite[doitTomber.getX()+1][doitTomber.getY()-1] == espace));
	}
	
	private void explose(Position p){
		if(p == null){
			throw new NullPointerException("La position pour l'explosion est a null");
		}
		
		if(peutExploser(p)){
			//retire les cases autour de la position
			testIndestructible(p);//milieu
			testIndestructible(new Position(p.getX()-1,p.getY()));//dessus
			testIndestructible(new Position(p.getX()-1,p.getY()+1));//dessus-droite
			testIndestructible(new Position(p.getX(),p.getY()+1));//droite
			testIndestructible(new Position(p.getX()+1,p.getY()+1));//droite-dessous
			testIndestructible(new Position(p.getX()+1,p.getY()));//dessous
			testIndestructible(new Position(p.getX()+1,p.getY()-1));//dessous-gauche
			testIndestructible(new Position(p.getX(),p.getY()-1));//gauche
			testIndestructible(new Position(p.getX()-1,p.getY()-1));//gauche-dessus
		}
	}
	
	private boolean supprimerPositionEntite(Position p){
		return supprimerPositionEntite(p,entiteCarte(p));
	}
	
	private boolean supprimerPositionTombeEntite(PositionTombe p){
		return supprimerPositionTombeEntite(p,entiteCarte(p));
	}
	
	private boolean supprimerPositionEntite(Position p, Entite e){
		Position posExtraite = recupererPosition(e,p);
		return e.getPosition().remove(posExtraite);
	}
	
	private boolean supprimerPositionTombeEntite(PositionTombe p, Entite e){
		PositionTombe posExtraite = (PositionTombe) recupererPosition(e,p);
		if(posExtraite != null){//attention aux explosions !
			posExtraite.setTombe(true);
			if(e.getPositionTombe().remove(posExtraite)==false){
				posExtraite.setTombe(false);
				return e.getPositionTombe().remove(posExtraite);
			}//si le test n'a pas marche pour tombe = true
		}//if la position existe ( explosions )
		return true;
	}
	
	/**
	 * Teste si la case a la position p est destructible ou non. 
	 * Si la case est hors-carte elle n'est pas detruite.
	 * @param Position p
	 * */
	private void testIndestructible(Position p) {
		if(p == null){
			throw new NullPointerException("impossible de tester une case null");
		}
		if(!isaPerdu()){
			if(estCaseExistante(p) && entiteCarte(p) != murTitane && entiteCarte(p) != murMagique && entiteCarte(p) != diamant){
				if(entiteCarte(p) == roc || entiteCarte(p) == diamant){
					System.out.println("___________\n\n"+p+"_____\n_______+tombe :");
					//+entiteCarte(p).getPositionTombe().remove(p)
					//if(entiteCarte(p).getPositionTombe().remove(p)==false){
					PositionTombe pos=new PositionTombe(p.getX(),p.getY());
					supprimerPositionTombeEntite(pos);
				}else{
					System.out.println("___________\n\n"+p+"_____\n_______+tombe pas :"+entiteCarte(p).getPosition().remove(p));
					supprimerPositionEntite(p);
				}
				//si la case a exploser est le joueur, alors c'est perdu.
				if(entiteCarte(p)==joueur){
					//on n'ajoute pas d'explosion quand le joueur perd car sinon,
					//apres le reset il reste un morceau d'explosion
					perdu();
				}else if(entiteCarte(p)==luciole || entiteCarte(p)==libellule || entiteCarte(p)==amibe){
					entite[p.getX()][p.getY()] = diamant;
					diamant.getPositionTombe().add(new PositionTombe(p.getX(),p.getY()));
				}else{
					entite[p.getX()][p.getY()] = explosion;
					explosion.getPosition().add(p);
				}
			}
		}
	}
	
	/**
	 * Une methode qui evite les problemes courants qui surviennent quand on doit
	 * supprimer une position d'un HashSet.
	 * */
	private Position recupererPosition(Entite e, Position p){
		Iterator<?> it=null;
		if(e == roc || e == diamant){
			it=e.getPositionTombe().iterator();
		}else{
			it=e.getPosition().iterator();
		}
		while(it.hasNext()){
			Position sortie=(Position) it.next();
			if(sortie.getX() == p.getX() && sortie.getY() == p.getY()){
				//System.out.println("Une sortie !");
				return sortie;
			}
		}
		return null;
	}
	
	/**
	 * Teste si la case de coordonnees (x,y) existe ou non
	 * @param int x,int y
	 * @return true si la case existe et false sinon
	 * */
	public boolean estCaseExistante(int x,int y){
		return x>=0 && y >=0 && x < entite.length && y <entite[0].length;
	}
	
	/**
	 * Teste si la case de coordonnees (x,y) existe ou non
	 * @param Position p
	 * @return true si la case existe et false sinon
	 * */
	public boolean estCaseExistante(Position p){
		if(p == null){
			throw new NullPointerException("impossible de voir si une case null existe");
		}
		return estCaseExistante(p.getX(),p.getY());
	}
	
	/**
	 * Renvoie l'entite present sur la carte a la position p
	 * @param Position p
	 * @return Entite entite
	 */
	public Entite entiteCarte(Position p){
		if(p == null){
			throw new NullPointerException("impossible de tester une case null");
		}
		return entite[p.getX()][p.getY()];
	}

	/**
	 * Fait tomber le rocher
	 * */
	public void tomber(Entite e){
		if(!(e instanceof Roc) && !(e instanceof Diamant)){
			throw new IllegalArgumentException();
		}

		PositionTombe pos;
		Set<PositionTombe> aTomber = new HashSet<PositionTombe>();
		Iterator<PositionTombe> it = e.getPositionTombe().iterator();

		//copie la liste des positions de rocher pour faire le traitement separement
		while(it.hasNext()){
			pos = it.next();
			aTomber.add(pos);
		}

		Iterator<PositionTombe> it1 = aTomber.iterator();
		PositionTombe doitTomber;

		//pour chaque rocher a faire tomber
		while(it1.hasNext()){
			//pour le debugage de tomber
			if(MODE_DEBUG_TOMBER){
				System.out.println("ATomber : "+e.toString());
				getFenetre().repaint();
				synchronized(getFenetre().getMoteur().thread){
					try {
						thread.wait();
					} catch (InterruptedException exp) {
						// TODO Auto-generated catch block
						exp.printStackTrace();
					}
				}
			}
			doitTomber = it1.next();
			//si le joueur a perdu, interrompt les operations
			if(!aPerdu){
				Position posSuivante=new Position(doitTomber.getX()+1,doitTomber.getY());
				if(doitTomber.isTombe() && peutExploser(posSuivante)){
					//explose si la cible du dessous est valide, sinon se deplace
					explose(posSuivante);
				}else if(entite[doitTomber.getX()+1][doitTomber.getY()] == espace){
					//si c'est un espace, tombe
					tombeSiEspace(doitTomber,e);
				}else if(peutTomber(doitTomber)){
					//si c'est un rocher ou un diamant, et qu'il y a de la place a gauche ou a droite, tombe de cote
					tombeDeCote(doitTomber,e);
				}else{
					Entite surLaCase=entiteCarte(posSuivante);
					
					//si c'est un joueur ou une luciole, tue-les.
					if((surLaCase == joueur ||
							surLaCase == luciole ||
							surLaCase == libellule)
							&& doitTomber.isTombe()){
						tueLesVivants(doitTomber,e, surLaCase);
						
					}else if(entite[doitTomber.getX()+1][doitTomber.getY()] == murMagique){
						//si c'est un mur magique, transforme le rocher en diamant
						transformeLesRochersEnDiamants(doitTomber,e);
					}else{
						//Arrete de tomber
						//e.getPositionTombe().remove(doitTomber);
						doitTomber.setTombe(false);
						supprimerPositionTombeEntite(doitTomber, e);
							
						PositionTombe aModif = new PositionTombe(doitTomber.getX(),doitTomber.getY());
						aModif.setTombe(false);
						e.getPositionTombe().add(aModif);
					}//else
				}//else
			}else{
				break;
			}//else -> if a perdu
		}//while
	}//tomber

	/**
	 * Teste si la case de position p peut exploser
	 * @param Position p
	 * @return true si la case peut exploser et false sinon
	 * */
	private boolean peutExploser(Position p) {
		return entite[p.getX()][p.getY()] == joueur ||
				entite[p.getX()][p.getY()] == luciole ||
				entite[p.getX()][p.getY()] == libellule ||
				entite[p.getX()][p.getY()] == amibe ||
				entite[p.getX()][p.getY()] == mur;
	}

	/**
	 * Construit la carte du jeu avec tout ses objets a partir d' un tableau a 2 dimension
	 * obtenu en lisant le fichier contenant tout les niveaux.
	 * */
	public void construireMapEntite(){
		//nouveau tableau avec nouvelle taille en X et Y
		Entite[][] nouveauTab = new Entite[map.getHauteur()][map.getLargeur()];
		
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){

				switch(map.getTab(i,j)){
				case ' ': nouveauTab[i][j] = espace; break;
				case 'P': nouveauTab[i][j] = joueur; break;
				case '.': nouveauTab[i][j] = poussiere; break;
				case 'r': nouveauTab[i][j] = roc; break;
				case 'd': nouveauTab[i][j] = diamant; break;
				case 'w': nouveauTab[i][j] = mur; break;
				case 'W': nouveauTab[i][j] = murTitane; break;
				case 'X': nouveauTab[i][j] = espace; posPorte = new Position(i,j);break;
				case 'M': nouveauTab[i][j] = murMagique; break;
				case 'a': nouveauTab[i][j] = amibe; break;
				default:
					if(map.getTab(i,j) == 'F' || map.getTab(i,j) == 'o' || map.getTab(i,j) == 'O' || map.getTab(i,j) == 'q' || map.getTab(i,j) == 'Q')
						{nouveauTab[i][j] = luciole; break;}
					if( map.getTab(i,j) == 'b' || map.getTab(i,j) == 'B' || map.getTab(i,j) == 'c' || map.getTab(i,j) == 'C')
						{nouveauTab[i][j] = libellule; break; }
					else
						{nouveauTab[i][j] = espace; break ; }
				}
				if(nouveauTab[i][j] == roc || nouveauTab[i][j] == diamant)
					nouveauTab[i][j].getPositionTombe().add(new PositionTombe(i,j));
				else
					nouveauTab[i][j].getPosition().add(new Position(i,j));
				
				entite=nouveauTab;//memorise le nouveau tableau
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
	 * Ajoute la porte au jeu si le nombre de diamants est suffisant.
	 * */
	public void afficherPorte(){
		for(int i=0;i<map.getHauteur();i++){
			for(int j=0; j<map.getLargeur();j++){
				if(map.getTab(i, j) == 'X')
					entite[i][j] = exit;
			}
		}
		porteAffiche = true;
	}

	/**
	 * Fait changer de carte et relance une nouvelle partie.
	 * */
	
	public void resetMap(){
		/*aGagne = false;
		aPerdu = false;*/
		
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
		chemin_direct.clear();
		
		score=0;
		nbDiamantRecolte = 0;
		nbTour = 0;
		
		construireMapEntite();
		System.out.println(afficherMapEntite());
	}
	
	public void changerMap(int n){
		/*aGagne = false;
		aPerdu = false;*/
		fenetre.effacerMessageVictoire();
		fenetre.ecrireMessage("Changement en carte "+n, 2);
		enPause=false;
		
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

		map = new Map(n,chemin);
		numMap = map.getNumMap();
		
		
		score=0;
		nbDiamantRecolte = 0;
		nbTour = 0;

		construireMapEntite();
		//fenetre.getMoteur().resetMap();
		//fenetre.repaint();
		System.out.println("Position joueur : "+joueur.getPosition());
		//jeu();
	}

	/**
	 * Deplace le joueur sur la case de coordonnees (x, y)
	 * @param int x, int y
	 * */
	public void deplacerJoueur(int x, int y){
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		if(entite[x][y] == exit){
			System.out.println("JE GAGNE ???");
				gagner();
		}
		if(!aGagne){
			if(entite[x][y] == diamant){
				gagnerPoints();
			}
			Iterator<Position> it = joueur.getPosition().iterator();
			Position p = it.next(); //pos actuelle du joueur
			//rajoute l'emplacement du joueur dans l'ens de pos d'espace
			ajouterUnEspace(p);
			
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
		

	}

	/**
	 * Ajoute un espace a la position indiquee
	 * @param Position p
	 * */
	public void ajouterUnEspace(Position p) {
		if(p == null){
			throw new NullPointerException("Impossible d'ajouter un espace a une position null");
		}
		entite[p.getX()][p.getY()] = espace;
		espace.getPosition().add(p);
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
		default : return false;
		}
	}

	/**
	 * Verifie si la case (posX, posY) est libre ( si elle est traversable )
	 * @return true si la case est libre et false sinon.
	 * */
	public boolean caseLibre(int posX, int posY){
		//System.out.println("Emplacement : "+posX+" "+posY+" longueur, largeur :"+entite.length);

		//verifie le contenu de la case
		if(entite[posX][posY] instanceof Ennemi)
			return false;
		if(entite[posX][posY] == diamant){
			Iterator<PositionTombe> it = diamant.getPositionTombe().iterator();
			PositionTombe pT = null;
			while(it.hasNext()){
				pT = it.next();
				if(pT.getX() == posX && pT.getY() == posY){
					if(pT.isTombe())
						return false;
				}
			}
		}
		if(entite[posX][posY].isTraversable()){
			//System.out.println("Case libre");
			return true;
		}
		//System.out.println("Case non libre");
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

	/**
	 * Fait gagner des points au joueur quand il prend des diamants.
	 * */
	public void gagnerPoints(){
		nbDiamantRecolte++;
		if(nbDiamantRecolte > map.getDiamondRec()){
			score += map.getDiamondVal() + map.getDiamondBonus();
		}
		else
			score += map.getDiamondVal();
	}

	/**
	 * Fait gagner le joueur. Fait aussi changer de carte.
	 * */
	public void gagner(){
		aGagne = true;
		if(numMap == map.getNbMap()){
			System.out.println("ca a fonctionne");
			fenetre.afficherMessageVictoire();
		}else{
			changerMap(++numMap);
		}
	}

	public void perdu() {
		//System.exit(0);//A MODIF UNE FOIS QU'ON SAURA QUOI FAIRE !!!
		//enJeu=false;
		getFenetre().ecrireMessage("Vous etes mort !", 1);
		aPerdu = true;
		resetMap();
		
	}

	
	/*private void afficherJeu(Map m) {
		System.out.println(m.toString());
	}*/
	
	private void deplacerEnnemis(){
		luciole.deplacer(entite);
		libellule.deplacer(entite);
		amibe.deplacer(entite);	
	}


	public boolean estIA(Intelligence ia){
		return ia.get() == intelligence;
	}

	public Entite[][] getEntite(){
		return entite;
	}
	
	public int getNbMap(){
		return map.getNbMap();
	}

	public boolean isEnJeu() {
		return enJeu;
	}

	public void setEnJeu(boolean enJeu) {
		this.enJeu = enJeu;
	}

	public boolean isaGagne() {
		return aGagne;
	}

	public boolean isaPerdu() {
		return aPerdu;
	}

	/**
	 * Renvoie le pointeur vers la fenêtre principale
	 * @return FenetreBoulder fenetre
	 * */
	public FenetreBoulder getFenetre() {
		return fenetre;
	}

	public int getHauteurMap() {
		return map.getHauteur();
	}

	public int getLargeurMap() {
		return map.getLargeur();
	}
	
	
	public String getNomFichier(){
		return nomFichier;
	}
	
	public int getNumMap(){
		return numMap;
	}

	public boolean isPorteAffiche() {
		return porteAffiche;
	}

	public Set<PositionTombe> getPositionDiamant() {
		return diamant.getPositionTombe();
	}
	
	public Set<Position> getPositionJoueur() {
		return joueur.getPosition();
	}
	
	/*public int getDiamondValue(){
		return map.getDiamondVal();
	}
	
	public int getScoreAAvoir(){
		return map.getDiamondRec()*map.getDiamondVal();
	}*/
	
	public int getNbDiamandRec(){
		return map.getDiamondRec();
	}
	
	public Position getPosPorte(){
		//System.out.println("LA PORTE !!! "+posPorte.toString());
		return posPorte;	
	}

	public int getNbDiamantRecolte() {
		return nbDiamantRecolte;
	}
	
	/**
	 * Fait que l'IA va s'arreter dans son execution jusqu' a la prochaine repriseIA()
	 * */
	public void pauseIA(){
		getFenetre().ecrireMessage("Jeu en Pause", 2);
		enPause=true;
	}
	/**
	 * Fait reprendre l'IA qui a ete stoppee par pauseIA()
	 * */
	public void repriseIA(){
		getFenetre().ecrireMessage("Jeu en Fonctionnement", 10);
		enPause=false;
	}
	/**
	 * Verifie si le jeuest en pause ou non
	 * @return true si le jeu est en pause et false sinon
	 * */
	public boolean enPause(){
		return enPause;
	}
}
