package de.mi.hsrm.swt.campusadventure.gameenvironment.level;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.helper.Point;
import de.mi.hsrm.swt.campusadventure.repository.Direction;
import de.mi.hsrm.swt.campusadventure.repository.FloorType;

/**
 * Ein Feld in einem Building, kann entweder ein CorridorField oder ein
 * RoomField sein.
 * 
 */
public abstract class Field implements Serializable {

	private static final long serialVersionUID = -2490450707025814045L;

	private PropertyChangeSupport changes;

	protected Building building;
	protected Point position;
	private Entity entity;
	private Field west;
	private Field east;
	private Field north;
	private Field south;

	/**
	 * Erzeugt ein neues Field
	 * 
	 * @param point - Koordinatenpunkt
	 * @param building - Building in dem das Field liegt
	 * */
	public Field(Point point, Building building) {
		this.building = building;
		this.position = point;
		changes = new PropertyChangeSupport(this);
	}

	/**
	 * Löscht das Objekt von dem Feld.
	 */
	public void deleteEntity() {
		setEntity(null);
	}

	/**
	 * Prüft, ob es ein nächstes Field in eine bestimmte Direction gibt
	 * 
	 * @param direction Direction in die Das Feld liegt
	 * @return boolean gibt es ein Field in die angegebene Direction?
	 */
	public boolean hasNextField(Direction direction) {
		return getNextField(direction) != null;
	}

	/**
	 * Gibt das Nachbarfeld welches in direction liegt zurück.
	 * 
	 * @param direction -  Direction in die Das Feld liegt
	 * @return Field - Field, welches in der angegebenen Direction zu finden ist.
	 */
	public Field getNextField(Direction direction) {
		switch (direction) {
		case WEST:
			return getWest();
		case EAST:
			return getEast();
		case SOUTH:
			return getSouth();
		case NORTH:
			return getNorth();
		default:
			return null;
		}
	}

	/**
	 * Entscheidet zu welchen Seiten das Field Nachbarn hat. (Richtungsabhängig)
	 * 
	 * @param direction - Direction
	 * @return Den FloorType der auf das Field passt
	 */
	public FloorType getFloorType(Direction direction) {
		FloorType floorType = null;
		if (hasNextField(direction.getLeft())
				&& hasNextField(direction.getRight())) {
			floorType = FloorType.BOTH;
		} else if (hasNextField(direction.getLeft())
				&& !hasNextField(direction.getRight())) {
			floorType = FloorType.LEFT;
		} else if (!hasNextField(direction.getLeft())
				&& hasNextField(direction.getRight())) {
			floorType = FloorType.RIGHT;
		} else if (!hasNextField(direction.getLeft())
				&& !hasNextField(direction.getRight())) {
			floorType = FloorType.NONE;
		}
		return floorType;
	}

	/**
	 * Prüft, ob auf dem Field ein Entity liegt.
	 * 
	 * @return true wenn ein Entity vorhanden ist.
	 */
	public boolean hasEntity() {
		return (entity == null) ? false : true;
	}

	/**
	 * Holt sich aus dem zugehörigen Building den Mutationsfaktor.
	 */
	public float getAreaMutationFactor() {
		return building.getMutationFactor();
	}

	public Point getPosition() {
		return position;
	}

	public Field getWest() {
		return west;
	}

	public Field getEast() {
		return east;
	}

	public Field getNorth() {
		return north;
	}

	public Field getSouth() {
		return south;
	}

	public Entity getEntity() {
		return entity;
	}

	public Building getBuilding() {
		return building;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setWest(Field west) {
		this.west = west;
	}

	public void setEast(Field east) {
		this.east = east;
	}

	public void setNorth(Field north) {
		this.north = north;
	}

	public void setSouth(Field south) {
		this.south = south;
	}

	public void setEntity(Entity entity) {
		Entity oldEntity = this.entity;
		this.entity = entity;
		changes.firePropertyChange("fieldEntity", oldEntity, this.entity);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
	
	@Override
	public String toString() {
		String fieldtype = "";
		if (this instanceof RoomField) {
			fieldtype = "RaumFeld";
		} else {
			fieldtype = "GangFeld";
		}

		String directions = "";
		if (west != null) {
			directions += "west ";
		}
		if (north != null) {
			directions += "north ";
		}
		if (south != null) {
			directions += "south ";
		}
		if (east != null) {
			directions += "east ";
		}
		
		
		return fieldtype + " im Gebäude: " + building.toString()
				+ " auf Position: " + position + " mit Abzweigungen: " + directions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (building == null) {
			if (other.building != null)
				return false;
		} else if (!building.equals(other.building))
			return false;
		if (east == null) {
			if (other.east != null)
				return false;
		} else if (!east.equals(other.east))
			return false;
		if (north == null) {
			if (other.north != null)
				return false;
		} else if (!north.equals(other.north))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		if (west == null) {
			if (other.west != null)
				return false;
		} else if (!west.equals(other.west))
			return false;
		return true;
	}

}