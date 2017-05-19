package ia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JSpinner.ListEditor;

import com.sun.glass.events.KeyEvent;

import entite.Position;

import moteurJeu.Convertisseur;
import moteurJeu.DataRobil;
import moteurJeu.MoteurJeu;

public class IA_Parfaite {
	MoteurJeu moteur;
	char[] solution;
	private char[] mouvements = {KeyEvent.VK_RIGHT,KeyEvent.VK_LEFT,
			KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_0};
	//private int indiceMouvement = 0;
	
	public IA_Parfaite(int n, MoteurJeu moteur){
		this.moteur=moteur;
		//DataRobil dr = new DataRobil(moteur);
		//System.out.println("IAParf to str : \n"+dr);
		
	}

	/**
	 * Lance l'analyse du chemin pour l'IA parfaite.
	 * @param n
	 * @return
	 */
	public boolean lancerAnalyse(int n){
		HashSet<DataRobil> robils = new HashSet<DataRobil>();//les robils en concurrences
		robils.add(new DataRobil(moteur));//Le point de depart de l'IA
		HashSet<DataRobil> selectionnees = new HashSet<DataRobil>();//les robils arrives a destination
		for(int i=0;i<n;i++){//pour n deplacements
			afficheLesRobils(robils, i);//assuprimer ou pour debug
			HashSet<DataRobil> copieRobils = new HashSet<DataRobil>(robils);
			Iterator<DataRobil> it = copieRobils.iterator();//copie les robils pour garder en memoire le tour precedent
			for(int j=0;j<copieRobils.size();j++){//pour chaque robils du tour precedent
				DataRobil unRobil = it.next();//extrait un robil
				DataRobil[] ret = deplacementsRobil(unRobil);//fait les 5 deplacements du robil et garde ceux qui vivent
				//unRobil.supprimerData(robils);
				//System.out.println("testavant : "+robils.size());
				unRobil.supprimerData(robils);
				//System.out.println("testapres : "+robils.size());
				for(int k=0;k<ret.length;k++){
					robils.add(ret[k]);
					//moteur.chargerDonnees(ret[k]);
					if(ret[k].estSolution()){//si un des 5 robils est solution, ajoute-le aux solutions
						//selectionnees.add(ret[k]);
						//ret[k].supprimerData(robils);//supprime le robil solution pour eviter de le re-multiplier
						System.out.println("SOLUTION !!");
						solution = new char[ret[k].listeDeplacements.size()];
						for(int inc=0;inc<ret[k].listeDeplacements.size();inc++){
							//char sortie = Convertisseur.convertirVK_vers_6482(ret[k].listeDeplacements.get(inc));
							solution[inc] = ret[k].listeDeplacements.get(inc);
							System.out.println("sol "+inc+" : "+solution[inc]);
						}
						System.out.println("CA MARCHE !!!!!!!");
						synchronized(moteur.thread){
							try {
								moteur.thread.wait();
							} catch (InterruptedException exp) {
								exp.printStackTrace();
							}
						}
						return true;
					}//si est solution
				}//pour les 5 deplacements
			}//pour les robils du tour precedent
			supprimerDoublons(robils);//supprime les robils iddentiques pour eviter l'explosion combinatoire
			System.out.println("Il y a "+robils.size()+" robils");
		}//pour n deplacements
		 //solution = meilleur(selectionnees).solution();//recupere la solution du meilleur
		 return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moteur == null) ? 0 : moteur.hashCode());
		result = prime * result + Arrays.hashCode(mouvements);
		result = prime * result + Arrays.hashCode(solution);
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
		IA_Parfaite other = (IA_Parfaite) obj;
		if (moteur == null) {
			if (other.moteur != null)
				return false;
		} else if (!moteur.equals(other.moteur))
			return false;
		if (!Arrays.equals(mouvements, other.mouvements))
			return false;
		if (!Arrays.equals(solution, other.solution))
			return false;
		return true;
	}

	private void afficheLesRobils(HashSet<DataRobil> robils,int n) {
		//pour le debugage de l'IA_Parfaite
		if(MoteurJeu.MODE_DEBUG_PARFAITE){
			Iterator<DataRobil> it = robils.iterator();
			System.out.println("\n___Tour "+n+" : ___\n");
			while(it.hasNext()){
				System.out.println("->"+it.next());
			}
			moteur.getFenetre().repaint();
			synchronized(moteur.thread){
				try {
					moteur.thread.wait();
				} catch (InterruptedException exp) {
					exp.printStackTrace();
				}
			}
		}
	}
	
	public char[] recupererSolution(){
		return solution;
	}

	private DataRobil meilleur(HashSet<DataRobil> selectionnees) {
		Iterator<DataRobil> it = selectionnees.iterator();
		DataRobil meilleur=null;
		if(it.hasNext()){//charge le premier meilleur
			meilleur = it.next();
		}else{
			return null;
		}
		while(it.hasNext()){
			DataRobil unRobil = it.next();
			if(unRobil.compare(meilleur) > 0){//sortie meilleure
				meilleur = unRobil;
			}
		}
		return meilleur;
	}

	private void supprimerDoublons(HashSet<DataRobil> robils) {
		ArrayList<DataRobil> liste = new ArrayList<DataRobil>(robils);
		ArrayList<DataRobil> retour = new ArrayList<DataRobil>();
		DataRobil meilleur = null;
		for(int i=0;i<liste.size();i++){//pour les elements de la liste
			if(liste.get(i).pasDansLaListe(retour)){
				meilleur = liste.get(i);
				for(int j=i+1;j<liste.size();j++){//pour les elements suivants
					/*System.out.println("liste.get(j) : "+liste.get(j));
					System.out.println("liste.get(i) : "+liste.get(i));
					System.out.println("meilleur : "+meilleur);*/
					if(liste.get(j).equals(meilleur)){
						System.out.println("Fait la comparaison pour : ");
						System.out.println("liste.get(j) : "+liste.get(j));
						System.out.println("meilleur : "+meilleur);
						if(liste.get(j).compare(meilleur)>0){//j meilleur 
							System.out.println("Comparaison Vraie_____________");
							meilleur = liste.get(j);
						}//if compare
					}
				}//elements suivants
				retour.add(meilleur);
			}//pas dans la liste
		}//tout les elements
		robils.clear();
		robils.addAll(retour);//supprime les anciens elements et en ajoute de nouveaux
	}

	private DataRobil[] deplacementsRobil(DataRobil robil) {
		HashSet<DataRobil> gardes = new HashSet<DataRobil>();
		for(int i=0;i<5;i++){//pour 5 directions
			robil.chargerDonnees(moteur);//charge les donnees sauvegardees dans le moteur
			//Iterator<Position> it= moteur.joueur.getPosition().iterator();
			moteur.affichage();
			moteur.tour(mouvementSuivant(i), moteur.processPosition());//joue un tour
			moteur.processEndOfTurn();
			moteur.getFenetre().repaint();
			if(MoteurJeu.MODE_DEBUG_PARFAITE){
				synchronized(moteur.getFenetre().getMoteur().thread){
					try {
						Thread.sleep(50);
					} catch (InterruptedException exp) {
						exp.printStackTrace();
					}
				}
			}
			if(!moteur.isaPerdu()){//si le joueur n'a pas perdu, sauvegarde la direction
				DataRobil data = new DataRobil(moteur,robil.listeDeplacements);
				//System.out.println("data créee ( deplacement robil )"+data);
				if(mouvementSuivant(i) != KeyEvent.VK_0){
					data.listeDeplacements.add(mouvementSuivant(i));
				}
				gardes.add(data);
			}
		}
		DataRobil[] retour = new DataRobil[gardes.size()];
		int indice = 0;
		Iterator<DataRobil> it=gardes.iterator();
		while(it.hasNext()){//met les nouveaux DataRobils dans un tableau retour
			retour[indice]=it.next();
			System.out.println("deplacement robil retour "+indice+" : "+retour[indice]);
			indice++;
		}
		return retour;
	}

	private char mouvementSuivant(int i) {
		if(i > 5){
			throw new IllegalStateException("i trop grand ( > a 5)");
		}
		return mouvements[i];
	}

	
	
}
