package moteurJeu;

/**
 * Classe creant un enum d'intelligence
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public enum Intelligence {
	REJOUE(-2),
	NO(-1),
	ME(0),
	RANDOM(1),
	GENETIQUE(2),
	DIRECTIVE(3),
	PARFAITE(4);

	private int intelligence;

	Intelligence(int j){
		intelligence=j;
	}

	public int get(){
		return intelligence;
	}
}
