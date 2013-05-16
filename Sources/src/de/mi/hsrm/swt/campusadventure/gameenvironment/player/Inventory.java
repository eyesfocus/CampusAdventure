package de.mi.hsrm.swt.campusadventure.gameenvironment.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InventoryItem;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Inventar des Player
 * 
 */
public class Inventory extends Observable implements Serializable {
	private static final long serialVersionUID = -8866791267313457118L;
	private List<InventoryItem> inventory;

	/**
	 * Erstellt ein neues Standard-Inventar 
	 * (verwendet bei  newGame in SystemManager) 
	 */
	public Inventory() {
		inventory = new ArrayList<InventoryItem>();
		inventory.add((InventoryItem) EntityInitializer.getInstance().getEntity("MacBook (ungeladen)"));
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Erstellt ein neues Inventar mit vorgegebenen Items
	 * @param items Liste von InventoryItem mit denen das Inventory gefüllt werden soll
	 */
	public Inventory(List<InventoryItem> items) {
		inventory = new ArrayList<InventoryItem>();
		inventory.addAll(items);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Fügt ein InventoryItem zum Inventar hinzu
	 * @param item - InventoryItem welches hinzugefügt werden soll
	 */
	public void addItem(InventoryItem item) {
		inventory.add(item);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Entfernt ein InventoryItem aus dem Inventory
	 * @param item - InventoryItem welches entfernt werden soll
	 */
	public void removeItem(InventoryItem item) {
		inventory.remove(item);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Prüft, ob das übergebene InventoryItem im Inventory ist.
	 * 
	 * @param item - InventoryItem welches auf Vorhandensein überprüft werden soll
	 * @return true wenn das InventoryItem bereits im Inventar vorhanden ist
	 */
	public boolean hasItem(Entity item) {
		if (!(item instanceof InventoryItem)) {
			return false;
		}
		if (inventory.contains(item)) {
			return true;
		}
		return false;
	}
	
	public InventoryItem getItem(int index) {
		return inventory.get(index);
	}

	/**
	 * Prüft, ob das Inventar leer ist.
	 * @return true wenn das Inventar leer ist, false sonst
	 */
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	/**
	 * Gibt die Anzahl der InventoryItem im Inventar an.
	 * @return inventargröße 
	 */
	public int size() {
		return inventory.size();
	}

	/**
	 * Textausgabe des Iventars
	 */
	public String toString() {
		String s = "Inventory:\n";
		for (InventoryItem i : inventory) {
			s += i.toString() + "\n";
		}
		return s;
	}
}
