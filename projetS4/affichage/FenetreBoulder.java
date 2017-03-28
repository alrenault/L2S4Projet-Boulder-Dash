package projetS4.affichage;

import javax.swing.JFrame;

import projetS4.moteurJeu.MoteurJeu;

/**
 * 
 */
public class FenetreBoulder extends JFrame{
	private MoteurJeu moteur;
	private static final long serialVersionUID = 1L;
	private PanneauBoulder panneauBoulder;
	private EcouteurTouche ecouteurTouche;
	
	/***/
	public FenetreBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		panneauBoulder=new PanneauBoulder(moteur);
		ecouteurTouche=new EcouteurTouche(moteur);
		
		this.add(panneauBoulder);
		this.addKeyListener(ecouteurTouche);
		
		this.setBounds(100, 100, 200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new FenetreBoulder(new MoteurJeu());

	}

}
