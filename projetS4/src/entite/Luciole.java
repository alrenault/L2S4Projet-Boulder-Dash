package entite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moteurJeu.MoteurJeu;
import moteurJeu.Touche;

/**
 * Classe construisant une luciole
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Luciole extends Entite implements Deplacable, Ennemi {
	
	/**
	 * Reference vers le moteur de jeu
	 */
	private MoteurJeu moteur;
	/**
	 * Booleen pour savoir si la luciole est immobile
	 */
	private boolean immobile = false; 
	
	/**
	 * Constructeur de la classe Luciole
	 * @param moteur Reference vers le moteur de jeu
	 */
	public Luciole(MoteurJeu moteur) {
		this.moteur = moteur;
		this.apparence = 'F';
		traversable = true;;
	}
	
	/**
	 * Constructeur de la copie de la Luciole
	 * @param moteur Reference vers le moteur de jeu
	 * @param position L'ensemble de position de la luciole
	 */
	public Luciole(MoteurJeu moteur, Set<Position> position) {
		this(moteur);
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree la copie de la luciole
	 * @return Retourne la copie de la luciole
	 */
	public Luciole copy(){
		return new Luciole(moteur, position);
	}
	
	/**
	 * Verifie si la case indiquee peut etre traversee relativement a la direction de la luciole.
	 * @param carte La map sur laqulle se trouve la luciole
	 * @param x La coordonnee en x sur laquelle se trouve la luciole
	 * @param y La coordonnee en y sur laquelle se trouve la luciole
	 * @param direction La direction dans laquelle se dirige la luciole
	 * @return Retourne estTraversable : true si la case est traversable et false sinon.
	 * */
	private boolean estTraversable(Entite[][] carte, int x, int y, Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return carte[x][y+1] instanceof Espace || carte[x][y+1] instanceof Joueur;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return carte[x-1][y] instanceof Espace || carte[x-1][y] instanceof Joueur;
		}else if(direction==Touche.TOUCHE_DROITE){
			return carte[x+1][y] instanceof Espace || carte[x+1][y] instanceof Joueur;
		}else if(direction==Touche.TOUCHE_HAUT){
			return carte[x][y-1] instanceof Espace || carte[x][y-1] instanceof Joueur;
		}
		return false;
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case a gauche de la luciole.
	 * relativement a son orientation actuelle.
	 * @param direction La direction de la luciole
	 * @return Retourne une touche representant la direction
	 * */
	public Touche directionGauche(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_GAUCHE;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_HAUT;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_BAS;
		}else{
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case a droite de la luciole.
	 * relativement a son orientation actuelle.
	 * @param direction La direction de la luciole
	 * @return Retourne une touche representant la direction
	 * */
	public Touche directionDroite(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_BAS;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_GAUCHE;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_HAUT;
		}else{
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case derriere la luciole.
	 * relativement a son orientation actuelle.
	 * @param direction La direction de la luciole
	 * @return Retourne une touche representant la direction
	 * */
	public Touche directionDerriere(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_HAUT;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_BAS;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_GAUCHE;
		}else{
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Fait tourner la luciole en fonction des murs qui entourent sa position.
	 * @param carte La map sur laquelle se trouve la luciole
	 * @param x Coordonnee en x de la future position
	 * @param y Coordonnee en y de la future position
	 * @param direction La direction de la luciole
	 * @return Retourne la touche correspondante au mouvement voulue
	 * */
	public Touche tourner(Entite[][] carte, int x,int y, Touche direction){
		immobile=false;
		//si il y a un mur devant, change d'optique, sinon avance.
		if(!estTraversable(carte,x,y, direction)){
			//s'il y a un mur a gauche, change d'optique, sinon tourne a gauche.
			if(!estTraversable(carte,x,y, directionGauche(direction))){
				//s'il y a un mur a droite, change d'optique, sinon tourne a gauche.
				if(!estTraversable(carte,x,y,directionDroite(direction))){
					//s'il y a un obstacle derriere reste immobile, sinon recule.
					if(!estTraversable(carte,x,y, directionDerriere(direction))){
						immobile=true;
					}else{
						//Fait demi-tour
						return directionDerriere(direction);
					}
				}else{
					//Tourne a droite
					return directionDroite(direction);
				}
			}else{
				//Tourne a gauche
				return directionGauche(direction);
			}
		}
		return direction;
	}
	
	/**
	 * Effectue le deplacement de toutes les lucioles du plateau.
	 * @param carte La map sur laquelle se trouve la luciole
	 * @return Renvoie false si le joueur a perdu a ce tour pour eviter le deplacement des autres lucioles, sinon true
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
		//copie de l'ensemble des lucioles
		Set<Position> ensemble=new HashSet<Position>();
		ensemble.addAll(this.getPosition());
		Iterator<Position> it = ensemble.iterator();
		while(it.hasNext()){
			//pour le debugage de luciole
			if(moteur.MODE_DEBUG_LUCIOLE){
				System.out.println("ABougerLuciole : "+toString());
				synchronized(moteur.getFenetre().getMoteur().thread){
					try {
						moteur.thread.wait();
					} catch (InterruptedException exp) {
						// TODO Auto-generated catch block
						exp.printStackTrace();
					}
				}
				moteur.getFenetre().repaint();
			}
			//position actuelle
			if(!moteur.isaPerdu()){
				Position p = it.next();
				deplacerUneLuciole(carte,p);	
			}
			else 
				return false;

		}
		return true;
	}
	
	
	/**
	 * Effectue le deplacement d'une luciole reperee par son emplacement sur la carte.
	 * @param carte La carte sur laquelle se trouve la luciole
	 * @param p La position ou se trouve la luciole
	 * @return Retourne true
	 * */
	private boolean deplacerUneLuciole(Entite[][] carte, Position p){
		//changer d'orientation
		int x=0,y=0;
		Touche nouvelleDirection=tourner(carte,p.getX(),p.getY(),p.getDirection());
				
		//nouvelles coordonnees relatives a la direction qu'emprunte la luciole.
		if(nouvelleDirection==Touche.TOUCHE_BAS){
			x=p.getX();
			y=p.getY()+1;
		}else if(nouvelleDirection==Touche.TOUCHE_HAUT){
			x=p.getX();
			y=p.getY()-1;
		}else if(nouvelleDirection==Touche.TOUCHE_GAUCHE){
			x=p.getX()-1;
			y=p.getY();
		}else if(nouvelleDirection==Touche.TOUCHE_DROITE){
			x=p.getX()+1;
			y=p.getY();
		}else{
			x=p.getX();
			y=p.getY();
		}
		//si la luciole ne reste pas immobile
		if(!immobile){
			//manger le joueur ?
			if(carte[x][y] instanceof Joueur){
				mangerJoueur(carte,x,y);
			}
			if(!moteur.isaPerdu()){
				//deplacement
				moteur.ajouterUnEspace(p); //rajoute l'emplacement de la libellule a Espace
						
				this.getPosition().remove(p); //enleve la pos actuelle de this
				carte[x][y] = this; //fait pointer sur la nouvelle pos
				carte[x][y].getPosition().add(new Position(x,y,nouvelleDirection)); //rajoute l'emplacement de this dans son ensemble de position.
			}
			
		}
		return true;
	}

	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		//System.out.println("La luciole a mange le joueur");
		//moteur.setEnJeu(false);
		moteur.perdu();
		//System.exit(0);
	}
}
