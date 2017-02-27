package projetS4;
import java.io.*;

public class Main {

	public static void main(String[] args) {

		Map m = new Map(3,new File("BD01plus.bd"));
		//System.out.println(m.findMap(new File("src/projetS4/BD01plus.bd")));
		m.placerJoueur();
		System.out.println(m.toString());
		MoteurJeu moteur=new MoteurJeu();
		moteur.jeu(m);
	}
}
