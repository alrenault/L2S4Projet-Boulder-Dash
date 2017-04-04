package projetS4.affichage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import projetS4.moteurJeu.MoteurJeu;
import projetS4.entite.*;

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
	private final int TAILLE=20;

	/***/
	PanneauBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		Entite[][] tab=moteur.getMap();
		largeurGrille=tab.length;
		longueurGrille=tab[0].length;
		this.setSize((int)(longueurGrille*TAILLE*1.5),(int)(largeurGrille*TAILLE*1.5));
	}
	/***/
	private Color reconnaitreCouleur(Entite entite){
		if(entite instanceof MurBasique || entite instanceof MurMagique || entite instanceof MurTitane){
			return Color.BLUE;
		}else if(entite instanceof Diamant){
			return Color.YELLOW;
		}else if(entite instanceof Espace){
			return Color.WHITE;
		}else if(entite instanceof Poussiere){
			return Color.PINK;
		}else if(entite instanceof Joueur || entite instanceof Exit){
			return Color.CYAN;
		}else if(entite instanceof Roc){
			return Color.GRAY;
		}else if(entite instanceof Luciole || entite instanceof Libellule){
			return new Color(255,0,255);
		}else{
			return Color.GREEN;
		}
	}
	
	/***/
	public void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		//moteur.jeu('r');
		debutCaseX=(getWidth()/2)-((longueurGrille*TAILLE)/2);
		debutCaseY=(getHeight()/2)-((largeurGrille*TAILLE)/2);
		Entite[][] tab=moteur.getMap();
		
		for(int i=0;i<largeurGrille;i++){
			for(int j=0;j<longueurGrille;j++){
				g.setColor(reconnaitreCouleur(tab[i][j]));
				/**/
				g.fillRect(debutCaseX+TAILLE*j+j, debutCaseY+TAILLE*i+i, TAILLE, TAILLE);
			}
		}
	}

}
