package map;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import moteurJeu.MoteurJeu;

import org.junit.Test;
import junit.framework.TestCase;

public class MapTest extends TestCase {

private Map map;
	
	protected void setUp() throws Exception {
		super.setUp();
		map = new Map(1,"src/Test.bd");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		map = null ;
	}
	
	//---------------------------------------------------------------------
	
	@Test
	public void testMap() {
		try{
			Map bidule = new Map(1,"src/Test.bd");
			assertNotNull(bidule);
		}
		catch (Exception e){
			fail("Erreur de chargement");
		}
		
		try{
			new Map(1,"jtj/jtj.jtj");
			fail("Vous avez un dossier jtj qui traine");
		}
		catch (Exception e){
			//OK
		}
		
	}

	@Test
	public void testReadFile() {
		Path f = Paths.get("src/Test.bd");
		String j = null;
		try {
			j = Map.readFile(f,Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(j);
		assertEquals(5,map.nbLigne(j));
	}
	

	@Test
	public void testGetContenu() {
		assertNotNull(map.getContenu());
		
	}

	@Test
	public void testGetHauteur() {
		assertEquals(5,map.getHauteur());
	}

	@Test
	public void testGetLargeur() {
		assertEquals(6,map.getLargeur());
	}

	@Test
	public void testGetTab() {
		char p = map.getTab(1,1);
		assertEquals('P',p);
	}

	@Test
	public void testGetDiamondRec() {
		assertEquals(1,map.getDiamondRec());
	}

	@Test
	public void testGetDiamondVal() {
		assertEquals(20,map.getDiamondVal());
	}

	@Test
	public void testGetDiamondBonus() {
		assertEquals(99,map.getDiamondBonus());
	}

	@Test
	public void testGetNumMap() {
		assertEquals(1,map.getNumMap());
	}

	@Test
	public void testGetNbMap() {
		assertEquals(2,map.getNbMap());
	}

}
