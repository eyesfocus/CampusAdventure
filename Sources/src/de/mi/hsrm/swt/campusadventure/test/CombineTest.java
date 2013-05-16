package de.mi.hsrm.swt.campusadventure.test;

import org.junit.Test;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InteractionEntity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InventoryItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.Direction;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Unit-Test für Kombinationen ausgewählter Entity Objekte.
 * */
public class CombineTest {
	Player player;
	EntityInitializer ei;
	
	public CombineTest() {
		ei = EntityInitializer.getInstance();
		
		player = new Player("Heinz", Direction.EAST,
				new TestField().getStartField());
	}

	@Test
	public void testCombine() {
		System.out.println(player.getInventory());

		InteractionEntity ladekabel = (InteractionEntity) ei.getEntity("ladekabel");
		InteractionEntity steckdose = (InteractionEntity) ei.getEntity("steckdose");
		InventoryItem macbook_u = (InventoryItem) ei.getEntity("MacBook (ungeladen)");
		
		/*Hebe Ladekabel Auf*/
		player.pickUpEntity(ladekabel);
		
		/*Inventory: Macbook ungeladen, Ladekabel*/
		System.out.println(player.getInventory().toString());

		/*Setze Steckdose aktiv*/
		player.setActiveEntity(steckdose);
		System.out.println("active: " + player.getActiveEntity().toString() + "\n");

		try {
			/*Kombiniere: Steckdose(aktiv) + Ladekabel*/
			player.combineEntities(ladekabel);
		} catch (CombineNotSupportedException e) {
			e.printStackTrace();
		}

		/*Inventory: Macbook ungeladen*/
		System.out.println(player.getInventory().toString());
		
		/*Setze Steckdose m. Kabel aktiv*/
		player.setActiveEntity((InteractionEntity)player.getActField().getNextField(player.getDirection()).getEntity());

		try {
			/*Kombiniere: Steckdose m. Kabel(aktiv) + Macbook(ungeladen)*/
			player.combineEntities(macbook_u);
		} catch (CombineNotSupportedException e) {
			e.printStackTrace();

		}
		
		/*Inventory: Macbook geladen*/
		System.out.println(player.getInventory().toString());		

	}

}
