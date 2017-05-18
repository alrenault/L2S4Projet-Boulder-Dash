package moteurJeu;

import static org.junit.Assert.*;
import junit.framework.TestCase;


import org.junit.Test;

public class MoteurJeuTest extends TestCase {
	
	private MoteurJeu motor;
	
	public MoteurJeuTest(String name){
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		motor = new MoteurJeu();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		motor = null ;
	}
	
	//---------------------------------------------------------------------

	@Test
	public void testChangerIA() {
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		motor.changerIA(Intelligence.NO);
		assertEquals(-1,motor.getIntelligence());
		//motor.jeu();
	}

	@Test
	public void testMoteurJeuIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoteurJeuStringArray() {
		fail("Not yet implemented");
	}

	//Tests de Construction des Objets ------------------------------------------------------------
	@Test
	public void testMoteurJeu() {
		assertNotNull(motor);
	}

	public void testEntite(){
		assertNotNull(motor.getEntite());
	}
	
	public void testAmibe(){
		assertNotNull(motor.getAmibe());
	}
	
	public void testLuciole(){
		assertNotNull(motor.getLuciole());
	}
	
	public void testLibellule(){
		assertNotNull(motor.getLibellule());
	}
	
	public void testJoueur(){
		assertNotNull(motor.getJoueur());
	}
	
	public void testEspace(){
		assertNotNull(motor.getEspace());
	}
	
	public void testRoc(){
		assertNotNull(motor.getRoc());
	}
	
	public void testMur(){
		assertNotNull(motor.getMur());
	}
	
	public void testMurMagique(){
		assertNotNull(motor.getMurMagique());
	}
	
	public void testMurTitane(){
		assertNotNull(motor.getMurTitane());
	}
	
	public void testDiamant(){
		assertNotNull(motor.getDiamant());
	}
	
	public void testExit(){
		assertNotNull(motor.getExit());
	}
	
	public void testExplosion(){
		assertNotNull(motor.getExplosion());
	}
	
	public void testPoussiere(){
		assertNotNull(motor.getPoussiere());
	}
	
	@Test
	public void testAffichage() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessEndOfTurn() {
		fail("Not yet implemented");
	}

	/*@Test
	public void testJeu() {
		fail("Not yet implemented");
	}

	@Test
	public void testTour() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testPousserRocher() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstCaseExistanteIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstCaseExistantePosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testEntiteCarte() {
		fail("Not yet implemented");
	}

	@Test
	public void testTomber() {
		fail("Not yet implemented");
	}

	@Test
	public void testConstruireMapEntite() {
		fail("Not yet implemented");
	}

	@Test
	public void testAfficherMapEntite() {
		fail("Not yet implemented");
	}

	@Test
	public void testAfficherPorte() {
		fail("Not yet implemented");
	}

	@Test
	public void testResetMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangerMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeplacerJoueur() {
		fail("Not yet implemented");
	}

	@Test
	public void testAjouterUnEspace() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeplacementPossible() {
		fail("Not yet implemented");
	}

	@Test
	public void testCaseLibre() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNombreDiamants() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNombreTour() {
		fail("Not yet implemented");
	}

	@Test
	public void testGagnerPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testMemorizePath() {
		fail("Not yet implemented");
	}

	@Test
	public void testResetPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnregistrer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGagner() {
		fail("Not yet implemented");
	}

	@Test
	public void testPerdu() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstIA() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntite() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNbMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnJeu() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEnJeu() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsaGagne() {
		motor.gagner();
		assertEquals("Le joueur gagne",true,motor.isaGagne());
	}

	@Test
	public void testIsaPerdu() {
		motor.perdu();
		assertEquals("Le joueur perd",true,motor.isaPerdu());
	}

	@Test
	public void testGetFenetre() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHauteurMap() {
		motor.changerMap(1);
		assertEquals("Map 1 : Largeur = 22",22,motor.getHauteurMap());
	}

	@Test
	public void testGetLargeurMap() {
		motor.changerMap(1);
		assertEquals("Map 1 : Hauteur = 40",40,motor.getLargeurMap());
	}

	@Test
	public void testGetNomFichier() {
		//Default file is BD01plus.bd
		assertEquals("Nom fichier valide","BD01plus.bd",motor.getNomFichier());
	}

	@Test
	public void testGetNumMap() {
		motor.changerMap(1);
		assertEquals("Map 1",1,motor.getNumMap());
	}

	@Test
	public void testIsPorteAffiche() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPositionDiamant() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPositionJoueur() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNbDiamandRec() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosPorte() {
		fail("Not yet implemented");
	}

}
