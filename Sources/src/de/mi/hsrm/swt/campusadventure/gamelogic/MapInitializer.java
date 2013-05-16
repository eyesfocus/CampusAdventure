package de.mi.hsrm.swt.campusadventure.gamelogic;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Interface welches die Methoden beschreibt, die benötigt werden, 
 * um Entity auf Field's zu platzieren.
 */
public interface MapInitializer {
	/**
	 * Durchläut die Karte und platziert Entity Objekte
	 * 
	 * @param field - aktuelles Feld
	 * @param ignoreDirection - Direction des letzten Fields
	 */
	void placeEntitysOnMap(Field field, Direction ignoreDirection);
	
	/**
	 * Platziert das übergebene Entity auf dem übergebenen Field
	 * sollte sich noch kein Entity auf dem Field befinden.
	 */
	void placeEntityOnField(Field field);
}
