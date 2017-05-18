package ia;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe creant un Rockford
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Rockford implements Serializable, Comparable<Rockford> {
	
	/**
	 * Variable pour serialiser
	 */
	private static final long serialVersionUID = -488567652550232628L;

	/**
	 * Generateur aleatoire
	 */
	private Random random;
	
	/**
	 * Iterateur pour parcourir une liste
	 */
	private int itList;
	
	/**
	 * Nombre de pas du Rockford
	 */
	private int nbPas;
	
	/**
	 * Nombre de diamant recoltes par le Rockford
	 */
	private int nbDiam;
	
	/**
	 * Represente si le Rockford a gagne ou non
	 */
	private boolean victorious;
	
	/**
	 * Liste de touche
	 */
	private ArrayList<Character> directionList = new ArrayList<Character>();
	
	/**
	 * Tableau des directions possibles
	 */
	private char oneDirection[] = new char[]
			{
				KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT,
				KeyEvent.VK_DOWN,
				KeyEvent.VK_UP,
				KeyEvent.VK_0
			};
	

	/**
	 * Constructeur de la classe Rockford
	 * @param n Nombre de pas maximum
	 */
	public Rockford(int n) {
		// TODO Auto-generated constructor stub
		random = new Random();
		itList = 0 ;
		
		// On met le score au max pour dire que RockFord n'a pas 
		// réussi à se frayer un chemin vers la sortie
		
		nbPas = n ;
		nbDiam = 0 ;
		victorious = false ;
		
		
		//On charge le tableau de position
		
		for (int i = 0 ; i < n ; i++){
			directionList.add(randomTouche());
		}
		
		
		
		//score = 2n --- n pas
		//directions
	}
	
	/**
	 * Constructeur de la classe Rockford
	 */
	public Rockford(){
		this(1);
	}
	
	/**
	 * Constructeur de la classe Rockford a partir de deux listes de touches
	 * @param directionList1 Premiere liste de touches
	 * @param directionList2 Deuxieme liste de touches
	 * @param mutation Nombre de mutations 
	 */
	public Rockford(ArrayList<Character> directionList1,ArrayList<Character> directionList2, int mutation){
		random = new Random();
		itList = 0 ;
		
		nbDiam = 0 ;
		victorious = false ;
		
		directionList = new ArrayList<Character>();
		
		int size = directionList1.size();
		if (directionList2.size()<size) size = directionList2.size();
		
		//nbPas = size;
		
		boolean partition = random.nextBoolean();
		int lim = random.nextInt(size);
		//System.out.println("size : "+ size +" ----- lim1 : "+lim);
		
		directionList.addAll(directionList1.subList(0, lim));
		
		if (partition){	//Liste en 3 parties
			directionList.addAll(directionList2.subList(lim, lim += random.nextInt(size-lim)));
			//System.out.println(";  lim2 : "+lim);
			directionList.addAll(directionList1.subList(lim,directionList1.size()));
			
		} else directionList.addAll(directionList2.subList(lim, directionList2.size()));
		
		if (mutation > 0){
			for (int i = 0 ; i<mutation ; i++){
				int index = random.nextInt(size);
				char touche;
				do{
					touche = randomTouche() ;
				} while(touche == directionList.get(index));
				directionList.set(index, touche);
			}
			
		}
		nbPas = directionList.size();
		
		
	}
	
	/**
	 * Constructeur de la classe Rockford pour pouvoir les trier dans une nouvelle population apres l'evaluation
	 * @param victoire Si le Rockford a gagne
	 * @param diamants Le nombre de diamants qu'il a recolte
	 * @param pas Le nombre de pas effectue
	 * @param listePas La liste des touches suivie
	 */
	public Rockford (boolean victoire, int diamants, int pas, ArrayList<Character> listePas){
		random = new Random();
		itList = 0 ;
		
		victorious = victoire ;
		nbDiam = diamants ;
		nbPas = pas ;
		directionList=listePas;
	}
	
	/**
	 * Renvoie une touche aleatoire
	 * @return Retourne une touche aleatoire
	 */
	public char randomTouche(){
		
		// Generate un int borné par les 5 choix possibles aléatoirement
		int i = random.nextInt(5);
		
		// Cet int correspond à un des indices du tableau contenant les directions
		return oneDirection[i];
	}

	
	public static int iNew = 0;
	public String toString() {
		iNew++;
		return "Rockford : "+iNew+" 	Victorieux : "+victorious+" ; Nombre de Diamants : " + nbDiam + " ; Nombre de Pas : "+nbPas+" \t\n"+directionList.toString() + "\n";
	}
	

	
	/**
	 * Getter de moving
	 * @return Retourne true si le Rockford bouge sinon false
	 */
	public boolean isMoving(){
		return directionList.size()>itList;
	}

	/**
	 * Getter de la prochaine touche
	 * @return Retourne la prochaine touche
	 */
	public char nextPosition() {
		return directionList.get(itList++);
	}
	
	/**
	 * Reinitialise l'iterateur pour parcourir une liste
	 */
	public void resetItList(){
		itList=0;
	}

	/**
	 * Getter de la liste de touche
	 * @return Retourne la liste de touche
	 */
	public ArrayList<Character> getDirectionList() {
		return directionList;
	}


	/**
	 * Getter du nombre de pas
	 * @return Retourne le nombre de pas
	 */
	public int getNbPas() {
		return nbPas;
	}

	/**
	 * Getter du nombre de diamant recupere
	 * @return Retourne le nombre de diamant recupere
	 */
	public int getNbDiam() {
		return nbDiam;
	}

	/**
	 * Teste si le Rockford a gagne
	 * @return Retourne true si le Rockford a gagne, sinon false
	 */
	public boolean isVictorious() {
		return victorious;
	}
	
	@Override
	public int compareTo(Rockford j) {
		
		if (victorious && !j.isVictorious()) return -1;
		else if (!victorious && j.isVictorious()) return 1 ;
		else{
			int diffDiamant = nbDiam-j.getNbDiam();
			
			if (diffDiamant>0) return -1 ;
			else if (diffDiamant<0) return 1 ;
			
			else {
				int diffPas = nbPas-j.getNbPas();
				
				if (diffPas<0) return -1 ;
				else if (diffPas>0) return 1 ;
				else return 0 ;
			}
		}
		
	}
}
