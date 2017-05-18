package entite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moteurJeu.MoteurJeu;
import moteurJeu.Touche;

/**
 * Classe construisant une livellule
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Libellule extends Entite implements Deplacable, Ennemi {
	
	/**
	 * Booleen pour savoir si la libllule est immobile ou non
	 */
	private boolean immobile=false;
	
	/**
	 * Reference vers le moteur
	 */
	private MoteurJeu moteur;
	
	/**
	 * Constructeur de la Libellule
	 * @param moteur Reference vers le moteur
	 */
	public Libellule(MoteurJeu moteur) {
		this.apparence = 'B';
		traversable = true;
		this.moteur = moteur;
	}

	/**
	 * Constructeur de la copie de la class Libellule
	 * @param moteur Reference vers le moteur
	 * @param position L'ensemble des positions de la libellule
	 */
	public Libellule(MoteurJeu moteur, Set<Position> position) {
		this(moteur);
		this.position = new HashSet<Position>(position);
	}
	
	/**
	 * Cree une copie de la libellule
	 * @return Retourne une copie de la libellule
	 */
	public Libellule copy(){
		return new Libellule(moteur, position);
	}
	
	/**
	 * Verifie si la case indiquee peut etre traversee relativement a la direction de la libellule.
	 * @param carte La map d'entite sur laquelle se trouve la libellule
	 * @param x Coordonnee en x de la position a tester
	 * @param y Coordonnee en y de la position a tester 
	 * @param direction La direction dans laquelle la libellule va aller
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
	 * Retourne la direction qu'il faut suivre pour trouver la case a gauche de la libellule.
	 * relativement a son orientation actuelle.
	 * @return Retourne une touche representant la direction
	 * */
	private Touche directionGauche(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_GAUCHE;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_HAUT;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_BAS;
		}else{
			throw new IllegalStateException("La direction de la libellule ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case a droite de la libellule.
	 * relativement a son orientation actuelle.
	 * @return Retourne une touche representant la direction
	 * */
	private Touche directionDroite(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_BAS;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_GAUCHE;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_HAUT;
		}else{
			throw new IllegalStateException("La direction de la libellule ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case derriere la libellule.
	 * relativement a son orientation actuelle.
	 * @return Retourne une touche representant la direction
	 * */
	private Touche directionDerriere(Touche direction){
		if(direction==Touche.TOUCHE_BAS){
			return Touche.TOUCHE_HAUT;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return Touche.TOUCHE_DROITE;
		}else if(direction==Touche.TOUCHE_HAUT){
			return Touche.TOUCHE_BAS;
		}else if(direction==Touche.TOUCHE_DROITE){
			return Touche.TOUCHE_GAUCHE;
		}else{
			throw new IllegalStateException("La direction de la libellule ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Fait tourner la libellule en fonction des murs qui entourent sa position.
	 * @param carte La map sur laquelle se trouve la libellule
	 * @param x Coordonnee en x de la future position
	 * @param y Coordonnee en y de la future position
	 * @return Retourne la touche correspondante au mouvement voulue
	 * */
	private Touche tourner(Entite[][] carte, int x,int y, Touche direction){
		immobile=false;
		//si il y a un mur devant, change d'optique, sinon avance.
				if(!estTraversable(carte,x,y, direction)){
					//s'il y a un mur a gauche, change d'optique, sinon tourne a gauche.
					if(!estTraversable(carte,x,y, directionDroite(direction))){
						//s'il y a un mur a droite, change d'optique, sinon tourne a gauche.
						if(!estTraversable(carte,x,y,directionDerriere(direction))){
							//s'il y a un obstacle derriere reste immobile, sinon recule.
							if(!estTraversable(carte,x,y, directionGauche(direction))){
								immobile=true;
							}else{
								//Fait demi-tour
								return directionGauche(direction);
							}
						}else{
							//Tourne a gauche
							return directionDerriere(direction);
						}
					}else{
						//Tourne a droite
						return directionDroite(direction);
					}
				}
				return direction;
	}
	
	/**
	 * Teste si une case est un coin
	 * @param carte La map pu se trouve la libellule
	 * @param x Coordonnee en x de la position a tester
	 * @param y Coordonnee en y de la position a tester
	 * @param direction Direction dans laquelle va la libellule
	 * @return Retourne vrai si la case est un coin, sinon false
	 */
	private boolean estCoin(Entite[][] carte,int x,int y,Touche direction){
		
		if(direction==Touche.TOUCHE_HAUT){
			return !(carte[x-1][y-1] instanceof Espace) || !(carte[x-1][y+1] instanceof Joueur);
		}else if(direction==Touche.TOUCHE_DROITE){
			return !(carte[x-1][y+1] instanceof Espace) || !(carte[x-1][y-1] instanceof Joueur);
		}else if(direction==Touche.TOUCHE_BAS){
			return !(carte[x+1][y-1] instanceof Espace) || !(carte[x+1][y-1] instanceof Joueur);
		}else if(direction==Touche.TOUCHE_GAUCHE){
			return !(carte[x+1][y-1] instanceof Espace) || !(carte[x+1][y+1] instanceof Joueur);
		}else{
			throw new IllegalStateException("La direction de la libellule ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Effectue le deplacement de toutes les libellule de la map.
	 * @param carte La map sur laquelle se trouve la libellule
	 * @return Renvoie false si le joueur a perdu a ce tour pour eviter le deplacement des autres libellule, sinon true
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
		
		//copie de l'ensemble des libellules
		Set<Position> ensemble=new HashSet<Position>();
		ensemble.addAll(this.getPosition());
		Iterator<Position> it = ensemble.iterator();
		
		while(it.hasNext()){
			//pour le debugage de libellule
			if(moteur.MODE_DEBUG_LIBELLULE){
				System.out.println("ABougerLibellule : "+toString());
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
				deplacerUneLibellule(carte,p);	
			}
			else 
				return false;
		}
		return true;
	}
	
	
	/**
	 * Effectue le deplacement d'une libellule reperee par son emplacement sur la carte.
	 * @param carte La map sur laquelle se trouve la libellule
	 * @param p La position ou se trouve la libellule
	 * @return Retourne true
	 * */
	private boolean deplacerUneLibellule(Entite[][] carte, Position p){
		//changer d'orientation
		int x=0,y=0;
		Touche nouvelleDirection=tourner(carte,p.getX(),p.getY(),p.getDirection());
		if(nouvelleDirection!=Touche.TOUCHE_IMMOBILE){
			if(estCoin(carte,p.getX(),p.getY(),nouvelleDirection)
					&&estTraversable(carte,p.getX(),p.getY(),directionGauche(nouvelleDirection))
					&&estTraversable(carte,p.getX(),p.getY(),nouvelleDirection)){
				//System.out.println("----------------\n----------------\nestCoin");
				nouvelleDirection=directionGauche(nouvelleDirection);
			}
		}
		//nouvelles coordonnees relatives a la direction qu'emprunte la libellule.
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
		//si la libellule ne reste pas immobile
		if(!immobile){
			if(carte[x][y] instanceof Joueur){
				mangerJoueur(carte,x,y);
			}
			else{
				//deplacement
				moteur.ajouterUnEspace(p); //rajoute l'emplacement de la libellule a Espace
						
				this.getPosition().remove(p); //enleve la pos actuelle de this
				carte[x][y] = this; //fait pointer sur la nouvelle pos
				carte[x][y].getPosition().add(new Position(x,y,nouvelleDirection)); //rajoute l'emplacement de this dans son ensemble de position.
			}
			
		}
		return true;
	}

	/**
	 * Redefinition de la methode toString pour libellule
	 * @return Retourne l'etat de la libellule sous forme d'une chaine de caractere
	 */
	@Override
	public String toString() {
		return "Libellule [ position=" + position+"]";
	}

	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		moteur.perdu();
	}
}
