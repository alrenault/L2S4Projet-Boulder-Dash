package map;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Scanner;


/**
 * Classe lisant un fichier et enregistre une map avec tous ses arguments
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 * @author MARIN Nicolas
 * 
 */

public class Map{
	
	/**
	 * Le chemin du fichier contenant la map
	 */
	private Path fichier;
	
	/**
	 * Contenu du fichier
	 */
	private String contenu;
	
	/**
	 * Nombre de map contenu dans le fichier
	 */
	private int nbMap = 0; //nombre de map
	
	/**
	 * Numéro de la map choisie
	 */
	private int numMap;
	
	/**
	 * Hauteur de la map choisie
	 */
	private int hauteur;
	
	/**
	 * Largeur de la map choisie
	 */
	private int largeur;
	
	/**
	 * Nom de la map choisie
	 */
	private String name;
	
	/**
	 * Limite de temps de la map choisie
	 */
	private int maptime;
	
	/**
	 * Diamants requis pour gagner sur la map choisie
	 */
	private int diamondRec;
	
	/**
	 * La valeur des diamants sur la map choisie
	 */
	private int diamondVal;
	
	/**
	 * La valeur supplementaire pour les diamants collecte si le nombre de diamant collecte est supperieur a celui requi
	 */
	private int diamondBonus;
	
	/**
	 * Intervalle de temps pour un mouvement de l'amibe de la map choisie
	 */
	private int amoebaT;
	
	/**
	 * 
	 */
	private int magicWallT;
	
	/**
	 * Tableau de caractere representant la map
	 */
	private char[][] tab;
		
	/**
	 * Constructeur de la classe Map
	 * @param numMap Numero de la map choisie
	 * @param chemin Chemin du fichier où se trouve la map
	 */
	public Map(int numMap, String chemin){
		if(!Files.exists(Paths.get(chemin)))
			throw new IllegalArgumentException("Fichier non trouve");
		fichier = Paths.get(chemin);
		try{
			contenu = readFile(fichier, Charset.defaultCharset());
		}
		catch(IOException e){
			System.out.println("Fichier non trouve");
		}
		this.numMap = numMap;
		name = "Cave "+numMap;
		nbMap();
		findMap();
		//placerJoueur();
	}
	
	/**
	 * Permet de lire un fichier suivant un certain encodage
	 * @param path Chemin du fichier
	 * @param encoding Encodage choisi
	 * @return Retourne le contenu du fichier sous forme de chaine de caracteres
	 * @throws IOException
	 */
	static String readFile(Path path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	}
	
	/**
	 * Renvoie les proprietes d'un fichier bdcff
	 * @param chemin Le chemin du fichier dont on veut les proprietes
	 * @return Retourne sous forme de chaine de caractere les proprietes
	 */
	public static String proprieties(String chemin){
		Path f;
		String proprieties = "";
		String contenu = "";
		
		if(!Files.exists(Paths.get(chemin)))
			throw new IllegalArgumentException("Fichier non trouvé");
		f = Paths.get(chemin);
		try{
			proprieties = readFile(f, Charset.defaultCharset());
		}
		catch(IOException e){
			System.out.println("Fichier non trouvé");
		}
		
		Scanner sc = new Scanner(proprieties);
		String ligne = "";
		while(sc.hasNext()){
			ligne = sc.nextLine();
			if(ligne.equals("[game]")){
				break;
			}
		}
		while(sc.hasNext()){
			ligne = sc.nextLine();
			if(ligne.equals("[cave]")){
				break;
			}
			contenu += ligne + "\n";
		}
		
		sc.close();
		return contenu;
	}
	
	/**
	 * Compte le nombre de map dans le fichier et change nbMap en consequence
	 */
	public void nbMap(){
		Scanner sc = new Scanner(contenu);
		String ligne ="";
		while(sc.hasNext()){
			ligne = sc.next();
			if(ligne.equals("[map]")){
				nbMap++;
			}
		}
		sc.close();
	}
	
	
	/**
	 * Compte le nombre de ligne d'une map dans un fichier
	 * @param map String representant la map et ses attributs
	 * @return Retourne le nombre de ligne de la map choisie
	 */
	public int nbLigne(String map){
		Scanner scanner = new Scanner(map);
		Scanner sc = scanner.useDelimiter("\n"); //en deux étapes pour pouvoir fermer scanner
		String ligne ="";
		int i = 0;
		if(sc.hasNext()){
			ligne = sc.next();
			while(!ligne.equals("[map]")){
				if(sc.hasNext())
					ligne = sc.next();
			}
			while(!ligne.equals("[/map]")){
				i++;
				if(sc.hasNext())
					ligne = sc.next();
			}
		}
		scanner.close();
		return i-1;
		
	}
	
	
	/**
	 * Trouve la map dans le fichier et l'enregistre ainsi que ses arguments.
	 */
	public void findMap(){
		String map = "";
		Scanner sc;
		String ligne ="";
		sc = new Scanner(contenu);
		while(sc.hasNextLine()){
			ligne = sc.nextLine();
			if(ligne.equals("Name="+name)){
				ligne=sc.nextLine();
				while(!ligne.equals("[/cave]") && sc.hasNextLine()){
					map += ligne+"\n";
					ligne = sc.nextLine();
				}
				map+="[/cave]";
			}
		}
		sc.close();	
		
		Scanner scanner = new Scanner (map);
		Scanner sc1 = scanner.useDelimiter("=|\n");
		boolean quit = false;
		while(sc1.hasNext() && !quit){
			ligne = sc1.next();
			switch(ligne){
				case "CaveDelay":
				{
					if(sc1.hasNext()){
						ligne = sc1.next();
					}
					break;
				}
				case "CaveTime":
				{
					if(sc1.hasNext())
						ligne = sc1.next();
					maptime = Integer.parseInt(ligne);
					break;
				}
				case "DiamondsRequired":
				{
					if(sc1.hasNext())
						ligne = sc1.next();
					diamondRec = Integer.parseInt(ligne);
					break;
				}
				case "DiamondValue":
				{
					if(sc1.hasNext())
						ligne = sc1.next();
					String[] diamond = ligne.split("\\s+");
					diamondVal = Integer.parseInt(diamond[0]);
					diamondBonus = Integer.parseInt(diamond[1]);
					//System.out.println(diamondVal);
					//System.out.println(diamondBonus);
					break;
				}
				case "AmoebaTime":
				{
					if(sc1.hasNext())
						ligne = sc1.next();
					amoebaT = Integer.parseInt(ligne);
					break;
				}
				case "MagicWallTime":
				{
					if(sc1.hasNext())
						ligne = sc1.next();
					magicWallT = Integer.parseInt(ligne);					
					break;
				}
				case "":
				{
					break;
				}
				case"[map]":
				{
					if(sc1.hasNext()){
						ligne = sc1.next();
					}
					hauteur = nbLigne(map);
					char [] ligneCourante = ligne.toCharArray();
				    largeur = ligneCourante.length;
					tab = new char[hauteur][largeur];
					
					for(int i =0;i<hauteur;i++){
						ligneCourante = ligne.toCharArray();
						for(int j = 0;j<largeur;j++){
							tab[i][j] = ligneCourante[j];
							//System.out.print(tab[i][j]);
						}
						//System.out.println();;
						if(sc1.hasNext())
							ligne = sc1.next();
					}
						
						quit = true;
				}
					
				default:
				{
					if(sc1.hasNext()){
						ligne = sc1.next();
					}
				}
			}		
		}
		scanner.close();
	}
	

	/**
	 * Redefinition de la methode toString() pour Map
	 * @return Retourne l'etat de la map via une chaine de caractere
	 */
	public String toString(){
		String s ="";
		s+="Nom : "+name+"s\nLimite de temps : "+maptime+"\n";
		
		for(int i =0;i<hauteur;i++){
			for(int j = 0; j<largeur;j++){
				
				s+=tab[i][j];
			}
			s+="\n";
		}
		
		return s;
	}
	
	/**
	 * Getter du contenu de la map
	 * @return Retourne le contenu de la map
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Getter de la hauteur de la map
	 * @return Retourne la hauteur de la map
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * Getter de la largeur de la map
	 * @return Retourne la largeur de la map
	 */
	public int getLargeur() {
		return largeur;
	}
	
	/**
	 * Getter du char [i][j] du tableau de caractere representant la map
	 * @param i Coordonnee en x du char
	 * @param j Coordonne en y du char
	 * @return Retourne le char en [i][j] du tableau de char
	 */
	public char getTab(int i, int j){
		return tab[i][j];
	}
	
	
/*	public void setTab(int i, int j, char val){
		tab[i][j] = val;
	}*/

	/**
	 * Getter du nombre requis de diamant pour gagner
	 * @return Retourne le nombre de diamant requis
	 */
	public int getDiamondRec() {
		return diamondRec;
	}

	/**
	 * Getter de la valeur d'un diamant
	 * @return Retourne la valeur d'un diamant
	 */
	public int getDiamondVal() {
		return diamondVal;
	}

	/**
	 * Getter de la valeur bonus d'un diamant
	 * @return Retourne la valeur bonus d'un diamant
	 */
	public int getDiamondBonus() {
		return diamondBonus;
	}

	/**
	 * Getteur du numero de la map choisie
	 * @return Retourne le numero de la map choisie
	 */
	public int getNumMap() {
		return numMap;
	}

/*
	public void setNumMap(int numMap) {
		this.numMap = numMap;
	}*/

	/**
	 * Getter du nombre de map dans le fichier
	 * @return Retourne le nombre de map dans le fichier
	 */
	public int getNbMap() {
		return nbMap;
	}
	
	
	
	
}
