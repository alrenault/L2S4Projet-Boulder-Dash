package projetS4.map;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Scanner;

import projetS4.entite.*;
import prototypeProjetS4.Entite;

public class Map{
	private Path fichier;
	private String contenu;
	
	private int numMap;
	private int hauteur;
	private int largeur;
	private String name;
	private int maptime;
	private int diamondRec;
	private int diamondVal;
	private int diamondBonus;
	private int amoebaT;
	private int magicWallT;
	
	private char[][] tab;
	private Entite[][] entite;
	
	public Map(int numMap, String chemin){
		if(!Files.exists(Paths.get(chemin)))
			throw new IllegalArgumentException("Fichier non trouvé");
		fichier = Paths.get(chemin);
		try{
			contenu = readFile(fichier, Charset.defaultCharset());
		}
		catch(IOException e){
			System.out.println("Fichier non trouvé");
		}
		this.numMap = numMap;
		name = "Cave "+numMap;
		findMap();
		//placerJoueur();
	}
	
	static String readFile(Path path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	}
	
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
				case "DiamondsValue":
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
	
	/*public Joueur placerJoueur(){
		Joueur joueur = null;
		for(int i=0;i<tab.length;i++){
			for(int j = 0;j<tab[i].length;j++){
				if(tab[i][j] == 'P'){
					joueur = new JoueurHumain(true,true,'R',i,j);
					tab[i][j] = 'R';
					System.out.println("i et j : "+i+" "+j);
					return joueur;
				}
			}	
		}
		//System.out.println(joueur);
		return joueur;	
	}*/
	


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
	
	public String getContenu() {
		return contenu;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}
	public char getTab(int i, int j){
		return tab[i][j];
	}
	
	public void setTab(int i, int j, char val){
		tab[i][j] = val;
	}
	
	
}