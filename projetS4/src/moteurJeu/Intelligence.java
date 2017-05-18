package moteurJeu;

public enum Intelligence {
	NO(-1),
	ME(0),
	RANDOM(1),
	GENETIQUE(2),
	DIRECTIVE(3);

	private int intelligence;

	Intelligence(int j){
		intelligence=j;
	}

	public int get(){
		return intelligence;
	}
}
