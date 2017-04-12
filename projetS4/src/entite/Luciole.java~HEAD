package entite;

import java.util.Iterator;

import moteurJeu.MoteurJeu;
import moteurJeu.MoteurJeu.Touche;

public class Luciole extends Entite implements Deplacable, Disparaitre, Ennemi {
	
	private Touche direction=Touche.TOUCHE_DROITE;
	
	/**
	 * Le constructeur de la classe.
	 */
	public Luciole() {
		this.apparence = 'F';
		traversable = true;;
	}
	
	/**
	 * Verifie si la case indiquee est un mur.
	 * */
	private boolean estMur(Entite[][] carte, int x, int y){
		if(carte[x][y] instanceof MurBasique||carte[x][y] instanceof MurMagique||
			carte[x][y] instanceof MurTitane){
			return true;
		}
		return false;
	}
	
	/**
	 * fait tourner la luciole en fonction des murs qui encombrent son chemin.
	 * */
	public void tourner(Entite[][] carte, int x,int y){
		if(direction==Touche.TOUCHE_BAS && estMur(carte,x,y+1)){
			direction=Touche.TOUCHE_GAUCHE;
			tourner(carte,x,y);
		}else if(direction==Touche.TOUCHE_HAUT && estMur(carte,x,y-1)){
			direction=Touche.TOUCHE_DROITE;
			tourner(carte,x,y);
		}else if(direction==Touche.TOUCHE_GAUCHE && estMur(carte,x-1,y)){
			direction=Touche.TOUCHE_HAUT;
			tourner(carte,x,y);
		}else if(direction==Touche.TOUCHE_DROITE && estMur(carte,x+1,y)){
			direction=Touche.TOUCHE_BAS;
			tourner(carte,x,y);
		}
	}
	
	/**
	 * Effectue le deplacement de toutes les lucioles du plateau.
	 * */
	@Override
	public boolean deplacer(Entite[][] carte) {
		System.out.println("les lucioles se deplacent. Il y a "+this.getPosition().size()+" lucioles.");
		Iterator<Position> it = this.getPosition().iterator();
			
		while(it.hasNext()){
			//position actuelle
			System.out.println("la boucle de deplacer de luciole s'effectue 1 fois.");
			Position p = it.next();
			deplacerUneLuciole(carte,p);
		}
		return true;
	}
	
	/**
	 * Effectue le deplacement d'une luciole reperee par son emplacement sur la carte.
	 * */
	private boolean deplacerUneLuciole(Entite[][] carte, Position p){
		//changer d'orientation
		int x=0,y=0;
		tourner(carte,p.getX(),p.getY());
				
		//nouvelles coordonnees relatives a la direction qu'emprunte la luciole.
		if(direction==Touche.TOUCHE_BAS){
			x=p.getX();
			y=p.getY()+1;
		}else if(direction==Touche.TOUCHE_HAUT){
			x=p.getX();
			y=p.getY()-1;
		}else if(direction==Touche.TOUCHE_GAUCHE){
			x=p.getX()-1;
			y=p.getY();
		}else if(direction==Touche.TOUCHE_DROITE){
			x=p.getX()+1;
			y=p.getY();
		}else{
			x=p.getX();
			y=p.getY();
		}
				
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
		carte[x][y].getPosition().add(new Position(x,y)); //rajoute l'emplacement de this dans son ensemble de position.
		System.out.println("changement position luciole : "+this.getPosition().size());
		return true;
	}

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

	@Override
	public Touche pattern(){
		return Touche.TOUCHE_DROITE;
	}


	@Override
	public void mangerJoueur() {
		// TODO Auto-generated method stub
		
	}
}
