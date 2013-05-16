package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.Arrays;

import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Ein Entity welches die Mutation des Player Objekts beeinflusst.
 * Besitzt einen mutationValue, der entweder relativ oder absolut ist.
 */
public class EnergyItem extends Entity implements Serializable {

	private static final long serialVersionUID = 1044004755321703233L;
	private final static String IMAGE_PATH = "img/entities/energy.png";
	private final static String DESCRIPTION = "Ein Gegenstand der dir möglicherweise helfen kann deine Mutation einzudämmen.";
	
	private float mutationValue;
	private boolean absolute;
	
	/**
	 * Erzeugt ein EnergyItem mit übergebenen Werten
	 * @param name - Name des EnergyItem
	 * @param mutationValue - Mutationswert des des EnergyItem, der sich bei KOmbination auf den Player auswirkt
	 * @param absolute - Flag, welches beschreibt ob der Mutationswert absolut ist.
	 * */
	public EnergyItem(String name, float mutationValue, boolean absolute) {
		super(name, DESCRIPTION, Arrays.asList(Option.INSPECT, Option.PICK_UP), IMAGE_PATH);
		this.mutationValue = mutationValue;
		this.absolute = absolute;
	}

	public float getMutationValue() {
		return mutationValue;
	}

	public boolean isAbsolute() {
		return absolute;
	}

	public void setMutationValue(float mutationValue) {
		this.mutationValue = mutationValue;
	}

	public void setAbsolute(boolean absolute) {
		this.absolute = absolute;
	}
	
	@Override
	public EnergyItem clone() throws CloneNotSupportedException {
		return new EnergyItem(name, mutationValue, absolute);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
}