package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{
	
	public MenuBar(){
		this.add(new Fichier());
	}
	
	public class Fichier extends JMenu{
		
		public Fichier(){
			this.setText("Fichier");
			this.add(new NouvellePartie());
			this.add(new ChangerCarte());
			this.add(new ChangerIA());
		}
		/**
		 * Onglet de nouvelle partie.
		 * */
		public class NouvellePartie extends JMenuItem{
			
			public NouvellePartie(){
				this.setText("NouvellePartie");
				this.addActionListener(new actionNouvellePartie());
			}
			
			public class actionNouvellePartie implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Le bouton de nouvelle partie");
				}
			}
		}
		/**
		 * Menu de changement de carte.
		 * */
		public class ChangerCarte extends JMenu{
			
			public ChangerCarte(){
				this.setText("Changer la carte");
				for(int i=1;i<=10;i++){
					this.add(new carte(i));
				}
			}
			
			public class carte extends JMenuItem{
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
					}
				}
			}
		}
		/**
		 * Menu de changement des IA
		 * */
		public class ChangerIA extends JMenu{
			
			public ChangerIA(){
				this.setText("Changer IA");
				for(int i=0;i<5;i++){
					this.add(new IA(i));
				}
			}
			
			public class IA extends JMenuItem{
				private String intitule="IA";
				
				public IA(int num){
					switch(num){
					case 0:intitule="IA simplette";break;
					case 1:intitule="IA evoluee";break;
					case 2:intitule="IA directive";break;
					case 3:intitule="IA genetique";break;
					case 4:intitule="IA parfaite";break;
					}
					this.setText(intitule);
					this.addActionListener(new actionCarte());
				}
				
				public class actionCarte implements ActionListener{
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("changement en "+intitule);
					}
				}
			}
		}
	} 
}
