package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Abstrakte Klasse, welche alle Eigenschaften der verschiedenen Entity Typen im Spiel vereint.
 */
public abstract class Entity implements Serializable, Inspectable {

	private static final long serialVersionUID = -8102331620222439400L;

	protected String name;
	protected String description;
	protected List<Option> options;
	protected String imagePath;

	/**
	 * Erzeugt ein Entity mit übergebenen Werten
	 * 
	 * @param name - Name des Entity
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param options - Liste mit Optionen (vgl Option), die das Entity hat
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * */
	public Entity(String name, String description, List<Option> options, String imagePath) {
		this.name = name;
		this.description = description;
		this.options = options;
		this.imagePath = imagePath;
	}

	@Override
	public String showInspectionDialog() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Option> getOptions() {
		return options;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		return this.name.equals(((Entity) other).getName());
	}
	
	@Override
	public abstract Entity clone() throws CloneNotSupportedException;
}