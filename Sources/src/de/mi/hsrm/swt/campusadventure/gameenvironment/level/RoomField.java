package de.mi.hsrm.swt.campusadventure.gameenvironment.level;

import java.io.Serializable;

import de.mi.hsrm.swt.campusadventure.helper.Point;
import de.mi.hsrm.swt.campusadventure.repository.Direction;


/**
 * Spielfeld Field, das einen Raum darstellt. 
 * Besitzt eine Direction, die die Richtung der Tür im RoomField bezeichnet
 * */
public class RoomField extends Field implements Serializable {
	private static final long serialVersionUID = -2846034441276967349L;
	private Direction doorDirection;
	
	/**
	 * Erzeugt ein neues RoomField
	 * @param point - Koordinatenpunkt
	 * @param building - Building in dem das RoomField liegt
	 * */
	public RoomField(Point point, Building building) {
		super(point, building);
	}

	public Direction getDoorDirection() {
		return doorDirection; 
	}

	public void setDoorDirection(Direction doorDirection) {
		this.doorDirection = doorDirection;
	}
}