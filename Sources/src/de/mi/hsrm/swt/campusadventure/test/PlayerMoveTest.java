package de.mi.hsrm.swt.campusadventure.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Unit-Test für die Bewegung des Player Objekts.
 * Simuliert einen Walkthrough einer per Hand programmierten Spielwelt
 * (TestField).
 * */
public class PlayerMoveTest {

	private Player player;
	private TestField testfield;

	public PlayerMoveTest() {
		testfield = new TestField(); //startfeld, auf dem sich der Player befindet
		player = new Player("Heinz", Direction.EAST, testfield.getStartField());
	}

	public void north() {
		assertEquals(true, player.getDirection().equals(Direction.NORTH));
	}

	public void east() {
		assertEquals(true, player.getDirection().equals(Direction.EAST));
	}

	public void south() {
		assertEquals(true, player.getDirection().equals(Direction.SOUTH));
	}

	public void west() {
		assertEquals(true, player.getDirection().equals(Direction.WEST));
	}

	public void start() {
		assertEquals(
				true,
				player.getActField().getPosition()
						.equals(testfield.getStartField().getPosition()));
	}

	/**
	 * Der Player bewegt sich durch die in TestField generierte Map und
	 * muss sich am Ende wieder auf dem Startfeld befinden.
	 * Zwischendurch sollten keine Exceptions geworfen werden.
	 * */
	@Test
	public void testMove() {
		east();
		
		player.move();
		east();

		player.move();
		east();
		
		player.move();
		east();
		
		player.move();
		east();

		player.move();
		east();
		
		player.turnRight();
		south();
		
		player.move();
		south();
		
		player.move();
		south();
		
		player.move();
		south();
		
		player.move();
		south();
		
		player.turnRight();
		west();

		player.turnRight();
		north();

		player.turnAround();
		south();

		player.turnAround();
		north();
		
		player.move();
		north();
		
		player.move();
		north();
		
		player.move();
		north();
		
		player.move();
		north();

		player.turnLeft();
		west();
		
		player.move();
		west();
		
		player.move();
		west();
		
		player.move();
		west();
		
		player.move();
		west();

		start();
	}
}
