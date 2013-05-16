package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Ein festes Entity in der Spielwelt. Wird nach dem Benutzen nie verschwinden.
 */
public class EnvironmentItem extends InteractionEntity implements Serializable {

	private static final long serialVersionUID = -6815928994267719799L;

	/**
	 * Erzeugt ein EnvironmentItem
	 * ruft Konstruktor von Entity auf
	 * */
	public EnvironmentItem(String name, String description, String imagePath, Interaction interactions, boolean disappearsAfterCombining) {
		super(name, description, Arrays.asList(Option.INSPECT, Option.USE), imagePath, interactions, disappearsAfterCombining);
	}

	@Override
	public List<Entity> combine(InteractionEntity item) throws CombineNotSupportedException {	
		if (item.interactsWith(this)){
			List<Entity> result = item.getInteractions().getInteractionResults(this);
			return result;
		} else {
			throw new CombineNotSupportedException(this + " and " + item + " not supported");
		}
	}
	
	@Override
	public EnvironmentItem clone() throws CloneNotSupportedException {
		return new EnvironmentItem(name, description, imagePath, interactions, disappearsAfterCombining);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
}