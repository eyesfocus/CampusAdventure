package de.mi.hsrm.swt.campusadventure.io;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;

/**
 * Ein Interface welches die Methoden beschreibt 
 * die benötigt werden, um ein Level zu erstellen
 */
public interface LevelParser {
	
	/**
	 * Erstellt die leere Karte 
	 * 
	 * @return das Feld auf dem sich die Spielfigur befindet
	 */
	public Field buildWorld();
}
