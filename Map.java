package projetS4;

import java.io.*;
import java.util.Scanner;

public class Map {
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
	
	//--> HAUTEUR !!
	//--> LARGEUR !!
	public Map(int numMap, File f){
		this.numMap = numMap;
		name = "Cave "+numMap;
		findMap(f);
	}
	
	public int nbLigne(String map){
		Scanner sc = new Scanner(map).useDelimiter("\n");
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
		return i-1;
		
	}
	
	public void findMap(File f){
		String map = "";
		Scanner sc;
		String ligne ="";
		try{
			sc = new Scanner(f);
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
		}
		catch(FileNotFoundException e){
			System.out.println("Fichier non trouvé");
		}
		
		Scanner sc1 = new Scanner (map).useDelimiter("=|\n");
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
					System.out.println(diamondVal);
					System.out.println(diamondBonus);
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
					magicWallT = Integer.parseInt(ligne);					break;
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
	}
	
	public void placerJoueur(){
		for(int i=0;i<tab.length;i++){
			for(int j = 0;j<tab[i].length;j++){
				if(tab[i][j] == 'P'){
					Joueur joueur = new JoueurHumain(true,true,'R',i,j);
					tab[i][j] = 'R';
				}
			}
		}
	}
	
	public void deplacerJoueur(int x, int y){
		
	}

	public String toString(){
		String s ="";
		for(int i =0;i<hauteur;i++){
			for(int j = 0; j<largeur;j++){
				
				s+=tab[i][j];
			}
			s+="\n";
		}
		return s;
	}
	
}
