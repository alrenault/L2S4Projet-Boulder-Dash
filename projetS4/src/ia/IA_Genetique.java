package ia;
import java.io.*;
import java.util.List;

import moteurJeu.MoteurJeu;
import entite.IA;

public class IA_Genetique implements IA, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3771487772362717198L;
	private Rockford thisRockford[];
	private int population;
	private int nbPas;
	private static int itRocker;
	private int generation ;
	
	private File fileGen;

	public IA_Genetique(int n, MoteurJeu moteur) {
		// TODO Auto-generated constructor stub
		
		
		
		//Create 100 little Rockford
		population = n; //Le nombre de Rockford qui vont parcourir la map
		thisRockford = new Rockford[population]; //Le tableau qui contient tous les Rockford
		nbPas = (moteur.getHauteurMap())*(moteur.getLargeurMap()); //Le nombre de pas à faire sur la map (hauteur*largeur)
		
		
		//On importe la population précédente si elle existe
		if (dataGenDesu(moteur.getNomFichier(),moteur.getNumMap()))
			importDataGen();
		else initialisation(); //Sinon on la crée
			
	}
	
	
	
	
	
	public IA_Genetique(MoteurJeu moteur) {
		this(100,moteur); // Par défaut, on crée 100 Rockford
	}
	
	public void importGen (IA_Genetique j){
		this.thisRockford = j.getThisRockford();
		this.population = j.getPopulation();
		this.nbPas = j.getNbPas();
		this.generation = j.getGeneration();
	}
	
	//---------------------------------------------------------------------------------
	
	
	private void initialisation(){
		//Initialise la population de Rockford et l'envoie dans le fichier correspondant
		for(int i = 0 ; i < population ; i++){
			//thisRockford[i] = new Rockford(10000);
			thisRockford[i] = new Rockford(nbPas);
		}
		generation = 0 ;
		try{
			fileGen.createNewFile();
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
		
		fileGen = new File("src/Genetique/"+numMap+nomFichier);
		return (fileGen.exists() && !fileGen.isDirectory());
	}
	
	
	
	//Importe l'IA de la map actuelle à partir d'un fichier
	
	private void importDataGen(){
		//Importe le tableau thisRockford du fichier BD01plus.bd1 pour le niveau 1
		//dans thisRockford[]
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
		try {
			fos = new FileOutputStream(fileGen);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
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

	public void action() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Character> actionList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Getters, Setters, Equals ---------------------------------------------------------
	
	
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


	public Rockford[] getThisRockford() {
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

	

}
