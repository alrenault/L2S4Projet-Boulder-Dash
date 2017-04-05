package projetS4.moteurJeu;
import projetS4.affichage.*;
import projetS4.ia.*;
import projetS4.map.*;

public class maintest {

	public static void main(String[] args) {

		Map m = new Map(1,"src/projetS4/BD01plus.bd");
		System.out.println(m.toString());
		
		MoteurJeu j = new MoteurJeu(1,"src/projetS4/BD01plus.bd");
		j.construireMapEntite();
		System.out.println(j.afficherMapEntite());
	}

}
