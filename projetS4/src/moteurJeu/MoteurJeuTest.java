package moteurJeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import entite.*;

import junit.framework.TestCase;


import org.junit.Test;

import entite.Position;

public class MoteurJeuTest extends TestCase {
	
	private MoteurJeu motor;
	
	public MoteurJeuTest(String name){
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		motor = new MoteurJeu(1,"Test.bd");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		motor = null ;
	}
	
	//---------------------------------------------------------------------


	@Test
	public void testMoteurJeuIntString() {
		MoteurJeu moteur = new MoteurJeu(1,"BD01plus.bd");
		assertEquals(1,moteur.getNumMap());
		assertEquals("BD01plus.bd",moteur.getNomFichier());
	}

	@Test
	public void testMoteurJeuStringArray() {
		String [] args = new String[]{"tombe","libellule","luciole","parfaite"};
		MoteurJeu moteur = new MoteurJeu(1,"Test.bd",args,Intelligence.NO.get(),new ArrayList<Character>());
		assertEquals(true,moteur.getMODE_DEBUG_TOMBER());
		assertEquals(true,moteur.getMODE_DEBUG_LIBELLULE());
		assertEquals(true,moteur.getMODE_DEBUG_LUCIOLE());
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
	
	// FIN CONSTRUCTION ----------------------------------- 

	@Test
	public void testProcessPosition() {
		//Balise-J
		Position p = new Position(1,1);
		assertEquals(p,motor.processPosition());
	}
	
	@Test
	public void testChangerIA() {
		motor.changerIA(Intelligence.NO);
		assertEquals(-1,motor.getIntelligence());
	}

	/*@Test
	public void testProcessEndOfTurn() {
		MoteurJeu moteur = new MoteurJeu();
		
	}*/

	@Test
	public void testTour() {
		try{
			motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
			motor.tour('j',motor.processPosition());
		}catch(Exception e){
			fail("La méthode tour a échoué");
		}
		
		Position actuelle = new Position(2,1);
		assertTrue(actuelle.equals(motor.processPosition()));
	}

	@Test
	public void testPousserRocher() {
		motor.tour(Touche.TOUCHE_DROITE.toChar(),motor.processPosition());
		Position rocher = new Position(1,3);
		assertTrue(motor.entiteCarte(rocher).getClass() == new Roc().getClass());
	}

	@Test
	public void testGetHauteurMap() {
		motor.changerMap(1);
		assertEquals(5,motor.getHauteurMap());
	}

	@Test
	public void testGetLargeurMap() {
		motor.changerMap(1);
		assertEquals(6,motor.getLargeurMap());
	}
	
	@Test
	public void testEstCaseExistanteIntInt() {
		assertFalse(motor.estCaseExistante(-1,-1));
		assertFalse(motor.estCaseExistante(1,-1));
		assertFalse(motor.estCaseExistante(-1,1));
		assertTrue(motor.estCaseExistante(1,1));
		
		assertFalse(motor.estCaseExistante(100,100));
		assertFalse(motor.estCaseExistante(100,5));
		assertFalse(motor.estCaseExistante(4,100));
		assertTrue(motor.estCaseExistante(4,5));
	}

	@Test
	public void testEstCaseExistantePosition() throws Exception {
		Position position11 = new Position(1,1);
		Position positionMoins = new Position(-1,-1);
		Position positionOver9000 = new Position(9000,9000);
		
		assertFalse(motor.estCaseExistante(positionMoins));
		assertFalse(motor.estCaseExistante(positionOver9000));
		assertTrue(motor.estCaseExistante(position11));
		
		try{
			motor.estCaseExistante(null);
			fail("NullPointerException aurait du être levée");
		}
		catch (NullPointerException e){
			//NullPointerException attrapée
		}
	}

	@Test
	public void testEntiteCarte() {
		try{
			motor.entiteCarte(null);
		}
		catch (NullPointerException e) {
			//OK
		}
		
		Position murTitane = new Position(0,0);
		assertTrue(motor.entiteCarte(murTitane) instanceof MurTitane);
		
		Position roc = new Position(1,2);
		assertTrue(motor.entiteCarte(roc) instanceof Roc);
		
		Position diamant = new Position(2,1);
		assertTrue(motor.entiteCarte(diamant) instanceof Diamant);
		
		Position mur = new Position(3,4);
		assertTrue(motor.entiteCarte(mur) instanceof MurBasique);
		
		Position poussiere = new Position(3,1);
		assertTrue(motor.entiteCarte(poussiere) instanceof Poussiere);
		
		Position espace = new Position(3,2);
		assertTrue(motor.entiteCarte(espace) instanceof Espace);
		
		Position rockford = new Position(1,1);
		assertTrue(motor.entiteCarte(rockford) instanceof Joueur);
		
		//Récupérer le diamant pour faire apparaître la sortie
		try{
			motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
			motor.processEndOfTurn();
		}catch(Exception e){
			fail("La méthode tour et le processus de fin de tour ont échoués");
		}
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+motor.processPosition());
		Position exit = new Position(3,2);
		assertTrue(motor.entiteCarte(exit).getClass() == new Exit().getClass());
		
		
	}

	@Test
	public void testTomber() {
		motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
	}

	@Test
	public void testAfficherPorte() {
		motor.afficherPorte();
		assertTrue(motor.isPorteAffiche());
	}

	@Test
	public void testResetMap() {
		motor.changerMap(1);
		motor.resetMap();
		//assertTrue(true,joueur.PositionEmpty());
		assertEquals(1,motor.getNumMap());
	}

	@Test
	public void testChangerMap() {
		motor.changerMap(1);;
		assertEquals(1,motor.getNumMap());
	}

	@Test
	public void testDeplacerJoueur() {
		motor.deplacerJoueur(2, 1);
		Position joueur = new Position(2,1);
		assertEquals(joueur,motor.processPosition());
	}

	@Test
	public void testAjouterUnEspace() {
		Position espace = new Position(3,3);
		motor.ajouterUnEspace(espace);
		assertEquals(new Espace().getClass(),motor.entiteCarte(espace).getClass()) ;
	}

	@Test
	public void testDeplacementPossible() {
		assertTrue(motor.deplacementPossible(Touche.TOUCHE_BAS));
	}

	@Test
	public void testCaseLibre() {
		assertTrue(motor.caseLibre(3, 1));
	}

	@Test
	public void testGetNombreDiamants() {
		assertEquals(0,motor.getNombreDiamants());
		
		try{
			motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
		}catch(Exception e){
			fail("La méthode tour a échoué");
		}
		
		assertEquals(1,motor.getNombreDiamants());
	}

	@Test
	public void testGetScore() {
		assertEquals(0,motor.getScore());
		
		try{
			motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
		}catch(Exception e){
			fail("La méthode tour a échoué");
		}
		
		assertEquals(20,motor.getScore());
	}

	@Test
	public void testGetNombreTour() {
		assertEquals(0,motor.getNombreTour());
		
		try{
			motor.tour(Touche.TOUCHE_BAS.toChar(),motor.processPosition());
		}catch(Exception e){
			fail("La méthode tour a échoué");
		}
		
		assertEquals(1,motor.getNombreTour());
	}
	
	@Test
	public void testGagnerPoints() {
		assertEquals(0,motor.getScore());
		motor.gagnerPoints();
		assertEquals(20,motor.getScore());
	}

	@Test
	public void testMemorizePath() {
		assertTrue(motor.getDeplacements().isEmpty());
		motor.memorizePath(Touche.TOUCHE_DROITE.toChar());
		assertEquals((char)Touche.TOUCHE_DROITE.toChar(),(char)motor.getDeplacements().get(0));
	}

	@Test
	public void testResetPath() {
		assertTrue(motor.getDeplacements().isEmpty());
		motor.memorizePath(Touche.TOUCHE_DROITE.toChar());
		assertFalse(motor.getDeplacements().isEmpty());
		motor.resetPath();
		assertTrue(motor.getDeplacements().isEmpty());
	}

	@Test
	public void testGagner() {
		motor.gagner();
		assertTrue(motor.isaGagne());
		assertFalse(motor.isaPerdu());
	}

	@Test
	public void testPerdu() {
		motor.perdu();
		assertTrue(motor.isaPerdu());
		assertFalse(motor.isaGagne());
	}

	@Test
	public void testEstIA() {
		motor.changerIA(Intelligence.NO);
		motor.estIA(Intelligence.NO);
		assertEquals(-1,motor.getIntelligence());
	}

	@Test
	public void testGetEntite() {
		assertNotNull(motor.getEntite());
		assertTrue(motor.getEntite() instanceof Entite[][]);
		
		Entite[][] entite = motor.getEntite();
		assertTrue(entite[0][0] instanceof MurTitane);
		assertTrue(entite[1][1] instanceof Joueur);
		assertTrue(entite[2][2] instanceof Poussiere);
	}

	@Test
	public void testGetNbMap() {
		assertEquals(2,motor.getNbMap());
	}

	@Test
	public void testIsaGagne() {
		motor.gagner();
		assertEquals(true,motor.isaGagne());
	}

	@Test
	public void testIsaPerdu() {
		motor.perdu();
		assertEquals(true,motor.isaPerdu());
	}

	@Test
	public void testGetNomFichier() {
		//Default file is BD01plus.bd
		assertEquals("Test.bd",motor.getNomFichier());
	}

	@Test
	public void testGetNumMap() {
		motor.changerMap(1);
		assertEquals(1,motor.getNumMap());
	}

	@Test
	public void testIsPorteAffiche() {
		motor.afficherPorte();
		assertTrue(motor.isPorteAffiche());
	}

	@Test
	public void testGetPositionDiamant() {
		Set<PositionTombe> diamants = motor.getPositionDiamant();
		assertFalse(diamants.isEmpty());
		assertTrue(diamants.size()==1);
		Position diamant = new Position(2,1);
		
		Position actual = null;
		Iterator<PositionTombe> it = diamants.iterator();
		while (it.hasNext()){
			actual=it.next();
		}
		
		assertNotNull(actual);
		assertEquals(actual,diamant);
	}

	@Test
	public void testGetPositionJoueur() {
		Set<Position> joueur = motor.getPositionJoueur();
		assertFalse(joueur.isEmpty());
		assertTrue(joueur.size()==1);
		Position rockford = new Position(1,1);
		
		Position actual = null;
		Iterator<Position> it = joueur.iterator();
		while (it.hasNext()){
			actual=it.next();
		}
		
		assertEquals(actual,rockford);
	}

	@Test
	public void testGetNbDiamandRec() {
		assertEquals(1,motor.getNbDiamandRec());
	}

	@Test
	public void testGetPosPorte() {
		motor.afficherPorte();
		Position porte = new Position(4,3);
		assertEquals(porte,motor.getPosPorte());
	}
	
	public void testPauseIA(){
		motor.repriseIA();
		motor.pauseIA();
		assertEquals(true,motor.enPause());
	}
	
	public void testRepriseIA(){
		motor.pauseIA();
		motor.repriseIA();
		assertEquals(false,motor.enPause());
	}

}
