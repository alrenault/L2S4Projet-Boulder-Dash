package projetS4;

import junit.framework.TestCase;

public class JoueurTest extends TestCase {
	Joueur joueur=new JoueurHumain(false,true,'t',0,0);
	//joueur.deplacer();
	
	public void testDeplacer(){
		assertEquals(true,joueur.deplacer('2'));
	}
}
