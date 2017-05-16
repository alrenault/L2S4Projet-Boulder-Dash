package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import moteurJeu.Convertisseur;
import moteurJeu.MoteurJeu.Intelligence;

public class MenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	private FenetreBoulder fenetre;
	
	public MenuBar(FenetreBoulder fenetre){
		this.fenetre=fenetre;
		this.add(new Fichier());
		this.add(new ChangerIA());
		this.add(new ChangerCarte());
		this.addMouseListener(new EcouteurTouche());
	}
	
	public class Fichier extends JMenu{
		private static final long serialVersionUID = 1L;
		public Fichier(){
			this.setText("Fichier");
			this.add(new NouvellePartie());
			this.add(new Rejouer());
			this.addMouseListener(new EcouteurTouche());
		}
		/**
		 * Onglet de nouvelle partie.
		 * */
		public class NouvellePartie extends JMenuItem{
			private static final long serialVersionUID = 1L;

			public NouvellePartie(){
				this.setText("NouvellePartie");
				this.addActionListener(new actionNouvellePartie());
			}
			
			public class actionNouvellePartie implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Le bouton de nouvelle partie");
					fenetre.getMoteur().resetMap();
					fenetre.ecrireMessage("nouvelle partie", 1);
					fenetre.repaint();
				}
			}
		}//NouvellePartie
		/**
		 * Menu Rejouer
		 * */
		public class Rejouer extends JMenu{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public Rejouer(){
				this.setText("Rejouer");
				this.add(new RejouerFichier());
			}
			
			/**
			 * Onglet Rejouer un fichier
			 * */
			private class RejouerFichier extends JMenuItem{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				public RejouerFichier(){
					this.setText("Rejouer une partie enregistree");
					this.addActionListener(new ActionLecture());
				}
				
				private class ActionLecture implements ActionListener{
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser choix = new JFileChooser();
						choix.setCurrentDirectory(new File("src"));
						String path="";
						int retour=choix.showOpenDialog(null);
						//si une option correcte est saisie
						if(retour==JFileChooser.APPROVE_OPTION){
						   path = choix.getSelectedFile().getAbsolutePath();
						   Scanner sc;
							String ligne = "";
							//tente de recuperer la premiere ligne du fichier sauvegarde
							try {
								sc = new Scanner(new File(path));
								ligne = sc.nextLine();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							char[] tabChara = new char[ligne.length()];
							
							//remplit le tableau de deplacements avec les caracteres de la chaine
							for(int i=0;i<ligne.length();i++){
								char chara = ligne.charAt(i);
								System.out.println("Prochain cara : "+chara);
								tabChara[i]=chara;
							}
							//convertit du format dghb en vk
							Convertisseur.convTabDGHB_vers_VK(tabChara);
							
							//lance l' IA Rejoue
							fenetre.getMoteur().rejouerPartie(tabChara);
						}
					}
				}//ActionLecture
			}//RejouerFichier
		}//Rejouer
	}//Fichier
	
	/**
	 * Menu de changement des IA
	 * */
	public class ChangerIA extends JMenu{
		private static final long serialVersionUID = 1L;

		public ChangerIA(){
			this.setText("Changer IA");
			for(int i=0;i<8;i++){
				//menu IA simplette
				if(i==2){
					this.add(menuIA());
					i++;
				}else{
					this.add(new IA(i));
				}
			}
			this.addMouseListener(new EcouteurTouche());
		}
		
		private JMenu menuIA(){
			JMenu menu=new JMenu("IA simplette");
			menu.add(new IA(2));
			menu.add(new IA(3));
			return menu;
		}
		
		public class IA extends JMenuItem{
			private static final long serialVersionUID = 1L;
			private String intitule="IA";
			
			public IA(int num){
				switch(num){
				case 0:intitule="Immobile";break;
				case 1:intitule="Pas d'IA";break;
				case 2:intitule="Simple";break;
				case 3:intitule="Evoluee";break;
				case 4:intitule="IA evoluee";break;
				case 5:intitule="IA directive";break;
				case 6:intitule="IA genetique";break;
				case 7:intitule="IA parfaite";break;
				}
				this.setText(intitule);
				this.addActionListener(new actionIA());
			}
			
			public class actionIA implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("changement en "+intitule);
					switch(intitule){
					case "Immobile":fenetre.getMoteur().changerIA(
							Intelligence.NO);
						//fenetre.getMoteur().enJeu=false;
						//fenetre.getMoteur().resetMap();
						//fenetre.getMoteur().jeu();
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Immobile", 2);
						fenetre.repaint();
						break;
					case "Pas d'IA":fenetre.getMoteur().changerIA(
							Intelligence.ME);
						//fenetre.getMoteur().enJeu=false;
						//fenetre.getMoteur().resetMap();
						//fenetre.getMoteur().jeu();
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("A vous de Jouer", 2);
						fenetre.repaint();
						break;
					case "Simple":fenetre.getMoteur().changerIA(
							Intelligence.RANDOM);
						//fenetre.getMoteur().enJeu=false;
						//fenetre.getMoteur().resetMap();
						//fenetre.getMoteur().jeu();
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Simplette simple", 2);
						fenetre.repaint();
						break;
					case "Evoluee":fenetre.getMoteur().changerIA(
							Intelligence.RANDOM);
						//fenetre.getMoteur().enJeu=false;
						//fenetre.getMoteur().resetMap();
						//fenetre.getMoteur().jeu();
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Simplette evoluee", 2);
						fenetre.repaint();
						//fenetre.getMoteur().thread.notify();
						break;
					case "IA directive":fenetre.getMoteur().changerIA(
							Intelligence.DIRECTIVE);
						//fenetre.getMoteur().enJeu=false;
						//fenetre.getMoteur().resetMap();
						//fenetre.getMoteur().jeu();
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Directive", 2);
						fenetre.repaint();
						//fenetre.getMoteur().thread.notify();
						break;
					default:break;
					}
				}
			}
		}//class IA
	}//changerIA
	
	/**
	 * Menu de changement de carte.
	 * */
	public class ChangerCarte extends JMenu{
		private static final long serialVersionUID = 1L;

		public ChangerCarte(){
			this.setText("Changer la carte");
			for(int i=1;i<=fenetre.getMoteur().getNbMap();i++){
				this.add(new carte(i));
			}
			this.addMouseListener(new EcouteurTouche());
		}
		
		public class carte extends JMenuItem{
			private static final long serialVersionUID = 1L;
			private int num;
			
			public carte(int num){
				this.num=num;
				this.setText("carte"+num);
				this.addActionListener(new actionCarte());
			}
			
			public class actionCarte implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("La carte "+num);
					fenetre.getMoteur().changerMap(num);
					fenetre.repaint();
				}
			}
		}
	}//changerCarte
	
	
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
			synchronized(fenetre.getMoteur().thread){
				//fenetre.getMoteur().thread.wait();
				fenetre.getMoteur().pauseIA();
			}
		}
	}
}
