package moteurJeu;

import java.util.ArrayList;

import map.Map;

public class Maintest_v2 {
	
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
					new MoteurJeu();
					//j.construireMapEntite();
					//j.jeu();
					//System.out.println(m.toString());
				}else if(args[i].endsWith("cal")){
					System.out.println("-------Calcul-------\n");
				}else if(args[i].endsWith("rejoue")){
					System.out.println("-------Rejouer une partie-------\n");
				}else if(args[i].endsWith("simul")){
					System.out.println("-------Simuler un jeu-------\n");
				}else if(args[i].endsWith("d")){
					System.out.println("-------Mode Debug-------");
					String[] argsDebug = argumentsDebug(args,i+1);
					i+=argsDebug.length;
					for(int j=0;j<argsDebug.length;j++){
						System.out.println("alors ? :"+argsDebug[j]);
					}
					new MoteurJeu(argsDebug);
				}else{
					System.out.println("-------Adrien est trop cool !-------");
				}
			}
		}
	}
	
	/**
	 * Quand le -d est passe en argument, la fonction apelle celle-ci qui va recuperer tout
	 * les arguments valides qui suivent le -d et les ajouter a la liste d'options a debugger.
	 * @param String[] args, int i
	 * @return String[] arguments la liste des arguments valides qui suivent le -d
	 * */
	public static String[] argumentsDebug(String[] args, int i){
		ArrayList<String> retour=new ArrayList<String>();
		
		System.out.println("estArgumentValide : "+estArgumentValide(args[i]));
		
		while(i<args.length && estArgumentValide(args[i])){
			retour.add(args[i]);
			i++;
		}
		for(int j=0;j<retour.size();j++){
			System.out.println("alors ? :"+retour.get(j));
		}
		
		//Object[] o=retour.toArray();
		String[] tab=new String[retour.size()];
		retour.toArray(tab);
		//for(int j=0;j<o.length;j++){
		//	o[j]=(String) o[j];
		//}
		return tab;
	}
	
	/**
	 * Teste si l'argument passe en parametres est valide ( s'il correspond a un des cas
	 * suivants : 
	 * -tombe
	 * -luciole
	 * -libellule)
	 * @param String string
	 * @return true si l'argument est valide et false sinon
	 * */
	private static boolean estArgumentValide(String string) {
		String chaine=string.toLowerCase();
		return chaine.equals("tombe") || 
			   chaine.equals("libellule") ||
			   chaine.equals("luciole");
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
