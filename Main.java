package projetS4;
import java.io.*;

public class Main {

	public static void main(String[] args) {

		File f = new File("src/projetS4/BD01plus.bd");
		Map m = new Map(3,f);
		//System.out.println(m.findMap(new File("src/projetS4/BD01plus.bd")));
		m.findMap(f);
		//m.placerJoueur();
		MoteurJeu moteur=new MoteurJeu(m);
		moteur.jeu(m);
		System.out.println(m.toString());
	}
}
