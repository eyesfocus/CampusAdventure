package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Entity, welches nach Aufheben durch den Player in der Inventory verwaltet wird.
 * 
 * */
public class InventoryItem extends InteractionEntity implements Serializable {

	private static final long serialVersionUID = 7844268687585543423L;

	/**
	 * Erzeugt ein InventoryItem
	 * ruft Konstruktor von InteractionEntity auf
	 * 
	 * @param name - Name des Entity
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * @param interactions - Interaktionen des InteractionEntity
	 * @param disappearsAfterCombining - Flag, das aufzeigt ob das InteractionEntity nach kombinieren verschwindet
	 * */
	public InventoryItem(String name, String description, String imagePath,
			Interaction interactions, boolean disappearsAfterCombining) {
		super(name, description, Arrays.asList(Option.INSPECT, Option.USE,
				Option.PICK_UP), imagePath, interactions,
				disappearsAfterCombining);
	}

	@Override
	public List<Entity> combine(InteractionEntity item)
			throws CombineNotSupportedException {
		if (item.interactsWith(this)) {
			List<Entity> result = item.getInteractions().getInteractionResults(this);
			return result;
		} else {
			throw new CombineNotSupportedException(this + " and " + item
					+ " not supported");
		}

	}
	
	@Override
	public InventoryItem clone() throws CloneNotSupportedException {
		return new InventoryItem(name, description, imagePath, interactions, disappearsAfterCombining);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}

}
