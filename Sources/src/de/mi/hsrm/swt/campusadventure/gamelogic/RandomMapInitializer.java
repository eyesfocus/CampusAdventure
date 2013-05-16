package de.mi.hsrm.swt.campusadventure.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Robot;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Building;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.repository.Direction;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Füllt die Levelkarte mit den bekannten Entity Objekten. Platziert diese zufällig.
 * 
 */
public class RandomMapInitializer implements MapInitializer {

	private List<Entity> entities;
	private Random rnd;

	/**
	 * Füllt die Levelkarte mit den bekannten Entity Objekten. Platziert diese zufällig. 
	 */
	public RandomMapInitializer() {
		entities = new ArrayList<Entity>();
		rnd = new Random();
		loadEntities();
	}

	private void loadEntities() {
		entities.addAll(EntityInitializer.getInstance().getBasicEntities());
		/**
		List<Entity> init = EntityInitializer.getInstance().getBasicEntities();
		for (Entity e : init) {
			try {
				entities.add(e.clone());
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		}
		**/
	}

	@Override
	public void placeEntitysOnMap(Field field, Direction ignoreDirection) {
		if (field.getEast() != null && ignoreDirection != Direction.EAST) {
			placeEntitysOnMap(field.getEast(), Direction.WEST);
		}
		if (field.getWest() != null && ignoreDirection != Direction.WEST) {
			placeEntitysOnMap(field.getWest(), Direction.EAST);
		}
		if (field.getSouth() != null && ignoreDirection != Direction.SOUTH) {
			placeEntitysOnMap(field.getSouth(), Direction.NORTH);
		}
		if (field.getNorth() != null && ignoreDirection != Direction.NORTH) {
			placeEntitysOnMap(field.getNorth(), Direction.SOUTH);
		}
		if (!allEntitiesPlaced()) {
			placeEntityOnField(field);
		}
	}

	@Override
	public void placeEntityOnField(Field field) {
		Entity entity = getRandomEntity();
		boolean setEntity = (rnd.nextBoolean() && !field.hasEntity());
		if (setEntity && checkEntityInRightBuilding(field.getBuilding(), entity)) {
			field.setEntity(entity);
			entities.remove(entity);
		}
	}
	
	/**
	 * Prüft ob alle Entities platziert wurden oder ob noch welche übrig sind
	 * 
	 * @return true wenn alle Entities platziert wurden
	 */
	public boolean allEntitiesPlaced() {
		return entities.isEmpty();
	}

	/**
	 * Überprüft, ob das Entity in einem Building platziert werden darf.
	 * 
	 * @param building - aktuelles Building
	 * @param entity - aktuelles Entity
	 * @return true wenn das Entity platziert werden darf
	 */
	private boolean checkEntityInRightBuilding(Building building, Entity entity) {
		Building mainbuilding = EntityInitializer.getInstance().getBuilding("Hauptgebäude");
		Building camera = EntityInitializer.getInstance().getBuilding("Camera");
		Entity camerafrau = EntityInitializer.getInstance().getEntity("Camerafrau");

		if (!building.equals(mainbuilding) && entity instanceof Robot) {
			return false;
		} else if (!building.equals(camera) && entity.equals(camerafrau)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Holt sich ein zufälliges Entity aus der Liste
	 * @return zufälliges Entity
	 */
	private Entity getRandomEntity() {
		int size = entities.size();
		if (size > 1) {
			return entities.get(rnd.nextInt(size));
		} else if (size == 1) {
			return entities.get(0);
		}
		else return null;
	}
}
