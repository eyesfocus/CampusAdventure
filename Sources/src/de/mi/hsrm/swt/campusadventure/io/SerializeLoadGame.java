package de.mi.hsrm.swt.campusadventure.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;

/**
 * Läd den aktuellen Spielstand durch Deserialisierung
 */
public class SerializeLoadGame implements LoadGame {

	private String filePath;
	
	/**
	 * Setzt den reativen Speicherpfad des Savegames
	 * 
	 * @param playername - Ordnername 
	 * @param filename - Spielstand
	 */
	public SerializeLoadGame(String playername, String filename) {
		this.filePath = playername+"/"+filename;
	}

	@Override
	public Player generateWorld() {
		Player player = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save/"+filePath));
			player = (Player) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return player;
	}

}
