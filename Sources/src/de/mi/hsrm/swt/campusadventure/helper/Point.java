package de.mi.hsrm.swt.campusadventure.helper;

import java.io.Serializable;


/**
 * Beschreibt die Koordinaten eines Field 
 * 
 * */
public class Point implements Serializable {

	private static final long serialVersionUID = -8497207490004157975L;
	private int x;
	private int y;

	/**
	 * Erstellt einen neuen Point
	 * @param x - X-Koordinate
	 * @param y - Y-Koordinate
	 * */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point && this.x == ((Point)obj).getX() && this.y == ((Point)obj).getY()) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
