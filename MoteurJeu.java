package projetS4;

public class MoteurJeu {

	public static final int OBSTACLE_MORTEL=1;
	public static final int TOUCHER_MORTEL=2;
	Objet[] objets;
	Ennemi[] tabEnnemis;
	Joueur joueur;
	Roc[] tabRochers;
	
	public void jeu(Map map){
		while(true){
			//recupere la touche et tente un deplacement du joueur
			char touche=recupererTouche();
			if(joueur.deplacementPossible(touche)){
				joueur.deplacer();
			}
			perdu(OBSTACLE_MORTEL);
			joueur.gagne();
			joueur.prendObjets();
			//joueur.sortie();
			//deplace ensuite les rochers ( attention en dessous ! )
			deplacerRochers(tabRochers);
			perdu(TOUCHER_MORTEL);
			ennemisMorts(TOUCHER_MORTEL);
			//deplace finalement les ennemis
			deplacerEnnemis(tabEnnemis);
			ennemisMorts(OBSTACLE_MORTEL);
			perdu(TOUCHER_MORTEL);
			//afficher le jeu
			afficherJeu();
		}
	}
	private void perdu(int toucherMortel) {
		// TODO Auto-generated method stub
		
	}
	private void ennemisMorts(int toucherMortel) {
		// TODO Auto-generated method stub
		
	}
	private void afficherJeu() {
		// TODO Auto-generated method stub
		
	}
	private void deplacerEnnemis(Ennemi[] tabEnnemis2) {
		// TODO Auto-generated method stub
		
	}
	private void deplacerRochers(Roc[] tabRochers2) {
		// TODO Auto-generated method stub
		
	}
	private char recupererTouche() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
