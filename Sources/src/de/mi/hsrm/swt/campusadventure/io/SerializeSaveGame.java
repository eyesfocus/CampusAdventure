package de.mi.hsrm.swt.campusadventure.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;

/**
 * Speichert den aktuellen Spielstand durch Serialisierung
 */
public class SerializeSaveGame implements SaveGame {
	private Player player;

	public SerializeSaveGame(Player player) {
		this.player = player;
	}
	
	@Override
	public void saveWorld() {
		long date = new Date().getTime();
		File playerfolder = new File("save/"+player.getName());
		File savegame = new File("save/"+"/"+playerfolder.getName()+"/"+date+".ca");
		try {
			if (!playerfolder.exists()) {
				playerfolder.mkdir();
			}
			savegame.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savegame));
			oos.writeObject(player);
	        oos.flush();
	        oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
