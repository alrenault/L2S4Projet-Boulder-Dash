package ia;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import moteurJeu.MoteurJeu;
import entite.IA;
import moteurJeu.Enregistreur;

/**
 * Classe construisant une IA genetique
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class IA_Genetique implements IA, Serializable {
	
	/**
	 * Pour la serialisation
	 */
	private static final long serialVersionUID = -3771487772362717198L;
	
	/**
	 * Population de Rockford sauvegarde
	 */
	private TreeMap<Integer,Rockford> thisRockford ;
	
	/**
	 * Population de travail
	 */
	private List<Rockford> newPopulation ; 
	
	//[0 --------- idNew-Randrock ---------- idNew -------- population] //Variables
	//[- Meilleurs -------------- Aléatoires ----- Nouveaux ----------]	//Types de Rockford dans thisRockford
	
	/**
	 * Nombre de Rockford dans la population
	 */
	private int population; 

	/**
	 * indice auquel commence les nouveaux Rockford dans la reproduction --- Ceux avant sont selectionnes
	 */
	private int idNew;
	
	/**
	 * nombre de Rockford aleatoires : superieur ou egal a idNew
	 */
	private int randRock; 
	
	/**
	 * Nombre de pas que vont faire les Rockford sur la map
	 */
	private int nbPas; 
	
	/**
	 * Variable pour iterer sur la population
	 */
	private static int itRocker; 
	
	/**
	 * Nombre de fois ou l'IA genetique a ete repetee pour cette map
	 */
	private int generation ; 
	
	/**
	 * Variable temporaire stockant un Rockford lorsque l'on veut l'ajouter a une population
	 */
	private static Rockford tempRockford;
	
	/**
	 * Fichier serialise contenant les info de l'IA genetique pour une map
	 */
	private File fileGen;
	
	/**
	 * Fichier texte contenant les info de l'IA genetique pour une map
	 */
	private File fileSolution;
	
	/**
	 * Pour les nombres aleatoires
	 */
	private Random rand; 

	/**
	 * Constructeur de la classe IA_Genetique
	 * @param n Nombre de Rockford dans la population
	 * @param moteur Reference vers le moteur de jeu
	 */
	public IA_Genetique(int n, MoteurJeu moteur) {
		// TODO Auto-generated constructor stub
		
		
		//Create 100 little Rockford
		population = n; //Le nombre de Rockford qui vont parcourir la map
		idNew = 30 ;
		randRock = 10;
		
		thisRockford = new TreeMap<Integer,Rockford>();	//Population d'evalutation
		newPopulation = new ArrayList<Rockford>(); //Population de selection
		nbPas = (moteur.getHauteurMap())*(moteur.getLargeurMap()); //Le nombre de pas à faire sur la map (hauteur*largeur)
		itRocker=1;
		
		rand = new Random();
		
		
		//On importe la population precedente si elle existe
		if (dataGenDesu(moteur.getNomFichier(),moteur.getNumMap()))
			importDataGen();
		else initialisation(); //Sinon on la crée
		
		//System.out.println(thisRockford.get(1));
			
	}
	
	
	
	
	/**
	 * Constructeur de la classe IA_Genetique
	 * @param moteur Reference vers le moteur de jeu
	 */
	public IA_Genetique(MoteurJeu moteur) {
		this(100,moteur); // Par defaut, on cree 100 Rockford
	}
	
	/**
	 * Copie l'IA Genetique en parametre
	 * @param j L'IA a copier
	 */
	public void importGen (IA_Genetique j){	
		this.thisRockford = j.getThisRockford();
		this.population = j.getPopulation();
		this.nbPas = j.getNbPas();
		this.generation = j.getGeneration();
		this.newPopulation = new ArrayList<Rockford>();
	}
	
	//---------------------------------------------------------------------------------
	
	/**
	 * Initialise la population de Rockford et l'envoie dans le fichier correspondant
	 */
	private void initialisation(){
		for(int i = 1 ; i <= population ; i++){
			//thisRockford[i] = new Rockford(nbPas);
			thisRockford.put(i, new Rockford(nbPas));
		}
		generation = 0 ;
		try{
			fileGen.createNewFile();
			fileSolution.createNewFile();
		}
		catch(IOException e ){
			e.printStackTrace();
		}
		exportDataGen();
	}
	
	
	/**
	 * Informe de l'existence ou non d'une intelligence genetique pour cette map dans un fichier
	 * Initialisation de fileGen et de fileSolution
	 * @param nomFichier Fichier a tester
	 * @param numMap Numero de la map a tester
	 * @return Retourne true s'il y a une intelligence genetique pour cette map, sinon false
	 */
	private boolean dataGenDesu(String nomFichier, int numMap){
		
		String newName= numMap+"-"+Enregistreur.noExtension(nomFichier);
		
		fileGen = new File("src/Genetique/data/"+ newName +".dat");
		fileSolution = new File("src/Genetique/"+ newName +".txt");
		return (fileGen.exists() && !fileGen.isDirectory()  && 
				fileSolution.exists() && !fileSolution.isDirectory());
	}
	
	
	
	/**
	 * Importe l'IA de la map actuelle a� partir d'un fichier
	 */
	private void importDataGen(){
		
		FileInputStream fis = null ;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream(fileGen);
			ois = new ObjectInputStream(fis);
			IA_Genetique inFile = (IA_Genetique) ois.readObject() ;
			if (this.equals(inFile)) importGen(inFile);
			else throw new DifferentParametersException();
			
			
		}
		catch(DifferentParametersException e){
			System.out.println("Votre Version sauvegardee a des parametres differents que celle que vous essayez de construire");
		}
		
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Close fis
		finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * Exporte les donnees de l'IA vers les fichiers correspondants
	 */
	private void exportDataGen(){
		//Exporte le tableau de direction dans le fichier apres traitement
		FileOutputStream fos = null ;
		ObjectOutputStream oos ;
		PrintWriter pw ;
		FileWriter fw ;
		
		try {
			//Objet IA_Genetique
			fos = new FileOutputStream(fileGen);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			
			//Solution Lisible
			fw  = new FileWriter(fileSolution);
			pw = new PrintWriter(fw);
			
			pw.println(toString());
			for (int i = 1 ; i <= population ; i++) {
				pw.println("Rockford n°" + i + " --> " + thisRockford.get(i));
				if (i==population) 
					pw.println("\n\n-------------- Fin de l'IA : " + fileGen + " ; Version " + generation + " --------------\n") ;
				pw.flush();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//Close fos
		finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Ajoute tempRockford dans la nouvelle population
	 */
	private void addNewPopulation(){
		newPopulation.add(tempRockford);
	}
	
	/**
	 * Construit un Rockford
	 * @param victoire Si le Rockford a gagne
	 * @param diamants Le nombre de diamants recuperes
	 * @param pas Nombre de pas effectues
	 * @param listePas Liste de touche utilisees
	 */
	public void updateThisRockford(boolean victoire, int diamants, int pas, ArrayList<Character> listePas){
		tempRockford = new Rockford(victoire, diamants, pas, listePas);
		addNewPopulation();
	}
	
	/**
	 * Effectue la selection de l'IA genetique
	 * Selectionne les idNew-randRock premiers Rockford de newPopulation
	 * Ajoute randRock Rockford de newPopulation aleatoirement
	 *	Et les met en tete de thisRockford
	 */
	private void selection(){
				
		int i = 1;
		
		//System.out.println("\n------------------------ START ------------------------\n" + newPopulation +"\n------------------------ END ------------------------");
		Collections.sort(newPopulation);
		
		int iRockford = 1;
		for (Rockford j : newPopulation){
			System.out.println("Rockford : "+iRockford+" --> "+j+"\n");
			iRockford++;
		}
		
		Iterator<Rockford> it = newPopulation.iterator();
		
		while (it.hasNext() && i<=idNew) {
			
			if (i<=idNew-randRock){
				Rockford jRockfordSelectionne = it.next();
				thisRockford.put(i,jRockfordSelectionne);
				//System.out.println("Rockford :"+i+" --> "+ jRockfordSelectionne);
			} else {
				Rockford j = null;
				do{
					//int selectionRandom = rand.nextInt(population-idNew)
					j = newPopulation.get(rand.nextInt(population-idNew+randRock)+idNew-randRock);
				} while (j==null);
				//j = newPopulation.get(rand.nextInt(population-idNew+randRock)+1+idNew-randRock);
				//System.out.println("Rockford : "+i+ " --> "+j);
				thisRockford.put(i, j);
			}
			
		    i++;
		}
	}
	
	/**
	 * Utilise les idNew premiers Rockford de thisRockford pour reproduire les nouveaux Rockford
	 * @param mutation Nombre de mutations effectues
	 */
	private void reproduction(int mutation){
		
		for (int j = idNew+1 ; j<=population ; j++){
			Rockford newRockford = null ;
			do{
				newRockford = fusionRockford(mutation);
			}while(newRockford == null);
			thisRockford.put(j, newRockford);
			
		}
		
		//System.out.println("Size newPopulation :" + newPopulation.size() + "\nSize thisRockford :" + thisRockford.size());
		generation++;
		exportDataGen();
		importDataGen();
		
		System.out.println("Nouvelle génération :" + generation + thisRockford.get(1).toString());
	}
	
	/**
	 * Fusionne les listes de deux Rockford selectionnes
	 * @param mutation Nombre de mutations effectues
	 * @return Retourne un nouveau Rockford
	 */
	private Rockford fusionRockford(int mutation){
		int i = rand.nextInt(idNew)+1;
		int j = rand.nextInt(idNew)+1;
		//System.out.println("i :" + i + ", j :"+j);
		
		Rockford newRockford = null ;
		try{
			newRockford = new Rockford(thisRockford.get(i).getDirectionList(),thisRockford.get(j).getDirectionList(),mutation);
		} catch (NullPointerException e){
			//newRockford = thisRockford.get(i);
		}
		
		//System.out.println("newRockford :" +newRockford);
		return newRockford;
	}

	/**
	 * Recupere le prochain caractere joue par l'IA genetique
	 * @return Retourne le prochain caractere joue par l'IA genetique
	 */
	public char action(){
		if (!thisRockfordisMoving()){ //Si la liste de déplacement du Rockford est vide
			nextRockford(); //On passe au suivant
		}
		
		return thisRockford.get(itRocker).nextPosition(); //On retourne la prochaine position
		
	}

	@Override
	public List<Character> actionList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Reinitialise l'iterateur de parcourt de liste du Rockford actuel.
	 * Passe au prochain Rockford sauf si il s'agit du dernier et dans ce cas, lance la phase selection/reproduction.
	 * Vide les populations.
	 */
	public void nextRockford(){
		thisRockford.get(itRocker).resetItList();
		if (itRocker<population) itRocker++;
		else {
			thisRockford.clear();
			selection();
			reproduction(rand.nextInt(nbPas/40));
			newPopulation.clear();
			itRocker = 1 ;
		}
	}
	
	/**
	 * Teste si le Rockford actuel a une liste de deplacement non vide.
	 * @return Retourne true si la liste est non vide, sinon false.
	 */
	public boolean thisRockfordisMoving(){
		return (thisRockford.get(itRocker).isMoving());
	}
	
	//Getters, Setters, Equals ---------------------------------------------------------
	
	public String toString() {
		String sep = "\n_______________________________________\n\n" ;
		
		String j = "IA_Genetique de Map-Fichier :" + fileGen + 
					" \nGeneration : "+ generation
					
					+ sep +
					
					"Nombre de Pas sur la Map : " + nbPas +
					"\nNombre de Rockford : " + population +
					"\nNombre d'Operations Deplacement (population*nbPas) = " + nbPas*population 
					
					+ sep + 
					
					"Listes de Deplacement : \n\n" ;
					
		return j ;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileGen == null) ? 0 : fileGen.hashCode());
		result = prime * result + nbPas;
		result = prime * result + population;
		return result;
	}


	@Override
	public boolean equals(Object obj) {	
		//Compare l'�etat de l'IA actuelle avec celle sauvegardee
		//Ne compare pas les tableaux, il faut simplement que les map soient identiques
		//Pour pouvoir ameliorer la version de l'IA sauvegardee
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IA_Genetique other = (IA_Genetique) obj;
		
		if (fileGen == null) {
			if (other.fileGen != null)
				return false;
		} else if (!fileGen.getName().equals(other.fileGen.getName()))
			return false;
		if (nbPas != other.nbPas)
			return false;
		if (population != other.population)
			return false;
		return true;
	}


	/**
	 * Getter de thisRockford
	 * @return Retourne thisRockford
	 */
	public TreeMap<Integer, Rockford> getThisRockford() {
		return thisRockford;
	}

	/**
	 * Getter de population
	 * @return Retourne la population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * Getter de nbPas
	 * @return Retourne le nombre de pas
	 */
	public int getNbPas() {
		return nbPas;
	}

	
	/**
	 * Getter de FileGen
	 * @return Retourne FileGen
	 */
	public File getFileGen() {
		return fileGen;
	}
	
	/**
	 * Getter de ItRocker
	 * @return Retourne ItRocker
	 */
	public static int getItRocker() {
		return itRocker;
	}
	
	/**
	 * Getter de generation
	 * @return Retourne la generation
	 */
	public int getGeneration() {
		return generation;
	}	
}
