package de.mi.hsrm.swt.campusadventure.gamelogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.io.LevelParser;
import de.mi.hsrm.swt.campusadventure.io.SaveGame;
import de.mi.hsrm.swt.campusadventure.io.SerializeLoadGame;
import de.mi.hsrm.swt.campusadventure.io.SerializeSaveGame;
import de.mi.hsrm.swt.campusadventure.io.TxtLevelParser;
import de.mi.hsrm.swt.campusadventure.io.LoadGame;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Kümmert sich um die Kommunaktion mit der Ein- und Ausgabe um grundlegende Programmfunktionen umzusetzen. Singleton.
 */
public class SystemManager { 
	private static SystemManager manager;
	
	public static SystemManager getInstance() {
		if(manager == null) {
			manager = new SystemManager();
		}
		return manager;
	}
	
	private SystemManager() {
		
	}
	
	/**
	 * Läd alle Ordnernamen aus dem Speicherort für Spielstände.
	 * 
	 * @return Liste der Spielstände
	 */
	public List<String> loadSavegames() {
		ArrayList<String> savegames = new ArrayList<String>();
		File dir = new File("save");
		for (File file : dir.listFiles()) {
			if(file.isDirectory() && !file.isHidden()) {
				savegames.add(file.getName());
			}
		}
		return savegames;
	}
	
	/**
	 * Läd alle Speicherstände eines Player-Ordners.
	 * 
	 * @param playerName Name des Ordners
	 * @return Liste der Spielstände
	 */
	public List<String> loadPlayerSaves(String playerName) {
		ArrayList<String> playerSaves = new ArrayList<String>();
		File dir = new File("save/"+playerName);
		for (File file : dir.listFiles()) {
			if(file.isFile()) {
				playerSaves.add(file.getName());
			}
		}
		return playerSaves;
	}
	
	/**
	 * Initialisiert eine gespeicherte Spielwelt
	 * 
	 * @param playerName - Ordnername
	 * @param savegame - Spielstanddatei im Ordnernnamen
	 * @return Generierter Player.
	 */
	public Player loadGame(String playerName, String savegame) {
		LoadGame lg = new SerializeLoadGame(playerName, savegame);
		return lg.generateWorld();
	}

	/**
	 * Initialisiert eine neue Spielwelt.
	 * 
	 * @param playerName - Name des Player Objekts
	 * @return Generiertes Player Objekt.
	 */
	public Player newGame(String playerName) {
		LevelParser txtLevelParser = new TxtLevelParser("map/level.map");	// Läd die Levelkarte
		Field startfield = txtLevelParser.buildWorld();						// Erstellt eine leere Karte
		RandomMapInitializer randomMap = new RandomMapInitializer();				// Erstellt einen neuen MapInitializer
		while(!randomMap.allEntitiesPlaced()) {
			randomMap.placeEntitysOnMap(startfield, null);						// Platziert die Entities auf der Karte
		}
		
		return new Player(playerName, rndStartDirection(startfield), startfield);
	}

	/**
	 * Speichert den aktuellen Spielstand ab.
	 * 
	 * @param player Player Objekt
	 */
	public void saveGame(Player player) {
		SaveGame sg = new SerializeSaveGame(player);
		sg.saveWorld();
	}
	
	/**
	 * Sucht die möglichen Richtungen (vgl Direction) in die der Player
	 * schauen kann und wählt zufällig eine aus.
	 * 
	 * @param startfield - Field aud dem der Player steht
	 * @return Direction in die der Player gucken soll
	 */
	private Direction rndStartDirection(Field startfield) {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		if (startfield.hasNextField(Direction.WEST)) {
			directions.add(Direction.WEST);
		}
		if (startfield.hasNextField(Direction.EAST)) {
			directions.add(Direction.EAST);
		}
		if (startfield.hasNextField(Direction.SOUTH)) {
			directions.add(Direction.SOUTH);
		}
		if (startfield.hasNextField(Direction.NORTH)) {
			directions.add(Direction.NORTH);
		}
		return directions.get(new Random().nextInt(directions.size()));
	}
}