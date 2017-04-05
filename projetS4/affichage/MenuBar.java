package projetS4.affichage;

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
		}
		
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
	} 
}
