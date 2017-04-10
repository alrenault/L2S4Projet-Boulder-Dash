package moteurJeu;
import affichage.*;
import ia.*;
import map.*;

public class maintest {

	public static void main(String[] args) {
		Map m = new Map(1,"src/projetS4/BD01plus.bd");
		System.out.println(m.toString());
		
		MoteurJeu j = new MoteurJeu(1,"src/projetS4/BD01plus.bd");
		j.construireMapEntite();
		j.jeu(m);
		System.out.println(m.toString());
		//System.out.println(j.afficherMapEntite());
	}
	
	/*	m.findMap(f);
		System.out.println(m);
		MoteurJeu moteur=new MoteurJeu(m);
		moteur.jeu(m);
		System.out.println(m.toString());
	}*/

}
