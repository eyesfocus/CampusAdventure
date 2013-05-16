package de.mi.hsrm.swt.campusadventure.test;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * JUnit Test, welcher den Mutationsanstieg 
 * des Player Objektes überprüft.
 *
 */
public class PlayerMutationTest {

	private Player player;
	TestField testField;
	float mutation;

	public PlayerMutationTest() {
		testField = new TestField(); // mutationsfaktor der felder: 2f
		mutation = 0;

		player = new Player("Heinz", Direction.EAST, testField.getStartField());

		player.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("actMutation")) {
					mutation = (Float) evt.getNewValue();
				}
			}
		});
	}

	public void ignoreTurnAround() {
		player.move(); // 5
		player.turnAround();
		player.turnAround();
		player.move();// 7
		player.move();// 9
		player.turnAround();
	}

	public void pickUpEnergies() {
		player.pickUpEntity(new EnergyItem("t1", 3f, true)); // 3f.
		player.pickUpEntity(new EnergyItem("t2", 8f, true)); // 11f.
		player.pickUpEntity(new EnergyItem("t3", -8f, true)); // 3f.
	}

	public void manipulateFactor() {
		player.pickUpEntity(new EnergyItem("t4", 6f, true)); // 15
		player.pickUpEntity(new EnergyItem("t5", 5f, true)); // 20
		/* Manipulation */
		player.pickUpEntity(new EnergyItem("t6", 10f, false)); // 20
	}

	public void move() {
		player.move(); // 40
		player.move(); // 60

		player.move(); // 80

		//kein nextfield mehr in die blickrichtung des players.
		player.move(); // 80
	}

	@Test
	public void testMove() {

		pickUpEnergies();
		assertEquals(true, (mutation == 3f));

		ignoreTurnAround();
		assertEquals(true, (mutation == 9f));

		manipulateFactor();
		assertEquals(true, (mutation == 20f));

		move();
		assertEquals(true, (mutation == 80f));

	}
}
