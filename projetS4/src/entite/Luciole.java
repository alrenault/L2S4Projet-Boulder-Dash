package entite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moteurJeu.MoteurJeu;
import moteurJeu.MoteurJeu.Touche;

public class Luciole extends Entite implements Deplacable, Disparaitre, Ennemi {
	
	private boolean immobile = false; 
	
	/**
	 * Le constructeur de la classe.
	 */
	public Luciole() {
		this.apparence = 'F';
		traversable = true;;
	}
	
	/**
	 * Verifie si la case indiquee peut être traversee relativement a la direction de la luciole.
	 * @param Entite[][] carte, int x, int y, Touche direction
	 * @return boolean estTraversable : true si la case est traversable et false sinon.
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
	 * @return Touche direction
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
	 * @return Touche direction
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
	 * @return Touche direction
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
	 * @param Entite[][] carte, int x,int y
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
	 * @param Entite[][] carte
	 * @return boolean enVie : renvoie true.
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
			
		//copie de l'ensemble des lucioles
		Set<Position> ensemble=new HashSet<Position>();
		ensemble.addAll(this.getPosition());
		Iterator<Position> it = ensemble.iterator();
		
		while(it.hasNext()){
			//position actuelle
			Position p = it.next();
			deplacerUneLuciole(carte,p);
		}
		return true;
	}
	
	
	/**
	 * Effectue le deplacement d'une luciole reperee par son emplacement sur la carte.
	 * @param Entite[][] carte, Position p
	 * @return boolean enVie : renvoie true
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
			
			//deplacement
			carte[p.getX()][p.getY()] = MoteurJeu.espace;
			MoteurJeu.espace.getPosition().add(p); //rajoute l'emplacement de la libellule
					
			this.getPosition().remove(p); //enleve la pos actuelle de this
			carte[x][y] = this; //fait pointer sur la nouvelle pos
			carte[x][y].getPosition().add(new Position(x,y,nouvelleDirection)); //rajoute l'emplacement de this dans son ensemble de position.
		}
		return true;
	}

	/**
	 * Fait disparaitre ?
	 * */
	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}
	
	/*Déplacement :
		Prendre en paramètre la dernière direction pour le prochain déplacement :
		-> Si on allait à droite, on continue à droite si possible
		
		
		Creer 100 tableaux de taille 2 fois le nombre de pas
		-> on les remplit de HAUT,BAS,DROITE...
		-> on détermine le score de chacun des 100 tableaux
		-> on sélectionne les 50 meilleures scores -> on se retrouve avec 50 tableaux
		-> on en rajoute 50 avec les combinaisons entre les 50 meilleurs ancients tableaux
		-> on boucle
		-> on parametre la manière dont on arrete
		-> 1000 iterations
		
	*/
	
	/**
	 * Genere ?
	 * */
	public void generate(){
		
		/*
		 * Evaluation
		 * 
		 * tab[0-100] = int score et tab[2n] de char
		 * 		--> Individu
		 * 
		 * Selection
		 *  	Voir cours
		 *  
		 *  Mutation / Reproduction
		 *  25 par mut - 25 par rep
		 *  
		 *  
		 *  
		
		en face
		-->
		
		
		
		
		
		
		*/
	}

	
	

	/**
	 * Mange le joueur ?
	 * */
	@Override
	public void mangerJoueur(Entite[][] map, int x, int y) {
		map[x][y].getPosition().clear();
		System.out.println("La luciole a mange le joueur");
		//System.exit(0);
	}
}
