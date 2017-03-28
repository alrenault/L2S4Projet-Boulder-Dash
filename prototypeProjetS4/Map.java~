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
	
	public Map(int numMap){
		this.numMap = numMap;
		name = "Cave "+numMap;
	}
	
	public String findMap(File f){
		String map = "";
		Scanner sc;
		try{
			sc = new Scanner(f);
			String ligne ="";
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
		
		return map;
	}
	
}
