package ia;

import entite.IA;

public class IA_Directive implements IA{
	
	private Rockford j;
	
	public IA_Directive(){
		j = new Rockford(10000);
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionList() {
		// TODO Auto-generated method stub
		
	}
	
	public char[] getDirections(){
		return j.getDirections();
	}

}
