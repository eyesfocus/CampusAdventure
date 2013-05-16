package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.repository.Option;


/**
 * Entity in der Spielwelt, welches einen Roboter repräsentiert.
 * Besitzt ein InventoryItem weakness, welches die einzige Interaction für den Roboter ist.
 * Verschwindet nach Kombinieren mit weakness.
 * 
 * */
public class Robot extends InteractionEntity implements Serializable {

	private static final long serialVersionUID = -4925732031293369692L;
	private boolean isDestroyed;
	
	private PropertyChangeSupport changes;

	/**
	 * Erzeugt ein Robot mit übergebenen Werten
	 * 
	 * @param name - Name der Person
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * @param interactions - Interaktionsmöglichkeiten mit Gegenständen
	 **/
	public Robot(String name, String description, String imagePath,
			Interaction interactions) {
		super(name, description, Arrays.asList(Option.INSPECT, Option.USE),
				imagePath, interactions, true);
		this.changes = new PropertyChangeSupport(this);
		this.isDestroyed = false;
	}

	@Override
	public List<Entity> combine(InteractionEntity item) throws CombineNotSupportedException {
		if(item.interactsWith(this)){
			destroy();
			return item.getInteractions().getInteractionResults(this);
		} else {
			throw new CombineNotSupportedException(this + " and " + item + " not supported!");
		}

	}
	
	public void destroy() {
		this.isDestroyed = true;
		changes.firePropertyChange("robotDestroyed", false, true);
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
	
	@Override
	public Robot clone() throws CloneNotSupportedException {
		return new Robot(name, description, imagePath, interactions);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
}
