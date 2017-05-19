package moteurJeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import entite.*;

public class DataRobil {
	/*Position joueur;
	HashSet<Position> luciole;
	HashSet<Position> libellule;
	HashSet<Position> amibe;
	HashSet<Position> rochers;
	HashSet<Position> diamants;
	HashSet<Position> espace;
	HashSet<Position> poussiere;
	HashSet<Position> mur;
	HashSet<Position> murTitane;
	HashSet<Position> murMagique;
	HashSet<Position> explosions;*/
	
	Joueur joueur;
	Libellule libellule;
	Luciole luciole;
	Amibe amibe;
	Roc rocher;
	Diamant diamant;
	Explosion explosion;
	MurBasique mur;
	MurTitane murTitane;
	MurMagique murMagique;
	Espace espace;
	Poussiere poussiere;
	
	Entite[][] entite;
	int nbDiamants;
	
	Position posPorte = null;
	MoteurJeu moteur;
	
	int numMap;
	
	public ArrayList<Character> listeDeplacements = new ArrayList<Character>();
	public boolean aGagne = false;
	
	public DataRobil(MoteurJeu moteur){
		/*Iterator<Position> jou = moteur.joueur.getPosition().iterator();
		Position pos = jou.next();
		joueur = new Position(pos.getX(),pos.getY());
		luciole = new HashSet(moteur.li);*/
		//BuildEntity builder = new BuildEntity();
		//robil = (Joueur) builder.buildEntity(moteur,'P');;
		joueur = (Joueur) moteur.copyEntite(new Joueur());
		luciole = (Luciole) moteur.copyEntite(new Luciole(moteur));
		libellule = (Libellule) moteur.copyEntite(new Libellule(moteur));
		amibe = (Amibe) moteur.copyEntite(new Amibe(moteur));
		rocher = (Roc) moteur.copyEntite(new Roc());
		diamant = (Diamant) moteur.copyEntite(new Diamant());
		mur = (MurBasique) moteur.copyEntite(new MurBasique());
		murTitane = (MurTitane) moteur.copyEntite(new MurTitane());
		murMagique = (MurMagique) moteur.copyEntite(new MurMagique());
		explosion = (Explosion) moteur.copyEntite(new Explosion(moteur));
		espace = (Espace) moteur.copyEntite(new Espace());
		poussiere = (Poussiere) moteur.copyEntite(new Poussiere());
		
		entite = moteur.copieEntite();
		nbDiamants = moteur.getNbDiamantRecolte();
		numMap = moteur.getNumMap();
		
		this.moteur = moteur;
		aGagne = moteur.isIAParfaiteAGagne();
		
		//if(moteur.isPorteAffiche()){
			posPorte = moteur.getPosPorte();
		//}
		
		listeDeplacements = new ArrayList<Character>();
		//System.out.println("renvoie un "+joueur);
	}
	
	public DataRobil(MoteurJeu moteur, ArrayList<Character> listeDeplacements) {
		this(moteur);
		this.listeDeplacements.clear();
		this.listeDeplacements.addAll(listeDeplacements);
	}

	public boolean estSolution(){
		/*Iterator<Position> itJoueur = joueur.getPosition().iterator();
		boolean verite =itJoueur.next().equals(posPorte);
		System.out.println("estSolution : "+verite);
		moteur.isAParfaiteGagne(this);*/
		return aGagne;
	}
	
	public char[] solution(){
		char[] retour = new char[listeDeplacements.size()];
		for(int i=0;i<listeDeplacements.size();i++){
			retour[i] = listeDeplacements.get(i);
		}
		return retour;
	}
	
	public String toString(){
		return "DataRobil : \nrobil : "+joueur.toString()+"\ntaille chemin : "+
				listeDeplacements.size();
	}

	public void chargerDonnees(MoteurJeu moteur) {
		moteur.chargerDonnees(this);
	}

	public int compare(DataRobil data) {
		if(data == null){
			return 1;
		}else if(this.estMort() && data.estMort()){
			return 0;
		}else if(this.estMort()){
			return -1;
		}else if(data.estMort()){
			return 1;
		}else{
			return testSortie(data);
		}
	}
	
	private int testSortie(DataRobil data) {
		if(this.estSurSortie() && data.estSurSortie()){
			return 0;
		}else if(this.estSurSortie()){
			return 1;
		}else if(data.estSurSortie()){
			return -1;
		}else{
			return testSortieTrouvee(data);
		}
	}

	private int testSortieTrouvee(DataRobil data) {
		if(this.estSortieTrouvee() && data.estSortieTrouvee()){
			return testLongueur1(data);
		}else if(this.estSortieTrouvee()){
			return 1;
		}else if(data.estSortieTrouvee()){
			return -1;
		}else{
			return testDiamants1(data);
		}
	}

	private int testLongueur1(DataRobil data) {
		if(this.listeDeplacements.size() == data.listeDeplacements.size()){
			return testDiamants2(data);
		}else if(this.listeDeplacements.size() > data.listeDeplacements.size()){
			return 1;
		}else{
			return -1;
		}
	}
	
	private int testDiamants2(DataRobil data) {
		if(this.nbDiamants == data.nbDiamants){
			return 0;
		}else if(this.nbDiamants > data.nbDiamants){
			return 1;
		}else{
			return -1;
		}
	}

	private int testDiamants1(DataRobil data) {
		if(this.nbDiamants == data.nbDiamants){
			return testLongueur2(data);
		}else if(this.nbDiamants > data.nbDiamants){
			return 1;
		}else{
			return -1;
		}
	}

	private int testLongueur2(DataRobil data) {
		if(this.nbDiamants == data.nbDiamants){
			return 0;
		}else if(this.nbDiamants > data.nbDiamants){
			return 1;
		}else{
			return -1;
		}
	}

	private boolean estSortieTrouvee() {
		return this.moteur.isPorteAffiche();
	}

	private boolean estSurSortie() {
		Iterator<Position> it = moteur.getPositionJoueur().iterator();
		Position pos=it.next();
		return pos.equals(moteur.getPosPorte());
	}

	private boolean estMort() {
		System.out.println("estMort -> moteur isAPerdu"+moteur.isaPerdu());
		return this.moteur.isaPerdu();
	}

	/**
	 * Une methode qui evite les problemes courants qui surviennent quand on doit
	 * supprimer une position d'un HashSet.
	 * */
	private HashSet<DataRobil> extraireData(HashSet<DataRobil> e, Position p){
		System.out.println("euh ?... "+e.size());
		Iterator<DataRobil> it=e.iterator();
		HashSet<DataRobil> retour = new HashSet<DataRobil>();
		while(it.hasNext()){
			DataRobil sortie = it.next();
			Iterator<Position> positionJoueur = sortie.joueur.getPosition().iterator();
			Position pos=positionJoueur.next();
			if(pos.getX() != p.getX() || pos.getY() != p.getY()){
				retour.add(sortie);
			}
		}
		System.out.println("alors ? "+retour.size());
		return retour;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((joueur == null) ? 0 : joueur.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataRobil other = (DataRobil) obj;
		/*System.out.println("this : "+this);
		System.out.println("other : "+other);*/
		Position posJoueur = this.getPositionJoueur();
		Position posAutre = other.getPositionJoueur();
		/*System.out.println("PosJoueur : "+posJoueur);
		System.out.println("PosAutre : "+posAutre);*/
		if(posJoueur.getX() == posAutre.getX() &&
				posJoueur.getY() == posAutre.getY()){
			//System.out.println("\n______retour du equals vrai_______\n");
			return true;
		}
		//System.out.println("\n______retour du equals faux_______\n");
		return false;
	}
	
	public Position getPositionJoueur(){
		Iterator<Position> it = this.joueur.getPosition().iterator();
		return it.next();
	}

	public void supprimerData(HashSet<DataRobil> e){
		Iterator<Position> it = this.joueur.getPosition().iterator();
		Position posJoueur = it.next();
		e = extraireData(e, posJoueur);
	}

	public boolean pasDansLaListe(ArrayList<DataRobil> retour) {
		Iterator<DataRobil> it = retour.iterator();
		while(it.hasNext()){
			DataRobil data = it.next();
			if(data.equals(this)){
				return false;
			}
		}
		return true;
	}
	
}
