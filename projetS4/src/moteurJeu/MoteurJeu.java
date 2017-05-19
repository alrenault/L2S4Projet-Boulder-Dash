package moteurJeu;

import ia.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

import entite.*;
import map.Map;
import affichage.FenetreBoulder;
import moteurJeu.Enregistreur;

/**
 * Classe centrale de boulder dash creant le moteur de jeu se chargeant de tout coordonner
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */

public class MoteurJeu {


	/**
	 * Char representant la touche appuye
	 */
	public char touche;
	
	/**
	 * Boolean representant une victoire de l'IA parfaite
	 * */
	public boolean iaParfaiteAGagne = false;
	
	/**
	 * Fil d'execution du programme
	 */
	public Thread thread=Thread.currentThread();
	
	/**
	 * Booleen represantant si le jeu est en pause ou non
	 */
	private boolean enPause =false;
	
	/**
	 * Mode debug pour tomber
	 */
	public static boolean MODE_DEBUG_TOMBER=false;
	
	/**
	 * Mode debug pour les libellules
	 */
	public static boolean MODE_DEBUG_LIBELLULE=false;
	
	/**
	 * Mode debug pour les lucioles
	 */
	public static boolean MODE_DEBUG_LUCIOLE=false;
	/**
	 * Mode debug pour l' IA parfaite
	 */
	public static boolean MODE_DEBUG_PARFAITE=false;

	//IA

	
	/**
	 * Champ de l'IA random
	 */
	private IA_Random iaRandom;
	
	/**
	 * Champ de l'IA genetique
	 */
	private IA_Genetique iaGen;
	
	/**
	 * Champ de l'IA directive
	 */
	private IA_Directive directive;
	
	/**
	 * Champ de l'IA parfaite
	 */
	private IA_Parfaite parfaite;
	
	/**
	 * Liste de Character (representant les touches) de l'IA directive
	 */
	private List<Character> chemin_direct;
	/**
	 * Liste de Character (representant les touches) de l'IA rejoue
	 */
	private ArrayList<Character> deplacements;	//Mémorise les touches

	/**
	 * Int representant l'IA utilise :
	 * -1 : No
	 * 0 : Pas d'IA
	 * 1 : Random
	 * 2 : Genetique
	 * 3 : Directive
	 */
	private int intelligence;

	//reste

	/**
	 * Tableau d'entite representant la map
	 */
	private Entite[][] entite;
	
	/**
	 * Creation de la fenetre pour l'affichage graphique
	 */
	private FenetreBoulder fenetre;
	
	/**
	 * Numero de la map en cours d'utilisation
	 */
	private int numMap;
	
	/**
	 * Map en cours d'utilisation
	 */
	private Map map;
	
	/**
	 * Chemin vers le fichier où se trouve la map
	 */
	private String chemin;
	
	/**
	 * Nom du fichier où se trouve la map
	 */
	private String nomFichier;

	/**
	 * Le construteur de toutes les entites se trouvant sur la map
	 */
	private BuildEntity builder = new BuildEntity();
	
	/**
	 * Entite representant le joueur
	 */
	public Joueur joueur;
	
	/**
	 * Entite representant l'espace
	 */
	private Espace espace;
	
	/**
	 * Entite representant la poussiere
	 */
	private Poussiere poussiere;
	
	/**
	 * Entite representant le rocher
	 */
	private Roc roc;
	
	/**
	 * Entite representant le diamant
	 */
	private Diamant diamant;
	
	/**
	 * Entite representant le mur basique
	 */
	private MurBasique mur;
	
	/**
	 * Entite representant le mur de titane
	 */
	private MurTitane murTitane;
	/**
	 * Entite representant le mur magique
	 */
	private MurMagique murMagique;
	
	/**
	 * Entite representant la sortie
	 */
	private Exit exit;
	
	/**
	 * Entite representant l'amibe
	 */
	private Amibe amibe;
	
	/**
	 * Entite representant la luciole
	 */
	private Luciole luciole;
	
	/**
	 * Entite representant la libellule
	 */
	private Libellule libellule;
	
	/**
	 * Entite representant l'explosion
	 */
	private Explosion explosion;

	//private Position gagne;
	/**
	 * Score de la partie en cours
	 */
	private int score=0; 
	
	/**
	 * Nombre de diamant deja recolte
	 */
	private int nbDiamantRecolte = 0;
	
	/**
	 * Nombre de tours (de deplacements)
	 */
	private int nbTour = 0;
	
	//private boolean enJeu;
	
	/**
	 * Boolean servant a savoir si on a gagne le niveau
	 */
	private boolean aGagne;
	
	/**
	 * Boolean servant a savoir si on a perdu le niveau
	 */
	private boolean aPerdu;
	
	/**
	 * Boolean servant a savoir si la porte est affichee ou non
	 */
	private boolean porteAffiche = false;
	
	/**
	 * Position de la porte
	 */
	private Position posPorte;

	/**
	 * Permet a la barre de menu de changer le mode d'IA sans pour autant donner acces
	 * a la variable.
	 * @param ia La nouvelle IA
	 * */
	public void changerIA(Intelligence ia){
		this.intelligence=ia.get();
	}
	
	/**
	 * Constructeur general de la classe MoteurJeu
	 * @param numMap Numero de la map voulue
	 * @param fichier Nom du fichier où se trouve la map
	 */
	public MoteurJeu(int numMap, String fichier){

		this.chemin = "src/"+fichier;
		this.nomFichier = fichier;
		this.numMap = numMap;
		map = new Map(numMap,chemin);
		entite = new Entite[map.getHauteur()][map.getLargeur()];

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
		touche = KeyEvent.VK_0;
		
		//IA
		
		intelligence = Intelligence.ME.get();
		
		//deplacements ;
		deplacements = new ArrayList<Character>();
		
		//Random
		iaRandom = new IA_Random();
		//Genetique
		iaGen = new IA_Genetique(this);
		
		//Directive
		directive = new IA_Directive(this);
		chemin_direct = directive.actionList();
		parfaite=new IA_Parfaite(5,this);
		
		//jeu();
	}
	
	/**
	 * Constructeur de test de la classe MoteurJeu
	 */
	public MoteurJeu(){
		this(1,"BD01plus.bd");	
	}

	/**
	 * Constructeur de la classe MoteurJeu. Prend en charge les arguments du
	 * mode DEBUG suivants :
	 * -tombe
	 * -libellule
	 * -lucole
	 * -parfaite
	 * @param numMap : numero de la map choisie
	 * @param nomFichier : chemin de fichier
	 * @param argsDebug : les arguments qui suivent l'option -d
	 * @param ia Numero de l'IA
	 * @param aRejouer Liste des deplacements a rejouer
	 */
	public MoteurJeu(int numMap, String nomFichier, String[] argsDebug, int ia, List<Character> aRejouer) {
		this(numMap,nomFichier);
		if(aRejouer != null){
			//rejouerPartie(aRejouer);
			for(int i=0;i<aRejouer.size();i++){
				System.out.println("->"+aRejouer.get(i));
			}
			deplacements = new ArrayList<Character>(aRejouer);
		}
		//if(ia >= -2 && ia <= 4){
		System.out.println("intel : "+ia);
			intelligence = ia;
		//}
		for(int i=0;i<argsDebug.length;i++){
			switch(argsDebug[i]){
			case "tombe":MODE_DEBUG_TOMBER = true; break;
			case "libellule":MODE_DEBUG_LIBELLULE = true; break;
			case "luciole":MODE_DEBUG_LUCIOLE = true; break;
			case "parfaite":MODE_DEBUG_PARFAITE = true; break;
			}
		}
	}
	
	

	/**
	 * Affiche tour par tour la map sur l'interface graphique
	 */
	public void affichage(){
		if(nbDiamantRecolte>= map.getDiamondRec()){
				afficherPorte();
			}
		if (intelligence != 2 && intelligence != 4) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {/*
			System.out.println(iaGen.getITRocker());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}

		fenetre.repaint();
		System.out.println(afficherMapEntite());
	}

	/**
	 * Renvoi la position du joueur
	 * @throws IllegalArgumentException Si le joueur n'a aucune ou plusieurs positions renvoi une erreur
	 * @return Retourne la position du joueur
	 */
	public Position processPosition(){
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		Iterator<Position> it = joueur.getPosition().iterator();
		return it.next();
	}

	/**
	 * Fait toutes les actions de fin de tour : deplace les autres entites, test si on a gagne. 
	 * Reinitialise aGagne ou aPerdu pour recommencer une nouvelle partie.
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
		}
		
	/**
	 * Efface les explosions au debut d'un tour
	 */
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
	 * Appelle les differentes methodes du jeu en fontion de l'intelligence utilise actuellement
	 * */
	public void jeu(){

		char deplacement = KeyEvent.VK_0 ;
		
		switch (intelligence){
		case 2: //GENETIQUE --- Une population de Rockford va jouer et s'ameliorer au fil du temps
			
			while(true){
				affichage();
				//System.out.println(iaGen.getITRocker());
				if(!iaGen.thisRockfordisMoving()){
					exportPath();
					resetMap();
				}
				deplacement = iaGen.action();
				tour(deplacement,processPosition());
				processEndOfTurn();
			}
			//break; 
			//!GENETIQUE
		case 4 : //parfaite
			System.out.println("test parfaite jeu() - debut analyse");
			parfaite.lancerAnalyse(100);
			System.out.println("test parfaite jeu() - fin analyse");
			char[] deplacements = parfaite.recupererSolution();
			resetMap();
			for(int i=0;i<deplacements.length-1;i++){
				affichage();
				fenetre.repaint();
				synchronized(thread){
					try {
						Thread.sleep(500);
					} catch (InterruptedException exp) {
						exp.printStackTrace();
					}
				}
				tour(deplacements[i],processPosition());
				processEndOfTurn();
			}
			intelligence = Intelligence.ME.get();
			//break;
			//parfaite
		}		

		while(true){
			int cpt = 0;
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
			case -2 : // rejoue
				affichage();
				if(!deplacements.isEmpty()){
					deplacement = deplacements.get(0);
					deplacements.remove(0);
				}
				else{
					deplacement = recupererTouche();
				}
				tour(deplacement,processPosition());
				processEndOfTurn();
				break;
				//rejoue
				
			case -1 : //NO --- Rockford reste immobile
				affichage();
				deplacement = KeyEvent.VK_0;

				tour(deplacement,processPosition());
				processEndOfTurn();

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

			break; //!ME

			case 1 : //RANDOM --- Joue Rockford aleatoirement
				affichage();
				deplacement = iaRandom.action();
				tour(deplacement,processPosition());
				processEndOfTurn();

			break; //!RANDOM

			case 2 : break;

			case 3 :	//Directive
				affichage();
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
						if(chemin_direct.size() == 0){
							intelligence = Intelligence.ME.get();
							fenetre.ecrireMessage("Aucun chemin trouvé vers la porte. A vous de jouer", 10);
						}
						else{
							deplacement = chemin_direct.get(0);
							chemin_direct.remove(chemin_direct.get(0));
							tour(deplacement,processPosition());
							processEndOfTurn();
						}
						
					}
					else{
						while(directive.diamantAccessible().size() == 0 && cpt<map.getHauteur() && intelligence == Intelligence.DIRECTIVE.get()){
							tour((char)KeyEvent.VK_0,processPosition());
							processEndOfTurn();
							fenetre.repaint();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
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
							cpt++;
						}
						if(cpt >= map.getHauteur()){
							intelligence = Intelligence.ME.get();
							fenetre.ecrireMessage("Aucun chemin trouvé. A vous de jouer", 10);
						}	
					}
				}
				break;
				
				case 4: jeu();
			}
		}
	}

	/**
	 * Fait passer en IA rejouer avec une liste de deplacements predefinis passes en parametres
	 * @param listRejoue Liste de deplacements
	 */
	public void rejouerPartie(List<Character> listRejoue){
		deplacements=(ArrayList<Character>)listRejoue;
		resetMap();
		intelligence=Intelligence.REJOUE.get();
	}

	/**
	 * Test si la touche correspond au sur place
	 * @param touche Touche que l'on veut tester
	 * @return Retourne true si la touche correspond a KeyEvent.VK_0 sinon false
	 */
	private boolean isIndefini(char touche){
		return touche == KeyEvent.VK_0;
	}
	
	/**
	 * Methode simulant un tour de jeu pour un joueur
	 * @param touche La touche appuye par l'utilisateur pour la prochaine position du joueur
	 * @param position Position actuelle du joueur
	 */
	public void tour(char touche, Position position){
		//fait disparaitre les explosions
		effacerExplosions();

		nbTour++;

		Touche t = Touche.MAUVAISE_TOUCHE;


		int x = position.getX();
		int y = position.getY();

		switch(touche){
			case KeyEvent.VK_RIGHT: t = Touche.TOUCHE_DROITE; y+=1;break;
			case KeyEvent.VK_LEFT: t = Touche.TOUCHE_GAUCHE; y-=1;break;
			case KeyEvent.VK_UP: t = Touche.TOUCHE_HAUT; x-=1;break;
			case KeyEvent.VK_DOWN: t = Touche.TOUCHE_BAS; x+=1;break;
			case KeyEvent.VK_0 : t = Touche.TOUCHE_IMMOBILE;break;
		}

		if(entite[x][y] instanceof Roc){
			pousserRocher(new PositionTombe(x,y));
			memorizePath(touche);
		}
		else if (deplacementPossible(t)){
			memorizePath(touche);
			deplacerJoueur(x,y);
		} else memorizePath((char) KeyEvent.VK_0);
		
		

	}

	/**
	 * Methode permettant de pousser un rocher
	 * @param p Position où l'on veut pousser le rocher
	 * @return Retourne false si ce n'est pas possible et true sinon
	 */
	public boolean pousserRocher(Position p){
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
				entite[x1][y1] = roc; //fait pointer sur la nouvelle pos
				entite[x1][y1].getPositionTombe().add(p1); //rajoute l'emplacement du rocher dans l'ens de pos du rocher
				deplacerJoueur(p.getX(),p.getY());
			}
			return true;
		}
	
	/**
	 * Fait tomber une entite pouvant tomber (Roc ou Diamant) s'il y a un espace en dessous
	 * @param doitTomber Position de l'entite pouvant tomber
	 * @param e Reference vers le type d'entite devant tomber
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
		
		//Pour faire directement arreter de tomber un diamant pour eviter de se faire ecraser dans certains cas
		if(e instanceof Diamant && 
				entite[doitTomber.getX()+2][doitTomber.getY()] != espace && 
				entite[doitTomber.getX()+2][doitTomber.getY()] != joueur && 
				entite[doitTomber.getX()+2][doitTomber.getY()] != murMagique &&
				!(entite[doitTomber.getX()+2][doitTomber.getY()] instanceof Ennemi))
			aAjouter.setTombe(false);
		
		e.getPositionTombe().add(aAjouter);
	}
	
	/**
	 * Fait tomber une entite pouvant tomber de cote
	 * @param doitTomber L'entite devant tomber
	 * @param e Reference vers le type d'entite devant tomber
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
				
				supprimerPositionTombeEntite(doitTomber, e);
					
				Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY()+1);
				supprimerPositionEntite(pEspace, espace);
					
				entite[doitTomber.getX()+1][doitTomber.getY()+1] = e; //fait pointer sur la nouvelle pos
				PositionTombe pE = new PositionTombe(doitTomber.getX()+1,doitTomber.getY()+1);
				e.getPositionTombe().add(pE);
			}else if(doitTomber.getY()>0 && 
				entite[doitTomber.getX()][doitTomber.getY()-1] == espace &&
				entite[doitTomber.getX()+1][doitTomber.getY()-1] == espace){
				//tombe en diagonale a gauche
				//ajout des espaces
				entite[doitTomber.getX()][doitTomber.getY()] = espace;
				espace.getPosition().add(new Position(doitTomber.getX(),doitTomber.getY()));
					
				supprimerPositionTombeEntite(doitTomber, e);
					
				Position pEspace = new Position(doitTomber.getX()+1,doitTomber.getY()-1);
				supprimerPositionEntite(pEspace, espace);
					
				entite[doitTomber.getX()+1][doitTomber.getY()-1] = e; //fait pointer sur la nouvelle pos
				PositionTombe pE = new PositionTombe(doitTomber.getX()+1,doitTomber.getY()-1);
				e.getPositionTombe().add(pE);
			}// if il y a des cases libres a droite ou a gauche
			
		}else{
			doitTomber.setTombe(true);
		}
	}
	
	/**
	 * Methode permettant de forcer le remove dans le cas ou celui ci ne fonctionne pas alors qu'il le devrait
	 * @param surLaCase Entite dont on veut remove la position
	 * @param pEspace La position que l'on veut enlever
	 * @param p
	 */
	private void forceRemove(Entite surLaCase, Position pEspace, Position p){
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
	 * Methode permettant de tuer les entites vivantes par une entite tombant
	 * @param doitTomber Position de l'entite tombant
	 * @param e Reference vers l'entite tombant
	 * @param surLaCase Reference vers l'entite a tuer
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

			forceRemove(surLaCase,pEspace,doitTomber);
			
			e.getPositionTombe().add(new PositionTombe(doitTomber.getX()+1,doitTomber.getY()));
			entite[doitTomber.getX()+1][doitTomber.getY()] = e;

			//si l'entite ecrasee etait le joueur, fais-le perdre
			if(surLaCase == joueur){
				perdu();
			}
	}
	
	/**
	 * Permet de transformer les rochers en diamants et vis versa
	 * @param doitTomber Position de l'entite a transformer
	 * @param e Reference vers le type d'entite a transformer
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
	 * @param  doitTomber Position de l'entite devant tomber
	 * @return true si la chute est possible et false sinon
	 * */
	private boolean peutTomber(PositionTombe doitTomber){
		if(doitTomber == null){
			throw new NullPointerException("impossible de faire tomber sur une positionTombe a null");
		}
		
		return (entite[doitTomber.getX()+1][doitTomber.getY()] == roc ||
				entite[doitTomber.getX()+1][doitTomber.getY()] == diamant) 
				&&
					((entite[doitTomber.getX()][doitTomber.getY()+1] == espace &&
					entite[doitTomber.getX()+1][doitTomber.getY()+1] == espace) 
					||
					(entite[doitTomber.getX()][doitTomber.getY()-1] == espace &&
					entite[doitTomber.getX()+1][doitTomber.getY()-1] == espace));
	}
	
	/**
	 * Permet de faire exploser les entites ecrasee par un rocher
	 * @param p La position où se trouve l'explosion
	 */
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
	
	/*private boolean supprimerPositionEntite(Position p){
		return supprimerPositionEntite(p,entiteCarte(p));
	}*/
	
	/*private boolean supprimerPositionTombeEntite(PositionTombe p){
		return supprimerPositionTombeEntite(p,entiteCarte(p));
	}*/
	
	/**
	 * Supprime la position d'une entite
	 * @param p Position de l'entite
	 * @param e Reference vers le type d'entite a supprimer
	 * @return Retourne le resultat du remove
	 */
	private boolean supprimerPositionEntite(Position p, Entite e){
		Position posExtraite = recupererPosition(e,p);
		return e.getPosition().remove(posExtraite);
	}
	
	/**
	 * Supprime la position d'une entite devant tomber (Roc ou Diamant)
	 * @param p Position de l'entite
	 * @param e Reference vers le type d'entite a supprimer
	 * @return Retourne le resultat du remove
	 */
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
	 * @param Position p Position a tester
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
					supprimerPositionTombeEntite(pos,entiteCarte(p));
				}else{
					System.out.println("___________\n\n"+p+"_____\n_______+tombe pas :"+entiteCarte(p).getPosition().remove(p));
					supprimerPositionEntite(p,entiteCarte(p));
				}
				//si la case a exploser est le joueur, alors c'est perdu.
				if(entiteCarte(p)==joueur){
					//on n'ajoute pas d'explosion quand le joueur perd car sinon,
					//apres le reset il reste un morceau d'explosion
					perdu();
				}else if(entiteCarte(p)==libellule || entiteCarte(p)==amibe){
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
	 * supprimer une position d'un HashSet utilise lors de la suppression.
	 * @param e Reference vers l'entite dont on veut trouver la position
	 * @param p Position a tester
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
				return sortie;
			}
		}
		return null;
	}
	
	/**
	 * Teste si la case de coordonnees (x,y) existe ou non
	 * @param x Coordonnee en x
	 * @param y Coordonnee en y
	 * @return true si la case existe et false sinon
	 * */
	public boolean estCaseExistante(int x,int y){
		return x>=0 && y >=0 && x < entite.length && y <entite[0].length;
	}
	
	/**
	 * Teste si la case de position p existe ou non
	 * @param p Position a tester
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
	 * @param p La position ou l'on veut l'entite
	 * @return L'entite que l'on cherche
	 */
	public Entite entiteCarte(Position p){
		if(p == null){
			throw new NullPointerException("impossible de tester une case null");
		}
		return entite[p.getX()][p.getY()];
	}

	/**
	 * Fait tomber une entite pouvant tomber (Roc ou Diamant)
	 * @param e L'entite devant tomber
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
				fenetre.repaint();
				synchronized(fenetre.getMoteur().thread){
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
	 * @param p Position a tester
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
	 * Construit la carte du jeu avec tout ses objets a partir d'un tableau a 2 dimensions de char
	 * obtenu en lisant le fichier contenant tous les niveaux. Utilise des reference vers les entites
	 * creee plus haut
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
				else{
					nouveauTab[i][j].getPosition().add( new Position(i,j));
				}
				
				entite=nouveauTab;//memorise le nouveau tableau
			}
		}
	}


	/**
	 * Methode de debug renvoyant la map d'entite (leur apparence)
	 * @return Retourne une chaine de caractere representant la map d'entite
	 */
	public String afficherMapEntite(){ 
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
	 * Ajoute la porte a la map d'entite si on a recuperer le nombre adequat de diamant
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
	 * Reset la map : 
	 * Vide les positions, remet le score, le nombre de diamant recupere a 0.
	 * Reconstruit la meme map qu'au depart et relance la partie
	 * */
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
		chemin_direct.clear();
		
		score=0;
		nbDiamantRecolte = 0;
		nbTour = 0;
		chemin_direct.clear();
		
		construireMapEntite();
		System.out.println(afficherMapEntite());
	}
	
	/**
	 * Change la map par celle conrespondant au numero passe en parametre.
	 * Vide les positions des entites, remet le score et le nombre de diamant recupere a 0
	 * Lance la partie sur cette nouvelle map
	 * @param n Numero de la nouvelle map
	 */
	public void changerMap(int n){
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
		
		chemin_direct.clear();

		score=0;
		nbDiamantRecolte = 0;
		nbTour = 0;

		construireMapEntite();
	}

	/**
	 * Deplace le joueur sur la case de coordonnees (x, y)
	 * @param x Coordonnee x de la nouvelle position
	 * @param y Coordonnee y de la nouvelle position
	 * */
	public void deplacerJoueur(int x, int y){
		if(joueur.getPosition().size()!=1)
			throw new IllegalArgumentException("Plusieurs positions pour le joueurs");
		if(entite[x][y] instanceof Exit){
			System.out.println("JE GAGNE ???");
				gagner();
		}
		if(!aGagne){
			if(entite[x][y] instanceof Diamant){
				gagnerPoints();
			}
			Iterator<Position> it = joueur.getPosition().iterator();
			Position p = it.next(); //pos actuelle du joueur
			//rajoute l'emplacement du joueur dans l'ens de pos d'espace
			ajouterUnEspace(p);
			
			Position p1 = new Position(x,y);
			PositionTombe pT = new PositionTombe(x,y);
			joueur.getPosition().remove(p); //enleve la pos actuelle du joueur
			if(entite[x][y] instanceof Diamant){
				diamant.getPositionTombe().remove(pT);
			}
			else{
				entite[x][y].getPosition().remove(p1);
			}
			//System.out.println("deplacerJoueur() : ");
			entite[x][y] = joueur; //fait pointer sur la nouvelle pos
			entite[x][y].getPosition().add(p1); //rajoute l'emplacement du joueur dans l'ens de pos du joueur
		}
		

	}

	/**
	 * Ajoute un espace a la position indiquee
	 * @param p Position ou il faut ajouter un espace
	 * */
	public void ajouterUnEspace(Position p) {
		if(p == null){
			throw new NullPointerException("Impossible d'ajouter un espace a une position null");
		}
		entite[p.getX()][p.getY()] = espace;
		espace.getPosition().add(p);
	}

	/**
	 * Teste si le deplacement est possible ou non en ayant appuyer sur une certaine touche.
	 * @param t1 utilise par le joueur
	 * @return true si le deplacement est possible et false sinon.
	 * */
	public boolean deplacementPossible(Touche t1) {
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
	 * @param posX Coordonne en x de la case a tester
	 * @param posY Coordonne en y de la case a tester
	 * @return true si la case est libre et false sinon.
	 * */
	public boolean caseLibre(int posX, int posY){
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
			return true;
		}
		return false;
	}


	/**
	 * Permet de recuperer une touche entre au clavier
	 * @return Retourne la touche
	 */
	private char recupererTouche() {
		synchronized(fenetre.getMoteur().thread){
			try {
				thread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return touche;
	}
	
	/**
	 * Fait gagner des points au joueur quand il prend des diamants en incrementant le score.
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
	 * Memorise une touche et l'ajoute au chemin actuel
	 * @param touche Touche a memoriser
	 */
	public void memorizePath(char touche){
		//deplacements est une liste de touche qui mémorise les déplacements du joueur
		if(intelligence != -2){
			deplacements.add(touche);
		}
	}
	
	/**
	 * Vide le chemin actuel
	 */
	public void resetPath(){
		deplacements.clear();
	}
	
	/**
	 * Sauvegarde le chemin via l'enregistreur
	 */
	@SuppressWarnings("unchecked") //Il faut cast en ArrayList car la methode clone retourne un type object
	public void exportPath(){
		
		if(intelligence == 2){
			if (aGagne) iaGen.updateThisRockford(aGagne, nbDiamantRecolte, nbTour,(ArrayList<Character>) deplacements.clone());
			else if (aPerdu) iaGen.updateThisRockford(aGagne, nbDiamantRecolte, nbTour,(ArrayList<Character>) deplacements.clone());
			else iaGen.updateThisRockford(aGagne, nbDiamantRecolte, nbTour,(ArrayList<Character>) deplacements.clone());
			
			resetPath();
			iaGen.nextRockford();
		} else
		
		if (aGagne){
			Enregistreur.sauvegarderPartie(intelligence,numMap+nomFichier,deplacements);
			if (intelligence >= 2) Enregistreur.ecraserSolution(intelligence,numMap+nomFichier,deplacements);
			resetPath();
		} else if (aPerdu){
			Enregistreur.sauvegarderPartie(intelligence,numMap+nomFichier,deplacements);
			resetPath();
		}
		
		else if (true){	//Onglet EnregistrerPartie
			Enregistreur.sauvegarderPartie(intelligence,numMap+nomFichier,deplacements);
		}
		
	}
	

/*	public void enregistrer(){
		Enregistreur.sauvegarderPartie(intelligence,numMap+nomFichier,deplacements);
		if (intelligence >= 2) Enregistreur.ecraserSolution(intelligence,numMap+nomFichier,deplacements);
	}*/
	
	/**
	 * Fait gagner le joueur. Fait aussi changer de carte.
	 * */
	public void gagner(){
	aGagne = true;
	if(intelligence == 4){
		iaParfaiteAGagne = true;
	}
	if(intelligence == 3){
		intelligence = Intelligence.ME.get();
	}
	exportPath();
	if(numMap == map.getNbMap() && intelligence != 2 && intelligence != 4){
		System.out.println("ca a fonctionne");
		fenetre.afficherMessageVictoire();
		changerMap(1);
		synchronized(thread){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException exp) {
				exp.printStackTrace();
			}
		}
		fenetre.effacerMessageVictoire();
	}
	else{
		if (intelligence != 2 )changerMap(++numMap);
		else resetMap();
	}
		
}

	/**
	 * Fait perdre le joueur. Fait aussi reset la carte.
	 */
	public void perdu() {
		fenetre.ecrireMessage("Vous etes mort !", 1);
		aPerdu = true;
		if(intelligence == 3){
			intelligence = Intelligence.ME.get();
		}
		exportPath();
		resetMap();
		
	}

	
	/*private void afficherJeu(Map m) {
		System.out.println(m.toString());
	}*/
	
	/**
	 * Permet de deplacer les ennemis de la map
	 */
	private void deplacerEnnemis(){
		luciole.deplacer(entite);
		libellule.deplacer(entite);
		amibe.deplacer(entite);	
	}

	public Entite copyEntite(Entite type){
		//System.out.println("copyEntite apparence : "+type.getApparence());
		switch(type.getApparence()){
		case 'R' : return joueur.copy();
		case 'X' : return exit.copy();
		case ' ' : return espace.copy();
		case '.' : return poussiere.copy();
		case 'r' : return roc.copy();
		case 'd' : return diamant.copy();
		case 'w' : return mur.copy();
		case 'W' : return murTitane.copy();
		case 'M' : return murMagique.copy();
		case 'a' : return amibe.copy();
		case 'E' : return explosion.copy();
		case 'F' :
		case 'o' :
		case 'O' :
		case 'q' :
		case 'Q' :return luciole.copy();
		case 'b' :
		case 'B' :
		case 'c' :
		case 'C' :return libellule.copy();
		default : 
			return null;
		}
	}
	public void chargerDonnees(DataRobil data){
		joueur = data.joueur.copy();
		luciole = data.luciole.copy();
		libellule = data.libellule.copy();
		amibe = data.amibe.copy();
		explosion = data.explosion.copy();
		espace = data.espace.copy();
		mur = data.mur.copy();
		murTitane = data.murTitane.copy();
		murMagique = data.murMagique.copy();
		poussiere = data.poussiere.copy();
		diamant = data.diamant.copy();
		
		nbDiamantRecolte = data.nbDiamants;
		numMap = data.numMap;
		
		if(isPorteAffiche()){
			posPorte = new Position(data.posPorte.getX(), data.posPorte.getY());
		}
		for(int i=0;i<data.entite.length;i++){//copie la map d'entite
			for(int j=0;j<data.entite[0].length;j++){
				entite[i][j]=data.entite[i][j].copy();
			}
		}
	}

	public Entite[][] copieEntite() {
		Entite[][] retour = new Entite[entite.length][entite[0].length];
		for(int i=0;i<entite.length;i++){
			for(int j=0;j<entite[0].length;j++){
				retour[i][j]=(Entite) entite[i][j].copy();
			}
		}
		return retour;
	}
	
	/**
	 * Getter du nombre de diamants
	 * @return Retourne le nombre de diamants
	 */
	public int getNombreDiamants(){
		return nbDiamantRecolte;
	}

	/**
	 * Getter du score
	 * @return Retourne le score
	 */
	public int getScore(){
		return score;
	}

	/**
	 * Getter du nombre de tour
	 * @return Retourne le nombre de tour
	 */
	public int getNombreTour(){
		return nbTour;
	}

	/**
	 * Teste si l'Ia passe en parametre correspond a celle utilise actuellement
	 * @param ia IA a tester
	 * @return Retourne true si l'IA correspond a intelligence sinon false
	 */
	public boolean estIA(Intelligence ia){
		return ia.get() == intelligence;
	}

	/**
	 * Getter du tableau d'entite entite
	 * @return Retourne entite
	 */
	public Entite[][] getEntite(){
		return entite;
	}
	
	/**
	 * Getter du nombre de map dans le fichier de la map utilise
	 * @return Retourne le nombre de map
	 */
	public int getNbMap(){
		return map.getNbMap();
	}

	/*public boolean isEnJeu() {
		return enJeu;
	}

	public void setEnJeu(boolean enJeu) {
		this.enJeu = enJeu;
	}*/

	/**
	 * Teste si le joueur a gagne
	 * @return Retourne aGagne
	 */
	public boolean isaGagne() {
		return aGagne;
	}

	/**
	 * Teste si le joueur a perdu
	 * @return Retourne aPerdu
	 */
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

	/**
	 * Getter de la hauteur de la map
	 * @return Retourne la hauteur de la map
	 */
	public int getHauteurMap() {
		return map.getHauteur();
	}
	
	/**
	 * Getter de la largeur de la map
	 * @return Retourne la largeur de la map
	 */
	public int getLargeurMap() {
		return map.getLargeur();
	}
	
	/**
	 * Getter du nom du fichier ou se trouve la map
	 * @return Retourne le nom du fichier
	 */
	public String getNomFichier(){
		return nomFichier;
	}
	
	/**
	 * Getter du numero de la map utilisee
	 * @return Retourne le numero de la map
	 */
	public int getNumMap(){
		return numMap;
	}

	/**
	 * Getter de isAffiche
	 * @return true si la porte est affiche sinon false
	 */
	public boolean isPorteAffiche() {
		return porteAffiche;
	}

	/**
	 * Getter de l'ensemble des positions du diamant
	 * @return Retourne le set de positionTombe de diamant
	 */
	public Set<PositionTombe> getPositionDiamant() {
		return diamant.getPositionTombe();
	}
	
	/**
	 * Getter de l'ensemble des positions (ou de la position normalement) du joueur
	 * @return Retourne le set de position de joueur
	 */
	public Set<Position> getPositionJoueur() {
		return joueur.getPosition();
	}
	
	/*public int getDiamondValue(){
		return map.getDiamondVal();
	}
	
	public int getScoreAAvoir(){
		return map.getDiamondRec()*map.getDiamondVal();
	}*/
	
	/**
	 * Getter du nombre de diamant requis pour gagner
	 * @return Retourne le nombre de diamant requis pour gagner
	 */
	public int getNbDiamandRec(){
		return map.getDiamondRec();
	}
	
	/**
	 * Getter de la position de la porte
	 * @return Retourne la position de la porte
	 */
	public Position getPosPorte(){
		return posPorte;	
	}

	/**
	 * Getter du nombre de diamant deja recolte
	 * @return Retourne le nombre de diamant deja recolte
	 */
	public int getNbDiamantRecolte() {
		return nbDiamantRecolte;
	}

	public int getIntelligence() {
		return intelligence;
	}
	
	/**
	 * Fait que l'IA va s'arreter dans son execution jusqu' a la prochaine repriseIA()
	 * */
	public void pauseIA(){
		fenetre.ecrireMessage("Jeu en Pause", 2);
		enPause=true;
	}
	
	/**
	 * Fait reprendre l'IA qui a ete stoppee par pauseIA()
	 * */
	public void repriseIA(){
		fenetre.ecrireMessage("Jeu en Fonctionnement", 10);
		enPause=false;
	}
	
	/**
	 * Verifie si le jeuest en pause ou non
	 * @return true si le jeu est en pause et false sinon
	 * */
	public boolean enPause(){
		return enPause;
	}

public IA_Random getIaRandom() {
		return iaRandom;
	}

	public IA_Genetique getIaGen() {
		return iaGen;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public Espace getEspace() {
		return espace;
	}

	public Poussiere getPoussiere() {
		return poussiere;
	}

	public Roc getRoc() {
		return roc;
	}

	public Diamant getDiamant() {
		return diamant;
	}

	public MurBasique getMur() {
		return mur;
	}

	public MurTitane getMurTitane() {
		return murTitane;
	}

	public MurMagique getMurMagique() {
		return murMagique;
	}

	public Exit getExit() {
		return exit;
	}

	public Amibe getAmibe() {
		return amibe;
	}

	public Luciole getLuciole() {
		return luciole;
	}

	public Libellule getLibellule() {
		return libellule;
	}

	public Explosion getExplosion() {
		return explosion;
	}
	
	public boolean getMODE_DEBUG_TOMBER() {
		// TODO Auto-generated method stub
		return MODE_DEBUG_TOMBER;
	}
	
	public boolean getMODE_DEBUG_LIBELLULE() {
		// TODO Auto-generated method stub
		return MODE_DEBUG_LIBELLULE;
	}
	
	public boolean getMODE_DEBUG_LUCIOLE() {
		// TODO Auto-generated method stub
		return MODE_DEBUG_LUCIOLE;
	}

	public ArrayList<Character> getDeplacements() {
		return deplacements;
	}

	public boolean isIAParfaiteAGagne() {
		return iaParfaiteAGagne;
	}

}
