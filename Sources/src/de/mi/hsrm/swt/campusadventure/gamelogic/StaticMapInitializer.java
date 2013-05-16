package de.mi.hsrm.swt.campusadventure.gamelogic;

import java.util.Map;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.helper.Point;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Platziert die Entity auf den Field 
 * mit dem angegebenen Point
 */
public class StaticMapInitializer implements MapInitializer {
	
	Map<Point, Entity> entities;
	
	public StaticMapInitializer(Map<Point, Entity> entities) {
		this.entities = entities;
	}

	@Override
	public void placeEntitysOnMap(Field field, Direction ignoreDirection) {
		if(field.getEast() != null && ignoreDirection != Direction.EAST) {
			placeEntitysOnMap(field.getEast(), Direction.WEST);
		}
		if(field.getWest() != null && ignoreDirection != Direction.WEST) {
			placeEntitysOnMap(field.getWest(), Direction.EAST);
		}
		if(field.getSouth() != null && ignoreDirection != Direction.SOUTH) {
				placeEntitysOnMap(field.getSouth(), Direction.NORTH);
		}
		if(field.getNorth() != null && ignoreDirection != Direction.NORTH) {
				placeEntitysOnMap(field.getNorth(), Direction.SOUTH);
		}
		placeEntityOnField(field);
	}

	@Override
	public void placeEntityOnField(Field field) {
		Point point = field.getPosition();
		if (entities.containsKey(point)) {
			Entity entity = entities.get(point);
			field.setEntity(entity);
			entities.remove(entity);
		}
	}

}
