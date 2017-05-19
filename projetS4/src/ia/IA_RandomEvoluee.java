package ia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.glass.events.KeyEvent;

import entite.Diamant;


import entite.Entite;
import entite.IA;
import moteurJeu.MoteurJeu;
import moteurJeu.Touche;
import entite.Position;
import entite.PositionTombe;

public class IA_RandomEvoluee  extends IA_Random implements IA{

	private RockfordEvolue j ;
	
	@Override
	public List<Character> actionList() {
		// TODO Auto-generated method stub
		return null;
	}

	public IA_RandomEvoluee() {
		// TODO Auto-generated constructor stub
		//j = new RockfordEvolue(10000);
	}
	
}
