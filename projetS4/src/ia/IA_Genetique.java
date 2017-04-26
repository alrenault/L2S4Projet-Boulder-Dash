package ia;
import entite.IA;

public class IA_Genetique implements IA {
	
	private Rockford thisRockford[];
	private int population;

	public IA_Genetique(int n) {
		// TODO Auto-generated constructor stub
		
		//Create 100 little Rockford
		population = n;
		thisRockford = new Rockford[population];		
	}
	
	public IA_Genetique() {
		this(100); // Par défaut, on crée 100 Rockford
	}
	

	private void initialisation(){
		for(int i = 0 ; i < population ; i++){
			thisRockford[i] = new Rockford(10000);
		}
	}
	
	public void process(){
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void actionList() {
		// TODO Auto-generated method stub
		
		
	}

}
