package de.mi.hsrm.swt.campusadventure.repository;

/**
 * Himmelsrichtungen.
 * Werden benötigt um zu definieren in welche Richtung der Player guckt
 * oder in welche Richtungen ein Field Nachbarfelder hat.
 *
 */
public enum Direction {
	EAST, WEST, NORTH, SOUTH;

	/**
	 * Gibt den linken Nachbarn der aktuellen Direction zurück
	 * @return linker Nachbar von this
	 */
	public Direction getLeft() {
		Direction left = null;
		switch (this) {
		case EAST:
			left = NORTH;
			break;
		case WEST:
			left = SOUTH;
			break;
		case NORTH:
			left = WEST;
			break;
		case SOUTH:
			left = EAST;
			break;
		}
		return left;
	}

	/**
	 * Gibt den rechten Nachbarn der aktuellen Direction zurück
	 * @return rechter Nachbar von this
	 */
	public Direction getRight() {
		Direction right = null;
		switch (this) {
			case EAST:
				right = SOUTH;
				break;
			case WEST:
				right = NORTH;
				break;
			case NORTH:
				right = EAST;
				break;
			case SOUTH:
				right = WEST;
				break;
		}
		return right;
	}
	
	
	/**
	 * Gibt die entgegengesetzte Himmelsrichtung der aktuellen zurück
	 * @return Entgegengesetzte Direction von this
	 */
	public Direction getBack() {
		Direction back = null;
		switch (this) {
			case EAST:
				back = WEST;
				break;
			case WEST:
				back = EAST;
				break;
			case NORTH:
				back = SOUTH;
				break;
			case SOUTH:
				back = NORTH;
				break;
		}
		return back;
	}

}