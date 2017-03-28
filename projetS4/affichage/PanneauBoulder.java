package projetS4.affichage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import projetS4.moteurJeu.MoteurJeu;

/**
 * 
 */
public class PanneauBoulder extends JPanel{

	private static final long serialVersionUID = 1L;
	private MoteurJeu moteur;
	private int longueurGrille=0;
	private int largeurGrille=0;
	private int debutCaseX=0;
	private int debutCaseY=0;
	private final int TAILLE=50;

	/***/
	PanneauBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		char[][] tab=moteur.getTab();
		longueurGrille=tab.length;
		largeurGrille=tab[0].length;
	}
	
	
	/***/
	public void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		moteur.jeu('r');
		debutCaseX=(getWidth()/2)-((longueurGrille*TAILLE)/2);
		debutCaseY=(getHeight()/2)-((largeurGrille*TAILLE)/2);
		char[][] tab=moteur.getTab();
		
		for(int i=0;i<longueurGrille;i++){
			for(int j=0;j<largeurGrille;j++){
				if(tab[i][j]!='t'){
					g.setColor(Color.BLUE);
				}else if((i+j)%2==0){
					g.setColor(Color.YELLOW);
				}else{
					g.setColor(Color.GREEN);
				}
				g.fillRect(debutCaseX+TAILLE*i+i, debutCaseY+TAILLE*j+j, TAILLE, TAILLE);
			}
		}
	}

}
