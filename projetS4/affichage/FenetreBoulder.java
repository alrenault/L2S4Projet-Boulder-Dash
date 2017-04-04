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
		
		this.setBounds(50, 100, panneauBoulder.getWidth(), panneauBoulder.getHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
