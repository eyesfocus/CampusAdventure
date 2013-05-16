package de.mi.hsrm.swt.campusadventure.io;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;

/**
 * Ein Interface, welches Methoden beschreibt die für 
 * das Laden eines gesicherten Spielstandes benötigt werden.
 */
public interface LoadGame {
	/**
	 * Wandelt die Eigenschaften der gespeicherten 
	 * Spielwelt in Spiel-Objekte um.
	 * 
	 * @return Spielfigur
	 */
	public Player generateWorld();
}
