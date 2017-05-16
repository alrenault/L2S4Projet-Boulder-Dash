package affichage;

import javax.swing.JFrame;

import moteurJeu.MoteurJeu;

/**
 * La classe qui gere l'affichage graphique. 
 * Cree une fenetre a l'instanciation qui contient tout les composants du jeu ainsi
 * que son contenu affiche.
 * @author PITROU Adrien
 * @since 14/04/17
 * @version 1.0
 */
public class FenetreBoulder extends JFrame{
	private MoteurJeu moteur=null;
	private static final long serialVersionUID = 1L;
	private PanneauBoulder panneauBoulder;
	private EcouteurTouche ecouteurTouche;
	
	/**
	 * Le constructeur de la classe.
	 * Prend en parametre une instance du moteur avec qui il va converser.
	 * @param MoteurJeu moteur
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
	 * @param String message, int duree
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
	 * */
	public MoteurJeu getMoteur() {
		return moteur;
	}
}
