package prototypeProjetS4;
import java.io.*;
import java.util.Scanner;

public class Main implements FindMap {
	/*
	public static void main(String[] args) {

		//Map m = new Map(3,new File("src/projetS4/BD01plus.bd"));
		
		//Quentin
		Map m = new Map(3,new File("BD01plus.bd"));
		
		
		//System.out.println(m.findMap(new File("src/projetS4/BD01plus.bd")));
		System.out.println(m.toString());
	}
	*/
	
public static void main(String[] args) {
		
		//System.out.println(m.findMap(new File("src/projetS4/BD01plus.bd")));
		//m.placerJoueur();
		m.findMap(f);
		System.out.println(m);
		MoteurJeu moteur=new MoteurJeu(m);
		moteur.jeu(m);
		System.out.println(m.toString());
	}
	
	
}
