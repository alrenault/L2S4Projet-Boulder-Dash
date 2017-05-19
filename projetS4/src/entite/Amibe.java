package entite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.Set;

import moteurJeu.MoteurJeu;
import moteurJeu.Touche;

/**
 * Classe qui sert a modeliser une Amibe et ses comportements
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */

public class Amibe extends Entite implements Deplacable, Ennemi {
	
	/**
	 * Pourcentage de chance que l'amibe se deplace
	 */
	private int seuil=0;
	
	/**
	 * Moteur de jeu relie a l'amibe
	 */
	private MoteurJeu moteur;
	
	/**
	 * Le constructeur d'Amibe.
	 * @param moteur Reference vers le moteur de jeu
	 * */
	public Amibe(MoteurJeu moteur) {
		this.apparence = 'a';
		traversable = true;
		this.moteur = moteur;
	}

	
	/**
	 * Le constructeur de copie d'Amibe.
	 * @param moteur Reference vers le moteur
	 * @param position L'ensemble des positions de l'amibe
	 * @param seuil Pourcentage de chance que l'amibe se deplace
	 * */
	public Amibe(MoteurJeu moteur, Set<Position> position, int seuil) {
		this(moteur);
		this.position = new HashSet<Position>(position);
		this.seuil=seuil;
	}
	
	/**
	 * Copie l'amibe
	 * @return Retourne l'amibe copie
	 */
	public Amibe copy(){
		return new Amibe(moteur,position,seuil);
	}

	/**
	 * Fait s'agrandir l'Amibe.
	 * @param carte Map d'entite où l'on fait s'agrandir l'amibe
	 * @return Retourne true si l'amibe grandit false sinon
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
		if(carte==null){
			throw new NullPointerException("Carte a null pour deplacer de l'Amibe");
		}
		Position caseMultiplication=null;
		
		//copie de l'ensemble des morceaux d'Amibe
		Set<Position> ensemble=new HashSet<Position>();
		ensemble.addAll(this.getPosition());
		Iterator<Position> it = ensemble.iterator();
		
		//choisit aleatoirement les parties de l'amibe et les multiplie peut-être si possible
		while(true){
			
			if(it.hasNext()){
				Position pos=it.next();
				ArrayList<Position> voisins=trouverVoisins(carte, pos);
				//s'il y a des cases pour faire le traitement
				if(voisins.size() > 0){
					//prend un voisin au hasard et passe a la suite
					caseMultiplication = aleaVoisins(voisins);
					//teste aleatoirement s'il y a deplacement ici
					if(carte[caseMultiplication.getX()][caseMultiplication.getY()] instanceof Joueur){
						mangerJoueur(carte,caseMultiplication.getX(),caseMultiplication.getY());
					}
					
					if(doitDeplacer(caseMultiplication) && !moteur.isaPerdu()){
						//multiplie l'amibe
						position.add(caseMultiplication);
						carte[caseMultiplication.getX()][caseMultiplication.getY()] = this;
						
						
					}
					return true;
				}
			}else{
				//si l'amibe est entouree de murs, ne la deplace pas
				break;
			}
		}		
		return false;
	}

	/**
	 * Exprime si oui ou non l'amibe va se deplacer.
	 * Le programme de deplacement est fait de telle sorte que :
	 * -Au 1er tour, l'Amibe a 0% de chances de se deplacer.
	 * -Au 2ème tour, l'Amibe a 1 chance sur 2 de se delacer
	 * -Les chances montent a 2/3, 3/4, 4/5 etc jusqu'a ce que l'Amibe se deplace
	 * -Une fois le deplacement effectue, les chances de se deplacer reviennent a 0 et on
	 * recommence du debut. 
	 * @param caseMultiplication Case ou l'amibe pourrait se deplacer
	 * @return Retourne true si l'Amibe va se deplacer et false sinon
	 * */
	private boolean doitDeplacer(Position caseMultiplication) {
		if(caseMultiplication==null){
			throw new NullPointerException("Impossible de decider du deplacement sur une case a null");
		}
		
		//gestion d'un seuil pour le deplacement
		int nbreAlea = (int)(Math.random()*seuil);
		seuil+=10;
		
		//A de plus en plus de chance de se deplacer a mesure que le temps passe
		//mais toujours aleatoirement. Une fois fait, remet le seuil a 0 ce qui
		//fait passer A COUP SUR le prochain deplacement.
		if(nbreAlea >= 10){
			seuil=0;
			System.out.println("Doit deplacer");
			return true;
		}
		System.out.println("Doit rester");
		return false;
	}

	/**
	 * Renvoie aleatoirement un des voisins passe en parametre
	 * @param voisins Tableau de voisins
	 * @return Retourne voisinChoisi : le voisin choisi aleatoirement 
	 * */
	private Position aleaVoisins(ArrayList<Position> voisins) {
		if(voisins==null){
			throw new NullPointerException("Voisins a null pour aleaVoisins de l'Amibe");
		}
		if(voisins.size() <= 0){
			throw new NullPointerException("Il n'y a pas de voisins pour un aleatoire");
		}
		
		int nbreAlea = (int) (Math.random()*voisins.size());
		Position retour = voisins.get(nbreAlea);
		
		return retour;
	}

	/**
	 * Trouve les voisins de la Position passee en parametres
	 * @param carte La map sur laquelle se trouve l'amibe
	 * @param pos La position dont on veut les voisins
	 * @return  Retourne voisins : Les voisins valides de la caseVisee
	 * */
	private ArrayList<Position> trouverVoisins(Entite[][] carte, Position pos) {
		if(carte==null){
			throw new NullPointerException("Carte a null pour trouverVoisins de l'Amibe");
		}
		if(pos==null){
			throw new NullPointerException("Pos a null pour trouverVoisins de l'Amibe");
		}
		ArrayList<Position> listeVoisins=new ArrayList<Position>();
		
		//case en dessous
		if(!position.contains(new Position(pos.getX()+1,pos.getY())) &&
			positionsCorrectes(carte,pos.getX()+1,pos.getY()) &&
			estTraversable(carte, pos.getX()+1,pos.getY())){
				listeVoisins.add(new Position(pos.getX()+1,pos.getY()));
		}
		//case au dessus
		if(!position.contains(new Position(pos.getX()-1,pos.getY())) &&
			positionsCorrectes(carte,pos.getX()-1,pos.getY()) &&
			estTraversable(carte, pos.getX()-1,pos.getY())){
				listeVoisins.add(new Position(pos.getX()-1,pos.getY()));
		}
		//case a gauche
		if(!position.contains(new Position(pos.getX(),pos.getY()-1)) &&
			positionsCorrectes(carte,pos.getX(),pos.getY()-1) &&
			estTraversable(carte, pos.getX(),pos.getY()-1)){
				listeVoisins.add(new Position(pos.getX(),pos.getY()-1));
		}
		//case a droite
		if(!position.contains(new Position(pos.getX(),pos.getY()+1)) &&
			positionsCorrectes(carte,pos.getX(),pos.getY()+1) &&
			estTraversable(carte, pos.getX(),pos.getY()+1)){
				listeVoisins.add(new Position(pos.getX(),pos.getY()+1));
		}
		
		return listeVoisins;
	}
	
	/**
	 * Teste si la position est situe sur la map ou non
	 * @param carte La map ou se trouve l'amibe
	 * @param x Coordonnee en x de la position a tester
	 * @param y Coordonnee en y de la position a tester
	 * @return Retourne true si la position se trouve dans la map, false sinon
	 */
	private boolean positionsCorrectes(Entite[][] carte, int x, int y) {
		if(carte==null){
			throw new NullPointerException("Carte a null pour positionsCorrectes de l'Amibe");
		}
		return x>=0 && y>=0 && x<carte.length && y<carte[0].length;
	}


	/**
	 * Verifie si la case indiquee peut être traversee.
	 * @param carte La carte sur laquelle se trouve l'amibe
	 * @param x Coordonnee en x de la position a tester
	 * @param y Coordonnee en y de la position a tester
	 * @return boolean estTraversable : true si la case est traversable et false sinon.
	 * */
	private boolean estTraversable(Entite[][] carte, int x, int y){
		if(carte==null){
			throw new NullPointerException("Carte a null pour estTraversable de l'Amibe");
		}
		return !(carte[x][y] instanceof MurBasique ||
				carte[x][y] instanceof MurMagique ||
				carte[x][y] instanceof MurTitane ||
				carte[x][y] instanceof Diamant ||
				carte[x][y] instanceof Roc);
	}

	/**
	 * Mange le joueur
	 * @param map La map sur laquelle se trouve l'amibe
	 * @param x Coordonnee en x ou se trouve le joueur
	 * @param y Coordonnee en y ou se trouve le joueur
	 * */
	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		moteur.perdu();
	}
	
	
}
