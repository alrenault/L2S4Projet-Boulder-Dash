package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import moteurJeu.MoteurJeu.Intelligence;

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
		this.addMouseListener(new EcouteurTouche());
	}
	
	/**
	 * Classe interne creant le menu fichier
	 */
	public class Fichier extends JMenu{
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructeur de la classe internet Fichier
		 */
		public Fichier(){
			this.setText("Fichier");
			this.add(new NouvellePartie());
			this.addMouseListener(new EcouteurTouche());
		}
		
		/**
		 * Classe interne creant l'onglet de nouvelle partie.
		 * */
		public class NouvellePartie extends JMenuItem{
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
			public class ActionNouvellePartie implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Le bouton de nouvelle partie");
					fenetre.getMoteur().resetMap();
					fenetre.ecrireMessage("nouvelle partie", 1);
					fenetre.repaint();
				}
			}
		}//NouvellePartie
	}//Fichier
	
	/**
	 * Classe interne creant le menu de changement des IA
	 * */
	public class ChangerIA extends JMenu{
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
		public class IA extends JMenuItem{
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
			public class ActionIA implements ActionListener{
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
					default:break;
					}
				}
			}
		}//class IA
	}//changerIA
	
	/**
	 * Classe interne creant le menu de changement de carte.
	 * */
	public class ChangerCarte extends JMenu{
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
		public class Carte extends JMenuItem{
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
			public class ActionCarte implements ActionListener{
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
