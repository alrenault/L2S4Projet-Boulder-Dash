package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import moteurJeu.Convertisseur;
import moteurJeu.Intelligence;

/**
 * Classe gerant le menu sur l'interface graphique
 * que son contenu affiche.
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class MenuBar extends JMenuBar{
	
	/**
	 * Variable pour la serialisation
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference vers la fenetre graphique
	 */
	private FenetreBoulder fenetre;
	
	/**
	 * Constructeur de la classe MenuBar
	 * @param fenetre Reference vers la fenetre graphique
	 */
	public MenuBar(FenetreBoulder fenetre){
		this.fenetre=fenetre;
		this.add(new Fichier());
		this.add(new ChangerIA());
		this.add(new ChangerCarte());
		this.add(new Credits());
		this.addMouseListener(new EcouteurTouche());
	}
	
	/**
	 * Classe interne creant le menu fichier
	 */
	private class Fichier extends JMenu{
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructeur de la classe internet Fichier
		 */
		public Fichier(){
			this.setText("Fichier");
			this.add(new NouvellePartie());
			this.add(new Rejouer());
			this.addMouseListener(new EcouteurTouche());
		}
		
		/**
		 * Classe interne creant l'onglet de nouvelle partie.
		 * */
		private class NouvellePartie extends JMenuItem{
			private static final long serialVersionUID = 1L;

			/**
			 * Constructeur de la classe interne NouvellePartie
			 */
			public NouvellePartie(){
				this.setText("NouvellePartie");
				this.addActionListener(new ActionNouvellePartie());
			}
			
			/**
			 * Classe interne faisant les actions lors de l'utilisation du bouton nouvelle partie
			 */
			private class ActionNouvellePartie implements ActionListener{
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
		 * Classe permettant d'afficher le menu Rejouer
		 * */
		private class Rejouer extends JMenu{
			/**
			 * Les actions de rejouer
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
		
				private static final long serialVersionUID = 1L;
				
				/**
				 * Les actions de l'onglets RejouerFichier
				 */
				public RejouerFichier(){
					this.setText("Rejouer une partie enregistree");
					this.addActionListener(new ActionLecture());
				}
				
				/**
				 * Lis le fichier choisi
				 */
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
							List<Character> list = new ArrayList<Character>();
							//remplit le tableau de deplacements avec les caracteres de la chaine
							for(int i=0;i<ligne.length();i++){
								char chara = ligne.charAt(i);
								System.out.println("Prochain cara : "+chara);
								list.add(chara);
							}
							//convertit du format dghb en vk
							Convertisseur.convTabDGHB_vers_VK(list);
							
							//lance l' IA Rejoue
							fenetre.getMoteur().rejouerPartie(list);
						}
					}
				}//ActionLecture
			}//RejouerFichier
		}//Rejouer
	}//Fichier
	
	/**
	 * Classe interne creant le menu de changement des IA
	 * */
	private class ChangerIA extends JMenu{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructeur de la classe interne ChangerIA
		 */
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
		
		/**
		 * Creation d'un onglet pour l'IA simplette (aleatoire)
		 * @return Retourne l'onglet
		 */
		private JMenu menuIA(){
			JMenu menu=new JMenu("IA simplette");
			menu.add(new IA(2));
			menu.add(new IA(3));
			return menu;
		}
		
		/**
		 * Classe interne creant les differents onglets
		 */
		private class IA extends JMenuItem{
			private static final long serialVersionUID = 1L;
			private String intitule="IA";
			
			/**
			 * Constructeur de la classe interne IA
			 * @param num Correspond a l'iA choisie
			 */
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
				this.addActionListener(new ActionIA());
			}
			
			/**
			 * Classe interne changeant l'IA en fonction de celle choisie 
			 * et affiche un message sur la fenetre
			 */
			private class ActionIA implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("changement en "+intitule);
					switch(intitule){
					case "Immobile":fenetre.getMoteur().changerIA(
							Intelligence.NO);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Immobile", 2);
						fenetre.repaint();
						break;
					case "Pas d'IA":fenetre.getMoteur().changerIA(
							Intelligence.ME);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("A vous de Jouer", 2);
						fenetre.repaint();
						break;
					case "Simple":fenetre.getMoteur().changerIA(
							Intelligence.RANDOM);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Simplette simple", 2);
						fenetre.repaint();
						break;
					case "Evoluee":fenetre.getMoteur().changerIA(
							Intelligence.RANDOM);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Simplette evoluee", 2);
						fenetre.repaint();
						break;
					case "IA directive":fenetre.getMoteur().changerIA(
							Intelligence.DIRECTIVE);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Directive", 2);
						fenetre.repaint();
						break;
					case "IA genetique":fenetre.getMoteur().changerIA(
							Intelligence.GENETIQUE);
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Genetique", 2);
						fenetre.repaint();
						break;
					case "IA parfaite":fenetre.getMoteur().changerIA(
							Intelligence.PARFAITE);
						fenetre.getMoteur().iaParfaiteAGagne = false;
						fenetre.getMoteur().repriseIA();
						fenetre.ecrireMessage("IA Parfaite", 20);
						fenetre.repaint();
						//fenetre.focusPanneau();
						//fenetre.getMoteur().jeu();
						//fenetre.getMoteur().thread.notify();
						break;
					default:break;
					}
				}
			}
		}//class IA
	}//changerIA
	
	/**
	 * Classe interne creant le menu de changement de carte.
	 * */
	private class ChangerCarte extends JMenu{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructeur de la classe interne ChangerCarte
		 */
		public ChangerCarte(){
			this.setText("Changer la carte");
			for(int i=1;i<=fenetre.getMoteur().getNbMap();i++){
				this.add(new Carte(i));
			}
			this.addMouseListener(new EcouteurTouche());
		}
		
		/**
		 * Classe interne creant un onglet par map
		 */
		private class Carte extends JMenuItem{
			private static final long serialVersionUID = 1L;
			
			/**
			 * Le numero de la map
			 */
			private int num;
			
			/**
			 * Constructeur de la classe interne Carte
			 * @param num Le numero de la map
			 */
			public Carte(int num){
				this.num=num;
				this.setText("carte"+num);
				this.addActionListener(new ActionCarte());
			}
			
			/**
			 * Classe interne changeant la carte et affichant un message
			 */
			private class ActionCarte implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("La carte "+num);
					fenetre.getMoteur().changerMap(num);
					fenetre.repaint();
				}
			}
		}
	}//changerCarte
	
	/**
	 * Classe interne gerant l'affichage des credits
	 */
	private class Credits extends JMenu{
		/**
		 * Le num de serialisation
		 */
		private static final long serialVersionUID = 1L;

		public Credits(){
			this.setText("Credits");
			this.add(new Noms());
			this.addMouseListener(new EcouteurTouche());
		}
		
		/**
		 * Classe interne qui s'occupe d'afficher les noms des membres du groupe
		 * */
		private class Noms extends JMenuItem{
			
			public Noms(){
				this.setText("Noms des membres du groupe");
				this.addActionListener(new ActionCredits());
			}
			/**
			 * Classe interne changeant la carte et affichant un message
			 */
			private class ActionCredits implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					fenetre.ecrireMessage("Projet realise par PITROU Adrien, RENAULT Alexis" +
							" et LEVEQUE Quentin", 3);
					fenetre.repaint();
				}
			}
		}
	}
	/**
	 * Classe interne permettant de mettre en pause quant on clique sur la fenetre
	 */
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
				fenetre.getMoteur().pauseIA();
			}
		}
	}
}
