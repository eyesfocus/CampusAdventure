package de.mi.hsrm.swt.campusadventure.test;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.gamelogic.SystemManager;

/**
 * JUnit Test welcher die funktionalitäten 
 * des SystemManager überprüft
 */
public class SystemManagerTest {
	
	private SystemManager manager;
	
	public SystemManagerTest() {
		manager = SystemManager.getInstance();
	}

	/**
	 * Testet das Speichern eines Spielstandes
	 */
	@Test
	public void testSaveGame() {
		Player player = manager.newGame("BlubbBlubb");
		player.move();
		player.move();
		player.move();
		manager.saveGame(player);
	}
	
	@Test
	public void testLoadGame() {
		String player = manager.loadSavegames().get(0);
		System.out.println(player);
		Player p = manager.loadGame(player, manager.loadPlayerSaves(player).get(0));
		System.out.println(p);
		p.move();
		p.turnRight();
		p.move();
		System.out.println(p);
	}
	
	@Test
	public void testNewGame() {
		manager.newGame("Harald");
	}
}
