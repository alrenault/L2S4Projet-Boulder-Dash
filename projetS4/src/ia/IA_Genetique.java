package ia;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.Comparator;

import moteurJeu.MoteurJeu;
import entite.IA;
import moteurJeu.Enregistreur;

public class IA_Genetique implements IA, Serializable {
	
	
	private static final long serialVersionUID = -3771487772362717198L;
	
	
	private TreeMap<Integer,Rockford> thisRockford ; //Population de Rockford sauvegardée
	private List<Rockford> newPopulation ; //Population de travail
	
	//[0 --------- idNew-Randrock ---------- idNew -------- population] //Variables
	//[- Meilleurs -------------- Aléatoires ----- Nouveaux ----------]	//Types de Rockford dans thisRockford
	
	private int population; //nombre de Rockford dans la population
	private int idNew; //indice auquel commence les nouveaux Rockford dans la reproduction --- Ceux avant sont sélectionnés
	private int randRock; //nombre de Rockford aléatoires : <= idNew
	
	private int nbPas; //Nombre de pas que vont faire les Rockford sur la map
	private static int itRocker; //Variable pour itérer sur la population
	private int generation ; //Nombre de fois où l'IA génétique a été répétée pour cette map
	private static Rockford tempRockford;
	
	//Fichiers de Sauvegarde
	private File fileGen;
	private File fileSolution;
	
	private Random rand; //Pour les nombres aléatoires

	public IA_Genetique(int n, MoteurJeu moteur) {
		// TODO Auto-generated constructor stub
		
		
		//Create 100 little Rockford
		population = n; //Le nombre de Rockford qui vont parcourir la map
		idNew = 30 ;
		randRock = 10;
		
		thisRockford = new TreeMap<Integer,Rockford>();	//Population d'évalutation
		newPopulation = new ArrayList<Rockford>(); //Population de sélection
		nbPas = (moteur.getHauteurMap())*(moteur.getLargeurMap()); //Le nombre de pas à faire sur la map (hauteur*largeur)
		itRocker=1;
		
		rand = new Random();
		
		
		//On importe la population précédente si elle existe
		if (dataGenDesu(moteur.getNomFichier(),moteur.getNumMap()))
			importDataGen();
		else initialisation(); //Sinon on la crée
		
		System.out.println(thisRockford.get(1));
			
	}
	
	
	
	
	
	public IA_Genetique(MoteurJeu moteur) {
		this(100,moteur); // Par défaut, on crée 100 Rockford
	}
	
	public void importGen (IA_Genetique j){	//Copie l'IA Génétique en paramètre
		this.thisRockford = j.getThisRockford();
		this.population = j.getPopulation();
		this.nbPas = j.getNbPas();
		this.generation = j.getGeneration();
		this.newPopulation = new ArrayList<Rockford>();
	}
	
	//---------------------------------------------------------------------------------
	
	
	private void initialisation(){
		//Initialise la population de Rockford et l'envoie dans le fichier correspondant
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
	
	
	//Informe de l'existence ou non d'une intelligence génétique pour cette map dans un fichier

	private boolean dataGenDesu(String nomFichier, int numMap){
		
		//Si le fichier existe, renvoie true, sinon false
		//Affecte un file à l'argument fileGen
		
		String newName= numMap+"-"+Enregistreur.noExtension(nomFichier);
		
		fileGen = new File("src/Genetique/data/"+ newName +".dat");
		fileSolution = new File("src/Genetique/"+ newName +".txt");
		System.out.println(fileGen.toString() + fileSolution.toString());
		return (fileGen.exists() && !fileGen.isDirectory()  && 
				fileSolution.exists() && !fileSolution.isDirectory());
	}
	
	
	
	//Importe l'IA de la map actuelle à partir d'un fichier
	
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
			System.out.println("Votre Version sauvegardée a des paramètres différents que celle que vous essayez de construire");
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
	
	//Exporte les données de l'IA vers le fichier correspondant --------------------------
	
	private void exportDataGen(){
		//Exporte le tableau de direction dans le fichier après traitement
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
	
	
	
	public void process(){
		
	}
	
	

	


	
	
	
	
	
	//Getters, Setters, Equals ---------------------------------------------------------
	
	public String toString() {
		String sep = "\n_______________________________________\n\n" ;
		
		String j = "IA_Génétique de Map-Fichier :" + fileGen + 
					" \nGénération : "+ generation
					
					+ sep +
					
					"Nombre de Pas sur la Map : " + nbPas +
					"\nNombre de Rockford : " + population +
					"\nNombre d'Opérations Déplacement (population*nbPas) = " + nbPas*population 
					
					+ sep + 
					
					"Listes de Déplacement : \n\n" ;
					
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
		//Compare l'état de l'IA actuelle avec celle sauvegardée
		//Ne compare pas les tableaux, il faut simplement que les map soient identiques
		//Pour pouvoir améliorer la version de l'IA sauvegardée
		
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


	public TreeMap<Integer, Rockford> getThisRockford() {
		return thisRockford;
	}

	public int getPopulation() {
		return population;
	}

	public int getNbPas() {
		return nbPas;
	}

	public File getFileGen() {
		return fileGen;
	}
	
	public static int getItRocker() {
		return itRocker;
	}





	public int getGeneration() {
		return generation;
	}

	
	
	private void addNewPopulation(){
		//newPopulation.put(itRocker, tempRockford);
		newPopulation.add(tempRockford);
		//tempRockford = null;
	}
	
	public void updateThisRockford(boolean victoire, int diamants, int pas, ArrayList<Character> listePas){
		tempRockford = new Rockford(victoire, diamants, pas, listePas);
		addNewPopulation();
	}
	
	private void selection(){
		
		//Sélectionne les $idNew-10 premiers Rockford de newPopulation
		//Ajoute $randRock Rockford de newPopulation aléatoirement
		//Et les met en tête de thisRockford
		
		int i = 1;
		//Collection<Rockford> valueMap = newPopulation.values();
		//newPopulation.sort(c);
		
		System.out.println("\n------------------------ START ------------------------\n" + newPopulation +"\n------------------------ END ------------------------");
		Collections.sort(newPopulation);
		/*Collections.sort(newPopulation,
				new Comparatator<Rockford>(){
					public int compare (Rockford j1, Rockford j2){
						return j1.compareTo(j2);
					}
		});*/
		
		//System.out.println(newPopulation);
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
	
	private void reproduction(int mutation){
		
		//Utilise les $idNew premiers Rockford de thisRockford pour reproduire les nouveaux Rockford
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
	
	public int getITRocker(){
		return itRocker;
	}
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
	
	public boolean thisRockfordisMoving(){
		return (thisRockford.get(itRocker).isMoving());
	}

	

}
