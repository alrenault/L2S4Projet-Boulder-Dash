package ia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.glass.events.KeyEvent;

import entite.Diamant;


import entite.Entite;
import entite.IA;
import moteurJeu.MoteurJeu;
import moteurJeu.MoteurJeu.Touche;
import entite.Position;
import entite.PositionTombe;

public class IA_Directive implements IA{
	
	//private Rockford j;
	private MoteurJeu moteur;
	private Entite[][] map;
	private int nbDiamRec;
	private int nbDiamRecupere = 0;
	
	public IA_Directive(MoteurJeu moteur){
	//	j = new Rockford(10000);
		this.moteur = moteur;
		map = this.moteur.getEntite();
		nbDiamRec = moteur.getNbDiamandRec();
	}
	

	@Override
	public  List<Character> actionList() {
		//List<Character> cheminTotal = new ArrayList<Character>();
		
		/*Set<Position> diamantAccessible = diamantAccessible();
		Iterator<Position> it = diamantAccessible.iterator();
		while(it.hasNext()){
			it.next();*/
			List<Character> chemin = new ArrayList<Character>();
			List<Position> pCC = plusCourtCheminDiamant(diamantAccessible());
		//	System.out.println("avant action : "+pCC.toString());
			
			
			for(int i=0;i<pCC.size()-1;i++){
				//System.out.println("LE NOMBRE !!! "+ (pCC.get(i).getX() - pCC.get(i+1).getX()));
				if(pCC.get(i).getX() - pCC.get(i+1).getX() == -1){
					chemin.add((char)KeyEvent.VK_DOWN);
				}
				else if(pCC.get(i).getX() - pCC.get(i+1).getX() == 1){
					chemin.add((char)KeyEvent.VK_UP);
				}
				else if(pCC.get(i).getY() - pCC.get(i+1).getY() == -1){
					chemin.add((char)KeyEvent.VK_RIGHT);
				}
				else if(pCC.get(i).getY() - pCC.get(i+1).getY() == 1){
					chemin.add((char)KeyEvent.VK_LEFT);
				}
				/*else
					throw new IllegalArgumentException("Deplacement impossible");*/
			}
			//cheminTotal.addAll(chemin);
		//}
		
		System.out.println("cheminTotal : "+pCC.toString());
			
		if(moteur.getNbDiamantRecolte() >= moteur.getNbDiamandRec()){
			chemin.clear();
			Iterator<Position> it = moteur.getPositionJoueur().iterator();
			Position j = null;
			if(it.hasNext())
				j = it.next();
			List<Position> cheminPorte = plusCourtCheminPorte(j/*pCC.get(pCC.size()-1)*/);
			System.out.println("chemin "+cheminPorte.toString());
			for(int i=0;i<cheminPorte.size()-1;i++){
				//System.out.println("LE NOMBRE !!! "+ (pCC.get(i).getX() - pCC.get(i+1).getX()));
				if(cheminPorte.get(i).getX() - cheminPorte.get(i+1).getX() == -1){
					chemin.add((char)KeyEvent.VK_DOWN);
				}
				else if(cheminPorte.get(i).getX() - cheminPorte.get(i+1).getX() == 1){
					chemin.add((char)KeyEvent.VK_UP);
				}
				else if(cheminPorte.get(i).getY() - cheminPorte.get(i+1).getY() == -1){
					chemin.add((char)KeyEvent.VK_RIGHT);
				}
				else if(cheminPorte.get(i).getY() - cheminPorte.get(i+1).getY() == 1){
					chemin.add((char)KeyEvent.VK_LEFT);
				}
			//chemin.addAll();
			}
			
		}
		return chemin;
		
	}
	
	/*public char[] getDirections(){
		return j.getDirections();
	}*/

	public Set<Position> diamantAccessible(){ //A VOIR SI ON PEUT PAS ENLEVER DES TRUCS
		Set<Position> diamantAccessible = new HashSet<Position>();
		
		Set<Position> atteint = new HashSet<Position>();
		int[][] poidCase = new int[moteur.getHauteurMap()][moteur.getLargeurMap()];
		
		Position posJoueur = null;
		Iterator<Position> it = moteur.getPositionJoueur().iterator();
		if(it.hasNext())
			posJoueur = it.next(); //Position initale du joueur
		atteint.add(posJoueur);
		
		for(int i=0; i<poidCase.length;i++){
			for(int j=0; j<poidCase[i].length;j++){
				poidCase[i][j] = -1; // Initialise toutes les cases à -1 pour dire que les cases ne sont pas atteintes
			}
		}
		
		poidCase[posJoueur.getX()][posJoueur.getY()] = 0;
		List<Position> fifo = new ArrayList<Position>(); //initialisation de la fifo
		fifo.add(posJoueur);
		
		Position tete = null;
		while(!fifo.isEmpty()){
			tete = fifo.get(0);
			
			Iterator<Position> it2 = voisins(tete).iterator();
			Position voisin = null;
			while(it2.hasNext()){
				voisin = it2.next();
				if(!atteint.contains(voisin)){
					atteint.add(voisin);
					poidCase[voisin.getX()][voisin.getY()] = poidCase[tete.getX()][tete.getY()]+1;
					fifo.add(voisin);
					if(moteur.getEntite()[voisin.getX()][voisin.getY()] instanceof Diamant){
						diamantAccessible.add(voisin); //profite du parcours en largeur pour créer l'ensemble des diamants accessibles
					}
				}
			}
			fifo.remove(fifo.get(0));
		}
		return diamantAccessible;
	}
	
	public List<Position> plusCourtCheminDiamant(Set<Position> diamAccess){
		
		int[][] poidCase = new int[moteur.getHauteurMap()][moteur.getLargeurMap()];
		Position posJoueur = null;
		
		List<Position> listFinale = new ArrayList<Position>();
		Set<Position> diamPossible = diamantAccessible();
		Iterator<Position> itDiam = /*diamantAccessible*/diamPossible.iterator();
		while(itDiam.hasNext()){
			itDiam.next();
			Set<Position> diamantAccessible = new HashSet<Position>();
			
			Set<Position> atteint = new HashSet<Position>();
			
			
			if(posJoueur == null){
				Iterator<Position> it = moteur.getPositionJoueur().iterator();
				if(it.hasNext())
					posJoueur = it.next(); //Position initale du joueur
			}
			else{
				posJoueur = listFinale.get(listFinale.size()-1);
			}
			atteint.add(posJoueur);
			//System.out.println("Position joueur : "+posJoueur.toString());
			
			for(int i=0; i<poidCase.length;i++){
				for(int j=0; j<poidCase[i].length;j++){
					poidCase[i][j] = -1; // Initialise toutes les cases à -1 pour dire que les cases ne sont pas atteintes
				}
			}
			
			poidCase[posJoueur.getX()][posJoueur.getY()] = 0;
			List<Position> fifo = new ArrayList<Position>(); //initialisation de la fifo
			fifo.add(posJoueur);
			
			Position tete = null;
			while(!fifo.isEmpty()){
				tete = fifo.get(0);
				
				Iterator<Position> it2 = voisins(tete).iterator();
				Position voisin = null;
				while(it2.hasNext()){
					voisin = it2.next();
					if(!atteint.contains(voisin)){
						atteint.add(voisin);
						poidCase[voisin.getX()][voisin.getY()] = poidCase[tete.getX()][tete.getY()]+1;
						fifo.add(voisin);
						if(moteur.getEntite()[voisin.getX()][voisin.getY()] instanceof Diamant){
							diamantAccessible.add(voisin); //profite du parcours en largeur pour créer l'ensemble des diamants accessibles
						}
					}
				}
				fifo.remove(fifo.get(0));
			}
		
		//Trouver le diamant le plus proche
		int tailleChemin = (int)Double.POSITIVE_INFINITY;
		Iterator<Position> it3 = diamAccess.iterator();
		Position diamant;
		Position posDiamantProche = new Position(0,0);
		
		while(it3.hasNext()){
			diamant = it3.next();
			if(poidCase[diamant.getX()][diamant.getY()] < tailleChemin){
				tailleChemin = poidCase[diamant.getX()][diamant.getY()];
				posDiamantProche.setX(diamant.getX());
				posDiamantProche.setY(diamant.getY());
			}
		}
		diamAccess.remove(posDiamantProche);
		
		//construction du chemin
		List<Position> listPos = new ArrayList<Position>();
		listPos.add(posDiamantProche);
		Position posActuelle = new Position(posDiamantProche.getX(),posDiamantProche.getY());
		
		for(int i= tailleChemin;i>0;i--)/*while(tailleChemin>=0)*/{
			Position suivant;
			if(poidCase[posActuelle.getX()][posActuelle.getY()+1] == i-1){
				suivant = new Position(posActuelle.getX(),posActuelle.getY()+1);
				listPos.add(suivant);
			}
			else if(poidCase[posActuelle.getX()][posActuelle.getY()-1] == i-1){
				suivant = new Position(posActuelle.getX(),posActuelle.getY()-1);
				listPos.add(suivant);
			}
			else if(poidCase[posActuelle.getX()+1][posActuelle.getY()] == i-1){
				suivant = new Position(posActuelle.getX()+1,posActuelle.getY());
				listPos.add(suivant);
			}
			else /*if(poidCase[posActuelle.getX()-1][posActuelle.getY()] == tailleChemin-1)*/{
				suivant = new Position(posActuelle.getX()-1,posActuelle.getY());
				listPos.add(suivant);
			}
			posActuelle.setX(suivant.getX());
			posActuelle.setY(suivant.getY());
			//System.out.println(suivant);
				
		}
		listPos = reverse(listPos);
		listFinale.addAll(listPos);
		nbDiamRecupere++;
		if(nbDiamRecupere >= nbDiamRec)
			break;
		}

		/*for(int i=0;i<poidCase.length;i++){
			for(int j=0;j<poidCase[i].length;j++){
				System.out.print(poidCase[i][j]+" ");
			}
			System.out.println();
		}
		
		Iterator<Position> it4 = listFinale.iterator();
		while(it4.hasNext()){
			System.out.println(it4.next());
		}*/
		return listFinale;
	}
	
	public List<Position> plusCourtCheminPorte(Position posInit){
		
		Position porte = new Position(moteur.getPosPorte().getX(),moteur.getPosPorte().getY());
		List<Position> listPos = new ArrayList<Position>();
		
		Set<Position> atteint = new HashSet<Position>();
		int[][] poidCase = new int[moteur.getHauteurMap()][moteur.getLargeurMap()];
		atteint.add(posInit);
		
		for(int i=0; i<poidCase.length;i++){
			for(int j=0; j<poidCase[i].length;j++){
				poidCase[i][j] = -1; // Initialise toutes les cases à -1 pour dire que les cases ne sont pas atteintes
			}
		}
		
		poidCase[posInit.getX()][posInit.getY()] = 0;
		List<Position> fifo = new ArrayList<Position>(); //initialisation de la fifo
		fifo.add(posInit);
		
		Position tete = null;
		while(!fifo.isEmpty()){
			tete = fifo.get(0);
			
			Iterator<Position> it2 = voisins(tete).iterator();
			Position voisin = null;
			while(it2.hasNext()){
				voisin = it2.next();
				if(!atteint.contains(voisin)){
					atteint.add(voisin);
					poidCase[voisin.getX()][voisin.getY()] = poidCase[tete.getX()][tete.getY()]+1;
					fifo.add(voisin);
				}
			}
			fifo.remove(fifo.get(0));
		}
		
		int tailleChemin = poidCase[porte.getX()][porte.getY()];
		
		//construction du chemin
	/*	List<Position> listPos = new ArrayList<Position>();
		listPos.add(posDiamantProche);
		Position posActuelle = new Position(posDiamantProche.getX(),posDiamantProche.getY());*/
		System.out.println("test porte "+porte.toString());
		Position laPorte = new Position(porte.getX(),porte.getY());
		listPos.add(laPorte);
		Position posActuelle = porte;
		for(int i= tailleChemin;i>0;i--)/*while(tailleChemin>=0)*/{
			System.out.println("la liste "+listPos.toString());
			Position suivant;
			if(poidCase[posActuelle.getX()][posActuelle.getY()+1] == i-1){
				suivant = new Position(posActuelle.getX(),posActuelle.getY()+1);
				listPos.add(suivant);
			}
			else if(poidCase[posActuelle.getX()][posActuelle.getY()-1] == i-1){
				suivant = new Position(posActuelle.getX(),posActuelle.getY()-1);
				listPos.add(suivant);
			}
			else if(poidCase[posActuelle.getX()+1][posActuelle.getY()] == i-1){
				suivant = new Position(posActuelle.getX()+1,posActuelle.getY());
				listPos.add(suivant);
			}
			else /*if(poidCase[posActuelle.getX()-1][posActuelle.getY()] == tailleChemin-1)*/{
				suivant = new Position(posActuelle.getX()-1,posActuelle.getY());
				listPos.add(suivant);
			}
			System.out.println("AU SUIVANT : "+suivant);

			posActuelle.setX(suivant.getX());
			posActuelle.setY(suivant.getY());
			//System.out.println(suivant);
			
		}
		listPos = reverse(listPos);
		
		/*listFinale.addAll(listPos);
		nbDiam++;
		if(nbDiam >= nbDiamRec)
			break;
		}*/

		/*System.out.println("Pour la porte");
		for(int i=0;i<poidCase.length;i++){
			for(int j=0;j<poidCase[i].length;j++){
				System.out.print(poidCase[i][j]+" ");
			}
			System.out.println();
		}
		
		Iterator<Position> it4 = listPos.iterator();
		while(it4.hasNext()){
			System.out.println(it4.next());
		}*/
		return listPos;
		
	}
	
	public Set<Position> voisins(Position p){
		Set<Position> voisins = new HashSet<Position>();
		int x = p.getX();
		int y = p.getY();
		
		if(moteur.caseLibre(x, y+1))
			voisins.add(new Position(x,y+1));
		if(moteur.caseLibre(x, y-1))
			voisins.add(new Position(x,y-1));
		if(moteur.caseLibre(x+1, y))
			voisins.add(new Position(x+1,y));
		if(moteur.caseLibre(x-1, y))
			voisins.add(new Position(x-1,y));
		
		return voisins;
	}
	
	public List<Position> reverse(List<Position> posList) {
	    for(int i = 0, j = posList.size() - 1; i < j; i++) {
	        posList.add(i, posList.remove(j));
	    }
	    return posList;
	}
	
}
