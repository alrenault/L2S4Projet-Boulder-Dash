package projetS4.affichage;


public class Menu {

	private char[][] tab={{'g','t','t'},{'t','a','t'},{'h','t','t'}};
	
	public void react(char touche){
		System.out.println("touche : "+touche);
	}
	
	public char[][] getTab(){
		return tab;
	}

}
