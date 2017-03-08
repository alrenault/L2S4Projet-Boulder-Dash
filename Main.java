package projetS4;
import java.io.*;

public class Main implements FindMap{

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
