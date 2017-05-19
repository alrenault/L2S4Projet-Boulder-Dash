package moteurJeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entite.IA;

import map.Map;

/**
 * Classe main a lancer depuis le .jar
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class Maintest_v2 {
	private static String[] argsDebug ={""};
	private static boolean jeu = false;
	private static int numMap = 1;
	private static String nomFichier ="BD01plus.bd";
	private static int intel=Intelligence.ME.get();
	private static List<Character> arejouer= new ArrayList<Character>();
	
	/**
	 * Teste les arguments du main et lance les bonnes fonctionnalites en fonction. Voici les arguments possibles :
	 * -name : Donne les noms des developpeurs du projet.
	 * -lis FICHIER.bcdf : affiche les parametres d'un fichier bcdf.
	 * -joue FICHIER.bcdf [-niveau N] : permet de jouer a Boulder Dash.
	 * 		par defaut : joue les niveaux les uns apres les autres.
	 * 		-niveau N : Fait jouer le niveau N et seulement lui.
	 * Le resultat du jeu est sauvegarde dans un fichier bcdf a la racine du projet.
	 * -cal strategie FICHIER.bdcff -niveau N :
	 * -rejoue fichier.dash fichier.bdcff -niveau N : 
	 * -simul N strategie strategie fichier.bdcff -niveau N : 
	 * @param args L'ensemble des arguments du main
	 */
	public static void testArguments(String args[]){
		if(args.length>0){
			for(int i=0;i<args.length;i++){
				if(args[i].endsWith("-name")){
					System.out.println("-------Projet Boulder Dash-------\n" +
										"realise par : \n" +
										"	PITROU Adrien\n" +
										"	LEVEQUE Quentin\n" +
										"	RENAULT Alexis\n");
				}else if(args[i].endsWith("-h")){
					System.out.println("-------Options-------\n" +
							"-name : Donne les noms des developpeurs du projet\n" +
							"-lis FICHIER.bcdf : affiche les parametres d'un fichier bcdf\n" +
							"-joue FICHIER.bcdf [-niveau N] : permet de jouer a Boulder Dash.\n" +
							"	par defaut : joue les niveaux les uns apres les autres.\n" +
							"	-niveau N : Fait jouer le niveau N et seulement lui.\n" +
							"Le resultat du jeu est sauvegarde dans un fichier bcdf a la racine du projet.\n" +
							"-cal strategie FICHIER.bdcff -niveau N : \n"+
							"-rejoue fichier.dash fichier.bdcff -niveau N : \n" +
							"-simul N strategie strategie fichier.bdcff -niveau N : \n" +
							"-d option [option] : jouer au BoulderDash avec les options de debug :\n" +
							" 1) tombe\n" +
							" 2) libellule\n" +
							" 3) luciole\n" +
							" 4) parfaite");
				}else if(args[i].endsWith("-lis")){
					System.out.println("-------Proprietes-------\n");
					String chaine = nomFichier;
					if(i+1<args.length){
						chaine=args[i+1];
						i++;
					}
					System.out.println(Map.proprieties("src/"+chaine));
				}else if(args[i].endsWith("-joue")){
					System.out.println("-------Jouer au jeu-------\n");
					
					if(i+1<args.length){
						nomFichier=args[i+1];
						i++;
					}
					System.out.println("test : "+(i+2<args.length));
					if(i+2<args.length && args[i+1].endsWith("niveau")){
						numMap=Integer.parseInt(args[i+2]);
						i++;
					}
					jeu = true;
				}else if(args[i].endsWith("-cal")){
					System.out.println("-------Calcul-------\n");
					if(i+1<args.length){
						String strategie=args[i+1];
						i++;
						if(estValideStrategie(strategie)){
							intel = strategie(strategie);
						}
					}
					jeu = true;
				}else if(args[i].endsWith("-rejoue")){
					System.out.println("-------Rejouer une partie-------\n");
					intel = Intelligence.REJOUE.get();
					String adresse = "src/Sauv1.dash";
					if(i+1<args.length){
						adresse=args[i+1];
						i++;
					}
					rejouerPartie(adresse);
					jeu = true;
				}else if(args[i].endsWith("-simul")){
					System.out.println("-------Simuler un jeu-------\n");
				}else if(args[i].endsWith("d")){
					System.out.println("-------Mode Debug-------");
					argsDebug = argumentsDebug(args,i+1);
					i+=argsDebug.length;
					/*for(int j=0;j<argsDebug.length;j++){
						System.out.println("alors ? :"+argsDebug[j]);
					}*/
					//new MoteurJeu(argsDebug);
				}
				if(jeu){
					MoteurJeu moteur = new MoteurJeu(numMap,nomFichier, argsDebug, intel, arejouer);
					moteur.jeu();
				}
			}
		}
	}
	
	private static int strategie(String strategie) {
		switch(strategie){
		case "-parfait" : return Intelligence.PARFAITE.get();
		case "-directif" : return Intelligence.DIRECTIVE.get();
		case "-evolue" : return Intelligence.GENETIQUE.get();
		case "-simplet" : return Intelligence.RANDOM.get();
		}
		return 0;
	}

	private static boolean estValideStrategie(String strategie) {
		return strategie.endsWith("-parfait") || strategie.endsWith("-directif") ||
				strategie.endsWith("-evolue") || strategie.endsWith("-simplet");
	}

	/**
	 * Quand le -d est passe en argument, la fonction apelle celle-ci qui va recuperer tout
	 * les arguments valides qui suivent le -d et les ajouter a la liste d'options a debugger.
	 * @param String[] args, int i
	 * @return String[] arguments la liste des arguments valides qui suivent le -d
	 * */
	/*private static String[] argumentsJoue(String[] args, int i) {
		ArrayList<String> retour=new ArrayList<String>();
		
		//System.out.println("estArgumentValide : "+estArgumentValideJoue(args[i]));
		
		
		
		
		//Object[] o=retour.toArray();
		String[] tab=new String[retour.size()];
		retour.toArray(tab);
		//for(int j=0;j<o.length;j++){
		//	o[j]=(String) o[j];
		//}
		return tab;
	}*/
	
	private static void rejouerPartie(String path){
		Scanner sc;
		String ligne = "";
		//tente de recuperer la premiere ligne du fichier sauvegarde
		try {
			sc = new Scanner(new File(path));
			ligne = sc.nextLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//List<Character> list = new ArrayList<Character>();
		//remplit le tableau de deplacements avec les caracteres de la chaine
		for(int i=0;i<ligne.length();i++){
			char chara = ligne.charAt(i);
			System.out.println("Prochain cara : "+chara);
			arejouer.add(chara);
		}
		//convertit du format dghb en vk
		Convertisseur.convTabDGHB_vers_VK(arejouer);
	}

	/**
	 * Quand le -d est passe en argument, la fonction apelle celle-ci qui va recuperer tout
	 * les arguments valides qui suivent le -d et les ajouter a la liste d'options a debugger.
	 * @param args L'ensemble des arguments
	 * @param i Iterateur sur les arguments (position actuelle)
	 * @return Retourne l'ensemble des arguments valides se trouvant apres -d
	 * */
	public static String[] argumentsDebug(String[] args, int i){
		ArrayList<String> retour=new ArrayList<String>();
		
		System.out.println("estArgumentValide : "+estArgumentValideDebug(args[i]));
		
		while(i<args.length && estArgumentValideDebug(args[i])){
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
	 * @param string L'argument a tester
	 * @return Retourne true si l'argument est valide et false sinon
	 * */
	private static boolean estArgumentValideDebug(String string) {
		String chaine=string.toLowerCase();
		return chaine.equals("tombe") || 
			   chaine.equals("libellule") ||
			   chaine.equals("parfaite") ||
			   chaine.equals("luciole");
	}
	
	/**
	 * Methode main de boulder dash
	 * @param args Les arguments passes au .jar
	 */
	public static void main(String[] args) {
		testArguments(args);
	}
}
