package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entite.*;
import moteurJeu.MoteurJeu;

/**
 * Classe correspondant au panneau d'affichage du jeu. Chaque appel a repaint()
 * va raffraichir cette classe qui va recalculer l'affichage a modifier.
 * @author PITROU Adrien
 * @since 14/04/17
 * @version 1.0
 */
public class PanneauBoulder extends JPanel{

	private static final long serialVersionUID = 1L;
	private MoteurJeu moteur;
	private int longueurGrille=0;
	private int largeurGrille=0;
	private int debutCaseX=0;
	private int debutCaseY=0;
	private final int TAILLE=24;
	private String message="coucou";
	private int duree=2;
	
	/**
	 * Une enumeration qui contient les URL vers les images du jeu.
	 * */
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
		EXPLOSION (chargerImage("src/BoulderDashImages/RocherExplose1.png")),
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

	/**
	 * La constructeur de la classe
	 * @param MoteurJeu moteur
	 * */
	PanneauBoulder(MoteurJeu moteur){
		this.moteur=moteur;
		raffraichirLongueurEtLargeur();
		this.setSize((int)(longueurGrille*TAILLE*1.2),(int)(largeurGrille*TAILLE*1.3));
		this.addMouseListener(new EcouteurTouche());
	}
  
	private void raffraichirLongueurEtLargeur(){
		Entite[][] tab=moteur.getEntite();
		largeurGrille=tab.length;
		longueurGrille=tab[0].length;
	}
	
	
	
	/**
	 * Charge une image sans etre affectee par la transformation du projet en .jar
	 * */
	/*
	public static Image chargerImage(String path){
		Image img =null;
		try {
			
			img = (Image) ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
		}catch (FileNotFoundException e) {e.printStackTrace();
		}catch (IOException e) {e.printStackTrace();}
		
		return img;
	}*/
	
	/**
	 * Effectue le chargement des images du jeu.
	 * @param String adresse
	 * @return Image image
	 * */
	private static Image chargerImage(String adresse){
		Image img = null;
		try {
		    img = ImageIO.read(new File(adresse));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * Ecrit un message sur la fenetre d'une duree duree
	 * @param String message, int duree
	 * */
	public void ecrireMessage(String message, int duree){
		this.message=message;
		this.duree=duree;
	}
	
	/**
	 * Recupere l'image associee a une Entite
	 * @param Entite entite
	 * @return Image image
	 * */
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
		}else if(entite instanceof Explosion){
			return ImagesJeu.EXPLOSION.get();
		}else{
			return ImagesJeu.DEFAULT.get();
		}
	}
	
	/**
	 * Dessine la barre de menu du jeu
	 * @param Graphics g
	 * @return Un Graphics avec la barre de menu.
	 * */
	private Graphics dessinerMenu(Graphics g) {
		
		g.drawImage(ImagesJeu.MENU.get(),(this.getWidth()/2)-TAILLE*10,0,TAILLE*20,TAILLE,null);
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF,1,15));
		g.drawString(""+moteur.getNombreDiamants(),(this.getWidth()/2)-TAILLE*10+104,16);
		g.drawString(""+moteur.getNombreTour(),(this.getWidth()/2)-TAILLE*10+220,16);
		g.drawString(""+moteur.getScore(),(this.getWidth()/2)-TAILLE*10+384,16);
		return g;
	}
	
	/**
	 * Redessine le jeu quand appele par repaint()
	 * @param Graphics g
	 * */
	public void paintComponent(Graphics g){
		//au cas ou la grille n'aurait pas la meme taille que la precedente
		raffraichirLongueurEtLargeur();
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//moteur.jeu('r');
		debutCaseX=(getWidth()/2)-((longueurGrille*TAILLE)/2);
		debutCaseY=(getHeight()/2)-((largeurGrille*TAILLE)/2);
		Entite[][] tab=moteur.getEntite();
		
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
		g=dessinerMenu(g);
		
		//messages
		if(duree>0){
			g.setFont(new Font("Arial",1, 20));
			g.drawString(message, 15, getHeight()-15);
			duree--;
		}
	}
	
	public class EcouteurTouche implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent evt) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
			
		/**
		 * Met le jeu en pause quand le joueur clique sur un onglet de la MenuBar
		 * */
		@Override
		public void mousePressed(MouseEvent evt) {
			synchronized(moteur.thread){
				//fenetre.getMoteur().thread.wait();
				if(moteur.enPause()){
					moteur.repriseIA();
					repaint();
					moteur.thread.notify();
				}else{
					moteur.pauseIA();
					repaint();
				}
			}
		}
	}
}
