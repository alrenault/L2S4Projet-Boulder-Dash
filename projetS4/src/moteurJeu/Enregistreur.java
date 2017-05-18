package moteurJeu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Enregistreur {
	
	private static File cible;
	private static FileWriter fw ;
	private static PrintWriter pw ;
	
	private Enregistreur(){}

	private static void enregistreurGlobal(int ia, String name, ArrayList<Character> listePas, boolean ecrase) {
		// TODO Auto-generated constructor stub
		//ecrase false -> enregistre dans un dossier qui contient toutes les parties
		//ecrase true -> enregistre dans un autre dossier qui contient les meilleures 
		//solutions en fonction de la map et de l'IA, écrase l'ancienne solution si elle existe
		
		String chemin = "src/";
		name=noExtension(name);
		
		switch(ia){
		case -2 : name += "_REJOUER";break;
		case -1 : name += "_NO"; break;
		case 0 : name += "_ME"; break;
		case 1 : name += "_RANDOM"; break;
		case 2 : name += "_GENETIQUE"; break;
		case 3 : name += "_DIRECTIVE"; break;
		case 4 : name += "_PARFAITE"; break;
		default : name += "_INCONNU"; break;
		}
		
		if (ecrase){
			chemin += "Solutions/"+name+".txt";
			cible = new File(chemin);
		}
		else{
			int i = 0;
			do{
				i++;
				cible = new File(chemin+"Sauvegardes/"+name+i+".dash");
				
			}while(cible.exists() || cible.isDirectory());
				
			
		}
		
		//Open File and Write
		System.out.println(cible);
		try{
			cible.createNewFile();
			fw = new FileWriter(cible);
			pw = new PrintWriter(fw);
			pw.println(listePas);
			pw.flush();
		}
		catch(IOException e){
			e.printStackTrace();
			
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	public static void sauvegarderPartie(int ia, String numMap_nameOfFile, ArrayList<Character> listePas){
		enregistreurGlobal(ia,numMap_nameOfFile,listePas,false);
	}
	
	public static void ecraserSolution(int ia, String numMap_nameOfFile, ArrayList<Character> listePas){
		enregistreurGlobal(ia,numMap_nameOfFile,listePas,true);
	}
	
	//Supprime l'extension d'un fichier SI il n'y a QU'UN SEUL point '.'
	public static String noExtension(String name){
		String j=""; int i=0;
		while(name.charAt(i) != '.'){
			j+=name.charAt(i);
			i++;
		}
	return j;
	}
	

}
