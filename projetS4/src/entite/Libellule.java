package entite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import moteurJeu.MoteurJeu;
import moteurJeu.MoteurJeu.Touche;

public class Libellule extends Entite implements Deplacable, Disparaitre {
	
	private Touche direction=Touche.TOUCHE_DROITE;
	private boolean immobile=false;
	
	public Libellule() {
		this.apparence = 'B';
		traversable = true;
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
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case a droite de la luciole.
	 * relativement a son orientation actuelle.
	 * @return Touche direction
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
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Retourne la direction qu'il faut suivre pour trouver la case derriere la luciole.
	 * relativement a son orientation actuelle.
	 * @return Touche direction
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
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Fait tourner la luciole en fonction des murs qui entourent sa position.
	 * @param Entite[][] carte, int x,int y
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
			throw new IllegalStateException("La direction de la luciole ne peut prendre une autre valeur.");
		}
	}
	
	/**
	 * Effectue le deplacement de toutes les lucioles du plateau.
	 * @param Entite[][] carte
	 * @return boolean enVie : renvoie true.
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
		System.out.println("les lucioles se deplacent. Il y a "+this.getPosition().size()+" lucioles.");
		
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
		if(nouvelleDirection!=Touche.TOUCHE_IMMOBILE){
			if(estCoin(carte,p.getX(),p.getY(),nouvelleDirection)
					&&estTraversable(carte,p.getX(),p.getY(),directionGauche(nouvelleDirection))
					&&estTraversable(carte,p.getX(),p.getY(),nouvelleDirection)){
				System.out.println("----------------\n----------------\nestCoin");
				nouvelleDirection=directionGauche(nouvelleDirection);
			}
		}
		System.out.println("Nouvelle direction : _______="+nouvelleDirection);
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
			//nouvelle position.
			Position pPlusUn = new Position(x,y);
			System.out.println("ancienne position de la luciole : posX="+p.getX()+
					" posY="+p.getY()+
					"\nnouvelle position : x="+pPlusUn.getX()+
					" y="+pPlusUn.getY());
					
			//deplacement
			carte[p.getX()][p.getY()] = MoteurJeu.espace;
			MoteurJeu.espace.getPosition().add(p); //rajoute l'emplacement de la libellule
					
			this.getPosition().remove(p); //enleve la pos actuelle de this
			carte[x][y] = this; //fait pointer sur la nouvelle pos
			carte[x][y].getPosition().add(new Position(x,y,nouvelleDirection)); //rajoute l'emplacement de this dans son ensemble de position.
			System.out.println("changement position luciole : "+this.getPosition().size());
		}
		return true;
	}


	@Override
	public void disparait() {
		// TODO Auto-generated method stub
		
	}
}
