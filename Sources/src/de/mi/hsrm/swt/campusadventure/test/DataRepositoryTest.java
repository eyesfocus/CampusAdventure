package de.mi.hsrm.swt.campusadventure.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.exception.NoSuchBuildingException;
import de.mi.hsrm.swt.campusadventure.exception.NoSuchEntityException;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Person;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * JUnit Test, welcher überprüft, ob die Entity und Building 
 * Objekte richtig initialisiert werden.
 * @author claudia
 *
 */
public class DataRepositoryTest {

	EntityInitializer ei;
	public DataRepositoryTest() {
		ei = EntityInitializer.getInstance();
	}
	
	/**
	 * Überpfüft, ob die Personen die 
	 * richtigen Gegenstände hält
	 */
	@Test
	public void testEntityGet() {
		assertEquals("Test Itemübergabe Weitz", ((Person)ei.getEntity("Herr Weitz")).getItem(), ei.getEntity("Glas"));
		assertEquals("Test Itemübergabe Barth", ((Person)ei.getEntity("Meister Barth")).getItem(), ei.getEntity("Email"));
	}
	
	@Test
	public void testEnergyItems() {
		EnergyItem energy = (EnergyItem) ei.getEntity("Nerdbrille");
		assertEquals(energy.getMutationValue(), 2f, 0.001f);
		assertFalse(energy.isAbsolute());
	}
	
	/**
	 * Überprüft, ob eine Exception geworfen wird, 
	 * wenn das angeforderte Building nicht existiert
	 */
	@Test(expected = NoSuchBuildingException.class)
	public void testBuildingException() {
		ei.getBuilding("Kamera");
	}
	
	/**
	 * Überprüft, ob eine Exception geworfen wird, 
	 * wenn das angeforderte Entity nicht existiert
	 */
	@Test(expected = NoSuchEntityException.class)
	public void testEntityException() {
		ei.getEntity("Blumentopf");
	}
}
