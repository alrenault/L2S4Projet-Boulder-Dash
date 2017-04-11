package projetS4.entite;

public class Luciole extends Entite implements Deplacable, Disparaitre {
	
	public Luciole() {
		this.apparence = 'F';
		traversable = true;
		ennemi=true;
	}

	
	public boolean deplacer() {
		// TODO Auto-generated method stub
		return false;
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
		
		
		-->
		
		
		
		
		
		
		*/
	}


	@Override
	public boolean deplacer(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
