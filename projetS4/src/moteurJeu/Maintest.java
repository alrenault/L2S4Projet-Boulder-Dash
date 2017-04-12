package moteurJeu;

import map.Map;

public class Maintest {
	
	public static void testArguments(String args[]){
		if(args.length>0){
			for(int i=0;i<args.length;i++){
				if(args[i].endsWith("name")){
					System.out.println("-------Projet Boulder Dash-------\n" +
										"realise par : \n" +
										"	PITROU Adrien\n" +
										"	LEVEQUE Quentin\n" +
										"	RENAULT Alexis\n");
				}else if(args[i].endsWith("h")){
					System.out.println("-------Options-------\n" +
							"-name : Donne les noms des developpeurs du projet\n" +
							"-lis FICHIER.bcdf : affiche les parametres d'un fichier bcdf\n" +
							"-joue FICHIER.bcdf [-niveau N] : permet de jouer a Boulder Dash.\n" +
							"	par defaut : joue les niveaux les uns apres les autres.\n" +
							"	-niveau N : Fait jouer le niveau N et seulement lui.\n" +
							"Le resultat du jeu est sauvegarde dans un fichier bcdf a la racine du projet.\n" +
							"-cal strategie FICHIER.bdcff -niveau N : \n"+
							"-rejoue fichier.dash fichier.bdcff -niveau N : \n" +
							"-simul N strategie strategie fichier.bdcff -niveau N : ");
				}else if(args[i].endsWith("joue")){
					System.out.println("-------Jouer au jeu-------\n");
					Map m = new Map(1,"src/BD01plus.bd");
					System.out.println(m.toString());
					
					MoteurJeu j = new MoteurJeu(1,"src/BD01plus.bd");
					j.construireMapEntite();
					j.jeu();
					System.out.println(m.toString());
				}else if(args[i].endsWith("cal")){
					System.out.println("-------Calcul-------\n");
				}else if(args[i].endsWith("rejoue")){
					System.out.println("-------Rejouer une partie-------\n");
				}else if(args[i].endsWith("simul")){
					System.out.println("-------Simuler un jeu-------\n");
				}else{
					System.out.println("-------Adrien est trop cool !-------");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		testArguments(args);
		
		/*Map m = new Map(1,"src/BD01plus.bd");
		System.out.println(m.toString());
		
		MoteurJeu j = new MoteurJeu(1,"src/BD01plus.bd");
		j.construireMapEntite();
		j.jeu();
		System.out.println(m.toString());*/
	}
}
