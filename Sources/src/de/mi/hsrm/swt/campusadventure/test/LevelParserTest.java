package de.mi.hsrm.swt.campusadventure.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.io.LevelParser;
import de.mi.hsrm.swt.campusadventure.io.TxtLevelParser;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Unit Test für das Parsen einer Level Datei. Vergleicht die LevelDatei aus
 * test/level.map mit der identischen Map, die in TestField per Hand generiert
 * wird.
 * 
 * */
public class LevelParserTest {

	private LevelParser parser;
	private Field parser_start;
	private Field hand_start;

	public LevelParserTest() {
		parser = new TxtLevelParser("test/level.map");
	}

	/**
	 * Rekursive Methode vergleicht die beiden aktuellen Felder
	 * 
	 * @param parser - aktuelles Field in der geparsten Map
	 * @param hand - aktuelles Field in der handprogrammierten Map
	 * @param ignore - Direction, die in der rekursion ignoriert werden muss, da das Feld dort vorher schon überprüft wurde
	 * 
	 * */
	public void testFields(Field parser, Field hand, Direction ignore) {
		assertTrue(parser.toString().equals(hand.toString()));
		if (parser.getNorth() != null && ignore != Direction.NORTH) {
			testFields(parser.getNorth(), hand.getNorth(), Direction.EAST);
		}
		if (parser.getEast() != null && ignore != Direction.EAST) {
			testFields(parser.getEast(), hand.getEast(), Direction.WEST);
		}
		if (parser.getSouth() != null && ignore != Direction.SOUTH) {
			testFields(parser.getSouth(), hand.getSouth(), Direction.NORTH);
		}
		if (parser.getWest() != null && ignore != Direction.WEST) {
			testFields(parser.getWest(), hand.getWest(), Direction.EAST);
		}
	}

	@Test
	public void testLevelBuild() {
		parser_start = parser.buildWorld();
		hand_start = new TestField().getStartField();

		testFields(parser_start, hand_start, null);
	}

}
