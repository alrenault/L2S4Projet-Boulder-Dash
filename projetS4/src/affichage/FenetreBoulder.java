package affichage;

import javax.swing.JFrame;

import moteurJeu.MoteurJeu;

/**
 * Classe gerant l'affichage graphique. 
 * Cree une fenetre a l'instanciation qui contient tout les composants du jeu ainsi
 * que son contenu affiche.
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class FenetreBoulder extends JFrame{
	
	/**
	 * Reference vers le moteur de jeu
	 */
	private MoteurJeu moteur=null;
	
	/**
	 * Variable pour serialiser
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Panneau d'affichage du jeu
	 */
	private PanneauBoulder panneauBoulder;
	
	/**
	 * Permet d'enregistrer les touches entrees au clavier
	 */
	private EcouteurTouche ecouteurTouche;
	
	/**
	 * Constructeur de la classe FenetreBOulder.
	 * @param moteur Reference vers le moteur de jeu
	 * */
	public FenetreBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		panneauBoulder=new PanneauBoulder(moteur);
		ecouteurTouche=new EcouteurTouche(this);
		
		this.add(panneauBoulder);
		this.addKeyListener(ecouteurTouche);
		this.setJMenuBar(new MenuBar(this));
		
		this.setBounds(50, 100, panneauBoulder.getWidth(), panneauBoulder.getHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Ecrit un message dans le panneau
	 * @param message Le message a afficher
	 * @param duree Le nombre de tour ou le message reste affiche
	 * */
	public void ecrireMessage(String message, int duree){
		panneauBoulder.ecrireMessage(message, duree);
	}

	/**
	 * Affiche le message de victoire du panneau quand le joueur a fini la derniere map.
	 * */
	public void afficherMessageVictoire(){
		panneauBoulder.afficherMessageVictoire();
	}
	
	/**
	 * Efface le message de victoire
	 * */
	public void effacerMessageVictoire() {
		panneauBoulder.effacerMessageVictoire();
	}
	
	/**
	 * Retourne la reference vers le moteur.
	 * @return Retourne le moteur
	 * */
	public MoteurJeu getMoteur() {
		return moteur;
	}
}
