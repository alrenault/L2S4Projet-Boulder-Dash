package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entite.*;
import moteurJeu.MoteurJeu;

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
	private final int TAILLE=24;
	
	private enum ImagesJeu{
		MUR_BASIQUE (chargerImage("BoulderDashImages/Mur.png")),
		MUR_MAGIQUE (chargerImage("BoulderDashImages/MurMagique.png")),
		MUR_TITANE (chargerImage("BoulderDashImages/MurTitane.png")),
		ROCHER (chargerImage("BoulderDashImages/Rocher.png")),
		ROBIL (chargerImage("BoulderDashImages/Robil.png")),
		LIBELLULE (chargerImage("BoulderDashImages/Libellule1.png")),
		LUCIOLE (chargerImage("BoulderDashImages/Carre1.png")),
		AMIBE (chargerImage("BoulderDashImages/Amibe.png")),
		SORTIE (chargerImage("BoulderDashImages/Sortie.png")),
		DIAMANT (chargerImage("BoulderDashImages/Diamant.png")),
		POUSSIERE (chargerImage("BoulderDashImages/Poussiere.png")),
		SOL (chargerImage("BoulderDashImages/Sol2.png")),
		MENU (chargerImage("BoulderDashImages/Menu.png")),
		DEFAULT (chargerImage("BoulderDashImages/Default.png"));
		
		private Image img;
		ImagesJeu(Image img){
			this.img=img;
		}
		public Image get(){
			return img;
		}
	}
	
	//Images img=Images.MUR_BASIQUE;

	/***/
	PanneauBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		Entite[][] tab=moteur.getMap();
		largeurGrille=tab.length;
		longueurGrille=tab[0].length;
		this.setSize((int)(longueurGrille*TAILLE*1.2),(int)(largeurGrille*TAILLE*1.2));
	}
	/***/
	/*private Color reconnaitreCouleur(Entite entite){
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
	}*/
  
	private static Image chargerImage(String adresse){
		Image img = null;
		try {
		    img = ImageIO.read(new File(adresse));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	/***/
	private Image recupererImage(Entite entite){
		if(entite instanceof MurBasique){
			return ImagesJeu.MUR_BASIQUE.get();
		}else if(entite instanceof MurMagique){
			return ImagesJeu.MUR_MAGIQUE.get();
		}else if(entite instanceof MurTitane){
			return ImagesJeu.MUR_TITANE.get();
		}else if(entite instanceof Diamant){
			return ImagesJeu.DIAMANT.get();
		}else if(entite instanceof Poussiere){
			return ImagesJeu.POUSSIERE.get();
		}else if(entite instanceof Joueur){
			return ImagesJeu.ROBIL.get();
		}else if(entite instanceof Exit){
			return ImagesJeu.SORTIE.get();
		}else if(entite instanceof Roc){
			return ImagesJeu.ROCHER.get();
		}else if(entite instanceof Luciole){
			return ImagesJeu.LUCIOLE.get();
		}else if(entite instanceof Libellule){
			return ImagesJeu.LIBELLULE.get();
		}else if(entite instanceof Amibe){
			return ImagesJeu.AMIBE.get();
		}else{
			return ImagesJeu.DEFAULT.get();
		}
	}
	
	/***/
	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//barre de menu
		g.drawImage(ImagesJeu.MENU.get(),(this.getWidth()/2)-TAILLE*5,0,TAILLE*10,TAILLE,null);
		
		//moteur.jeu('r');
		debutCaseX=(getWidth()/2)-((longueurGrille*TAILLE)/2);
		debutCaseY=(getHeight()/2)-((largeurGrille*TAILLE)/2);
		Entite[][] tab=moteur.getMap();
		
		for(int i=0;i<largeurGrille;i++){
			for(int j=0;j<longueurGrille;j++){
				//g.setColor(reconnaitreCouleur(tab[i][j]));
				/**/
				//g.fillRect(debutCaseX+TAILLE*j+j, debutCaseY+TAILLE*i+i, TAILLE, TAILLE);
				g.drawImage(ImagesJeu.SOL.get(), debutCaseX+TAILLE*j+j, debutCaseY+TAILLE*i+i, TAILLE, TAILLE, null);
				g.drawImage(recupererImage(tab[i][j]), debutCaseX+TAILLE*j+j, debutCaseY+TAILLE*i+i, TAILLE, TAILLE, null);
			}
		}
	}

}
