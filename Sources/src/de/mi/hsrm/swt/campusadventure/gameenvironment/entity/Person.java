package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Ansprechbares Entity in der Spielwelt. Wird nach dem Kombinieren nie
 * verschwinden.
 */
public class Person extends InteractionEntity  implements Serializable, Addressable {

	private static final long serialVersionUID = -9181797630067436091L;
	private String dialogItem;
	private String dialogNoItem;
	private InventoryItem item;

	/**
	 * Erzeugt eine Person mit übergebenen Werten, die kein Entity hält
	 * 
	 * @param name - Name der Person
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * @param interactions - Interaktionsmöglichkeiten mit Gegenständen
	 * @param disappearsAfterCombining - Verschwindet die Person nach einer erfolgreichen Kombination?
	 * @param dialogNoItem - Dialog der bei Inspektion gezeigt wird, wenn kein Entity vorhanden ist
	 * */
	public Person(String name, String description, String imagePath,
			Interaction interactions, boolean disappearsAfterCombining,
			String dialogNoItem) {
		super(name, description, Arrays.asList(Option.INSPECT, Option.USE, Option.ADDRESS), imagePath, interactions, disappearsAfterCombining);
		this.dialogItem = null;
		this.dialogNoItem = dialogNoItem;
		this.item = null;
	}

	/**
	 * Erzeugt eine Person mit übergebenen Werten, die ein Entity hält
	 * @param name - Name des Entity
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * @param interactions - Interaktionsmöglichkeiten mit Gegenständen
	 * @param disappearsAfterCombining - Verschwindet die Person nach einer erfolgreichen Kombination?
	 * @param item - InventoryItem, welches die Person hält
	 * @param dialogNoItem - Dialog der bei Inspektion durch den Player gezeigt wird, wenn kein Entity gehalten wird
	 * @param dialogItem - Dialog der bei Inspektion durch den Player gezeigt wird, wenn Entity gehalten wird
	 * */
	public Person(String name, String description, String imagePath,
			Interaction interactions, boolean disappearsAfterCombining,
			InventoryItem item, String dialogNoItem, String dialogItem) {
		super(name, description, Arrays.asList(Option.INSPECT, Option.ADDRESS, 
				Option.USE), imagePath, interactions, disappearsAfterCombining);
		this.dialogItem = dialogItem;
		this.dialogNoItem = dialogNoItem;
		this.item = item;
	}

	/**
	 * @return dialogItem - Dialog, wenn die Person ein Item besitzt, dialogNoItem sonst
	 * */
	public String showDialog() {
		if (hasItem()) {
			return dialogItem;
		} else {
			return dialogNoItem;
		}
	}

	/**
	 * Überprüft, ob Person ein Item hält
	 * @return true wenn ja, false sonst
	 * 
	 * */
	public boolean hasItem() {
		return (item != null);
	}
	
	/**
	 * Entfernt das Entity von der Person
	 */
	public void removeItem() {
		item = null;
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

	public String getDialogItem() {
		return dialogItem;
	}

	public String getDialogNoItem() {
		return dialogNoItem;
	}

	public void setDialogItem(String dialogItem) {
		this.dialogItem = dialogItem;
	}

	public void setDialogNoItem(String dialogNoItem) {
		this.dialogNoItem = dialogNoItem;
	}

	public InventoryItem getItem() {
		return item;
	}
	
	@Override
	public String toString() {
		return super.getName();
	}
	
	@Override
	public Person clone() throws CloneNotSupportedException {
		return new Person(name, description, imagePath, interactions, disappearsAfterCombining,
				item, dialogNoItem, dialogItem);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
}