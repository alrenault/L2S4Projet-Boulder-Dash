package moteurJeu;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLearnJunit extends LearnJunit {

	@Test
	public void test() {
		assertEquals(2,LearnJunit.calculer(1,1));
	}

}
