package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Font;

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
	//assuprimer
	private int nbreCoups = 0;
	
	private enum ImagesJeu{
		MUR_BASIQUE (chargerImage("src/BoulderDashImages/Mur.png")),
		MUR_MAGIQUE (chargerImage("src/BoulderDashImages/MurMagique.png")),
		MUR_TITANE (chargerImage("src/BoulderDashImages/MurTitane.png")),
		ROCHER (chargerImage("src/BoulderDashImages/Rocher.png")),
		ROBIL (chargerImage("src/BoulderDashImages/Robil.png")),
		LIBELLULE (chargerImage("src/BoulderDashImages/Libellule1.png")),
		LUCIOLE (chargerImage("src/BoulderDashImages/Carre1.png")),
		AMIBE (chargerImage("src/BoulderDashImages/Amibe.png")),
		SORTIE (chargerImage("src/BoulderDashImages/Sortie2.png")),
		DIAMANT (chargerImage("src/BoulderDashImages/Diamant.png")),
		POUSSIERE (chargerImage("src/BoulderDashImages/Poussiere.png")),
		SOL (chargerImage("src/BoulderDashImages/Sol2.png")),
		MENU (chargerImage("src/BoulderDashImages/Menu.png")),
		DEFAULT (chargerImage("src/BoulderDashImages/Default.png"));
		
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
  
	/**
	 * Charge une image sans etre affectee par la transformation du projet en .jar
	 * */
	public static Image chargerImage(String path){
		Image img =null;
		try {
			img = (Image) ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
		}catch (FileNotFoundException e) {e.printStackTrace();
		}catch (IOException e) {e.printStackTrace();}
		
		return img;
	}
	
	/*private static Image chargerImage(String adresse){
		Image img = null;
		try {
		    img = ImageIO.read(new File(adresse));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}*/
	
	
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
	public void incrementerNbreCoups() {
		this.nbreCoups++;
	}
	
	/***/
	private Graphics dessinerMenu(Graphics g, int nbreDiamants, int nbreCoups) {
		
		g.drawImage(ImagesJeu.MENU.get(),(this.getWidth()/2)-TAILLE*10,0,TAILLE*20,TAILLE,null);
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF,1,15));
		g.drawString(""+nbreDiamants,(this.getWidth()/2)-TAILLE*10+104,16);
		g.drawString(""+nbreCoups,(this.getWidth()/2)-TAILLE*10+220,16);
		g.drawString(""+0,(this.getWidth()/2)-TAILLE*10+384,16);
		return g;
	}
	
	/***/
	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
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
		
		//barre de menu
		g=dessinerMenu(g,5,nbreCoups);
	}

}
