package de.mi.hsrm.swt.campusadventure.gameenvironment.level;

import java.io.Serializable;

import de.mi.hsrm.swt.campusadventure.helper.Point;


/**
 * Spielfeld Field, das einen Gang darstellt. 
 * Kann ein Gateway sein, dh. ein Übergang zu einem anderen Building
 * */
public class CorridorField extends Field implements Serializable {

	private static final long serialVersionUID = 2104268087003119451L;
	private boolean isGateway; 
	
	/**
	 * Erzeugt ein neues CorridorField, welches kein Gateway ist
	 * @param point - Koordinatenpunkt
	 * @param building - Building in dem das Field liegt
	 * */
	public CorridorField(Point point, Building building) {
		super(point, building); 
		this.isGateway = false;
	}
	
	/**
	 * Erzeugt ein neues CorridorField, welches ein Gateway ist
	 * @param position - Koordinatenpunkt
	 * @param building - Building in dem das Field liegt
	 * @param isGateway - Ist das Field ein Durchgang zu einem anderen Gebäude?
	 * */
	public CorridorField(Point position, Building building, boolean isGateway) {
		super(position, building);
		this.isGateway = isGateway;
	}

	public boolean isGateway() {
		return isGateway;
	}

	public void setGateway(boolean isGateway) {
		this.isGateway = isGateway;
	}
}